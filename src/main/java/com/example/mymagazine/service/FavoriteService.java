package com.example.mymagazine.service;


import com.example.mymagazine.domain.Favorite;
import com.example.mymagazine.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Controller
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void save(Favorite favorite){
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void delete(Favorite favorite){
        favoriteRepository.delete(favorite);
    }
}
