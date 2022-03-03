package com.example.mymagazine.service;

import com.example.mymagazine.domain.Favorite;
import com.example.mymagazine.domain.Post;
import com.example.mymagazine.domain.User;
import com.example.mymagazine.repository.FavoriteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FavoriteServiceTest {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void favorite_save(){

        //given
        User user = new User("username","name","password");
        Post post = new Post("img", "contents", user);
        em.persist(user);
        em.persist(post);
        Favorite favorite = new Favorite(post, user);

        //when
        favoriteService.save(favorite);

        //then
        Optional<Favorite> findFavorite= favoriteRepository.findByUser(user);
        assertThat(findFavorite.get()).isNotNull();
    }

    @Test
    public void favorite_delete(){

        //given
        User user = new User("username","name","password");
        Post post = new Post("img", "contents", user);
        em.persist(user);
        em.persist(post);
        Favorite favorite = new Favorite(post, user);
        favoriteService.save(favorite);

        Optional<Favorite> findFavorite= favoriteRepository.findByUser(user);

        //when
        favoriteService.delete(findFavorite.get());

        //then
        Optional<Favorite> findFavorite2= favoriteRepository.findByUser(user);
        assertThrows(NoSuchElementException.class, () -> findFavorite2.get());
    }

}