package com.example.mymagazine.repository;

import com.example.mymagazine.domain.Favorite;
import com.example.mymagazine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

    @Query("select count(f) from Favorite f where f.post.id = :postID")
    public int countByPostId(@Param("postID") Long postID);

    @Query("select f from Favorite f where f.user.id = :userID")
    public List<Favorite> findByUserId(@Param("userID") Long userID);

    @Query("select f from Favorite f where f.post.id = :postID")
    public List<Favorite> findByPostId(@Param("postID") Long postID);

    @Query("select f from Favorite f where f.user.id = :userID and f.post.id = :postID")
    public Optional<Favorite> findByUserIdAndPostId(@Param("userID") Long userID, @Param("postID") Long postID);

    public Optional<Favorite> findByUser(User user);
}
