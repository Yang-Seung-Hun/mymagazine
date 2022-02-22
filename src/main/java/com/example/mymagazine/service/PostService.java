package com.example.mymagazine.service;

import com.example.mymagazine.domain.Post;
import com.example.mymagazine.dto.RequestPostUpdateDto;
import com.example.mymagazine.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void save(Post post){
        postRepository.save(post);
    }

    @Transactional
    public void delete(Long postId){
        Optional<Post> postById = postRepository.findPostById(postId);
        postRepository.delete(postById.get());
    }

    @Transactional
    public Post update(Long postId, RequestPostUpdateDto requestPostUpdateDto){

        Optional<Post> post = postRepository.findPostById(postId);
        post.get().update(requestPostUpdateDto);

        return post.get();
    }
}
