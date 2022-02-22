package com.example.mymagazine.service;


import com.example.mymagazine.domain.User;
import com.example.mymagazine.dto.RequestSingUpDto;
import com.example.mymagazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(RequestSingUpDto requestDto){
        String username = requestDto.getUsername();
        String name = requestDto.getName();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();

        if (requestDto.isValidateUsername(username) && requestDto.isValidatePassword(username,password)){

            Optional<User> findUser = userRepository.findByUsername(username);
            if(findUser.isPresent()){
                throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
            }
        }

        else{
            throw new IllegalArgumentException("올바르지 않은 아이디 및 비밀번호 입니다.");
        }

        User user = new User(username,name,nickname,passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
