package com.example.mymagazine.controller;

import com.example.mymagazine.domain.Favorite;
import com.example.mymagazine.domain.Post;
import com.example.mymagazine.domain.User;
import com.example.mymagazine.dto.*;
import com.example.mymagazine.exception.RestApiException;
import com.example.mymagazine.jwt.JwtTokenProvider;
import com.example.mymagazine.repository.FavoriteRepository;
import com.example.mymagazine.repository.PostRepository;
import com.example.mymagazine.repository.UserRepository;
import com.example.mymagazine.service.FavoriteService;
import com.example.mymagazine.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final FavoriteRepository likeRepository;
    private final FavoriteService likeService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/posts")
    public List<ResponseGetPostsDto> getPosts(HttpServletRequest request){

        List<ResponseGetPostsDto> responseGetPostsDtos = new ArrayList<>();
        List<Post> all = postRepository.findAll();
        for(Post post : all){
            Boolean like_ok = false;
            Long post_id = post.getId();
            String contents = post.getContents();
            String img_url = post.getImg_url();
            String create_date = post.getCreatedAt().toString();
            String modified_date = post.getModifiedAt().toString();

            int like_cnt = likeRepository.countByPostId(post.getId());

            String token = jwtTokenProvider.resolveToken(request);
            if(jwtTokenProvider.validateToken(token)){
                String username = jwtTokenProvider.getUserPk(token);
                Optional<User> currentUser = userRepository.findByUsername(username);
                Optional<Favorite> tmp = likeRepository.findByUserIdAndPostId(currentUser.get().getId(), post.getId());
                if(tmp.isPresent()){
                    like_ok = true;
                }
            }

            User user = post.getUser();
            Long user_id = user.getId();

            responseGetPostsDtos.add(new ResponseGetPostsDto(user_id, post_id,contents,img_url,like_cnt,create_date, modified_date, like_ok));
        }
        return responseGetPostsDtos;
    }

    @PostMapping("/api/posts")
    public ResponseEntity registerPost(@RequestBody RequestRegisterPostsDto requestRegisterPostsDto, HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        System.out.println(token);

        if(jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUserPk(token);
            System.out.println(username);
            Optional<User> currentUser = userRepository.findByUsername(username);
            Post post = new Post(requestRegisterPostsDto.getImg_url(), requestRegisterPostsDto.getContents(), currentUser.get());
            postService.save(post);

            ResponsePostDto responsePostDto = new ResponsePostDto(currentUser.get().getId(), post.getId(), post.getContents(), post.getImg_url(),
                0, post.getCreatedAt().toString(), post.getModifiedAt().toString(), false);

            return new ResponseEntity(responsePostDto, HttpStatus.OK);
        }

        else{
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
            restApiException.setErrorMessage("로그인 해주세요");
            return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);
        }
//        if (userDetails == null){
//            RestApiException restApiException = new RestApiException();
//            restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
//            restApiException.setErrorMessage("로그인 해주세요");
//            return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);
//        }
//
//        User currentUser = userDetails.getUser();
//        Post post = new Post(requestRegisterPostsDto.getTitle(), requestRegisterPostsDto.getContent(), currentUser);
//        postService.save(post);
//
//        ResponsePostDto responsePostDto = new ResponsePostDto(userDetails.getUser().getId(), post.getId(), post.getContents(), post.getTitle(),
//                0, post.getCreatedAt().toString(), post.getModifiedAt().toString(), false);

//        return new ResponseEntity(responsePostDto, HttpStatus.OK);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity deletePost(@PathVariable Long id, HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        if (jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUserPk(token);
            Optional<User> currentUsername = userRepository.findByUsername(username);
            Optional<Post> post = postRepository.findPostById(id);

            if (currentUsername.get().getUsername().equals(post.get().getUser().getUsername())) {
                List<Favorite> favorites = likeRepository.findByPostId(id);
                for (Favorite favorite : favorites) {
                    likeService.delete(favorite);
                }
                postService.delete(id);
            }

            else {
                RestApiException restApiException = new RestApiException();
                restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
                restApiException.setErrorMessage("게시글 주인만 삭제 가능합니다.");
                return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);

            }
        }

        else {
                RestApiException restApiException = new RestApiException();
                restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
                restApiException.setErrorMessage("로그인 해주세요");
                return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.OK);
    }


        @PatchMapping("/api/posts/{id}")
        public ResponseEntity updatePost (@PathVariable Long id, @RequestBody RequestPostUpdateDto requestPostUpdateDto, HttpServletRequest request){
            String token = jwtTokenProvider.resolveToken(request);

            Optional<Post> post = postRepository.findPostById(id);
            User user = post.get().getUser();

            Post updatedPost = null;
            int like_cnt = 0;
            Boolean like_ok = false;

            if(jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUserPk(token);
                Optional<User> currentUser = userRepository.findByUsername(username);

                if (currentUser.get().getUsername().equals(post.get().getUser().getUsername())) {
                    updatedPost = postService.update(id, requestPostUpdateDto);
                    like_cnt = likeRepository.countByPostId(post.get().getId());
                    Optional<Favorite> tmp = likeRepository.findByUserIdAndPostId(currentUser.get().getId(), id);
                    if (tmp.isPresent()) {
                        like_ok = true;
                    }
                }

                else{
                    RestApiException restApiException = new RestApiException();
                    restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
                    restApiException.setErrorMessage("게시글 주인만 수정 가능합니다.");
                    return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);
                }
            }

            else{
                RestApiException restApiException = new RestApiException();
                restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
                restApiException.setErrorMessage("로그인 해주세요");
                return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);
            }

            ResponsePostUpdateDto responsePostUpdateDto = new ResponsePostUpdateDto(user.getId(), id, updatedPost.getImg_url(), updatedPost.getContents(), updatedPost.getCreatedAt().toString(), updatedPost.getModifiedAt().toString(),
                    user.getUsername(), user.getName(), like_cnt, like_ok);

            return new ResponseEntity(responsePostUpdateDto, HttpStatus.OK);

//            Optional<Post> post = postRepository.findPostById(id);
//            User user = post.get().getUser();
//
//            Post updatedPost = null;
//            int like_cnt = 0;
//            Boolean like_ok = false;
//
//            if (userDetails == null) {
//                RestApiException restApiException = new RestApiException();
//                restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
//                restApiException.setErrorMessage("로그인 해주세요");
//                return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);
//            } else {
//                if (userDetails.getUsername().equals(post.get().getUser().getUsername())) {
//                    updatedPost = postService.update(id, requestPostUpdateDto);
//                    like_cnt = likeRepository.countByPostId(post.get().getId());
//                    Optional<Favorite> tmp = likeRepository.findByUserIdAndPostId(userDetails.getUser().getId(), id);
//                    if (tmp.isPresent()) {
//                        like_ok = true;
//                    }
//                } else {
//                    RestApiException restApiException = new RestApiException();
//                    restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
//                    restApiException.setErrorMessage("게시글 주인만 수정 가능합니다.");
//                    return new ResponseEntity(restApiException, HttpStatus.UNAUTHORIZED);
//                }
//            }
//
//            ResponsePostUpdateDto responsePostUpdateDto = new ResponsePostUpdateDto(user.getId(), id, updatedPost.getTitle(), updatedPost.getContents(), updatedPost.getCreatedAt().toString(), updatedPost.getModifiedAt().toString(),
//                    user.getUsername(), user.getName(), like_cnt, like_ok);
//            return new ResponseEntity(responsePostUpdateDto, HttpStatus.OK);
        }
    }

