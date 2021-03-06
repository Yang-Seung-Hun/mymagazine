package com.example.mymagazine.controller;

import com.example.mymagazine.domain.Favorite;
import com.example.mymagazine.domain.Post;
import com.example.mymagazine.domain.User;
import com.example.mymagazine.dto.ResponseFavoriteDto;
import com.example.mymagazine.exception.RestApiException;
import com.example.mymagazine.jwt.JwtTokenProvider;
import com.example.mymagazine.repository.FavoriteRepository;
import com.example.mymagazine.repository.PostRepository;
import com.example.mymagazine.repository.UserRepository;
import com.example.mymagazine.security.UserDetailsImpl;
import com.example.mymagazine.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteService favoriteService;
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("api/favorite/{postId}")
    public ResponseEntity Favorite(@PathVariable Long postId, HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println(token);

        if(jwtTokenProvider.validateToken(token)){

            //로그인 한 유저 정보
            String username = jwtTokenProvider.getUserPk(token);
            Optional<User> currentUser = userRepository.findByUsername(username);

            //현재 포스트, 포스트 작성 user 정보
            Optional<Post> post = postRepository.findPostById(postId);
            User postUser = post.get().getUser();

            Boolean like_ok = false;//일단 없다고 가정
            int like_cnt = favoriteRepository.countByPostId(postId);

            Optional<Favorite> byUserIdAndPostId = favoriteRepository.findByUserIdAndPostId(currentUser.get().getId(),postId);
            //현재 유저가 해당 포스트에 좋아요 누른 상태일떼 ==> 좋아요 취소
            if(byUserIdAndPostId.isPresent()){
                favoriteService.delete(byUserIdAndPostId.get());
                like_cnt -= 1;
            }
            //좋아요 추가
            else{
                Favorite new_favorite = new Favorite(post.get(), currentUser.get());
                favoriteService.save(new_favorite);
                like_ok = true;
                like_cnt += 1;
            }

            ResponseFavoriteDto responseFavoriteDto = new ResponseFavoriteDto(currentUser.get().getId(), postId, post.get().getImg_url(), post.get().getContents(), post.get().getCreatedAt().toString(), post.get().getModifiedAt().toString(),
                    postUser.getUsername(), postUser.getName(), like_cnt, like_ok);
            return new ResponseEntity(responseFavoriteDto,HttpStatus.OK);
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
//        //로그인 한 유저 정보
//        User currentUser = userDetails.getUser();
//
//        //현재 포스트, 포스트 작성 user 정보
//        Optional<Post> post = postRepository.findPostById(postId);
//        User postUser = post.get().getUser();
//
//        Boolean like_ok = false;//일단 없다고 가정
//        int like_cnt = favoriteRepository.countByPostId(postId);
//
//        Optional<Favorite> byUserIdAndPostId = favoriteRepository.findByUserIdAndPostId(currentUser.getId(),postId);
//        //현재 유저가 해당 포스트에 좋아요 누른 상태일떼 ==> 좋아요 취소
//        if(byUserIdAndPostId.isPresent()){
//            favoriteService.delete(byUserIdAndPostId.get());
//            like_cnt -= 1;
//        }
//        //좋아요 추가
//        else{
//            Favorite new_favorite = new Favorite(post.get(), currentUser);
//            favoriteService.save(new_favorite);
//            like_ok = true;
//            like_cnt += 1;
//        }
//
//        ResponseFavoriteDto responseFavoriteDto = new ResponseFavoriteDto(currentUser.getId(), postId, post.get().getTitle(), post.get().getContents(), post.get().getCreatedAt().toString(), post.get().getModifiedAt().toString(),
//                postUser.getUsername(), postUser.getName(), like_cnt, like_ok);
//        return new ResponseEntity(responseFavoriteDto,HttpStatus.OK);
    }

}
