package com.example.mymagazine.repository;

import com.example.mymagazine.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    public Optional<Post> findPostById(Long id);

}
