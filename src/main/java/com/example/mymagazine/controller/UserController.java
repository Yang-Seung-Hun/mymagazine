package com.example.mymagazine.controller;

import com.example.mymagazine.domain.User;
import com.example.mymagazine.dto.RequestSignInDto;
import com.example.mymagazine.dto.RequestSingUpDto;
import com.example.mymagazine.exception.RestApiException;
import com.example.mymagazine.security.UserDetailsImpl;
import com.example.mymagazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/signup")
    public ResponseEntity SignUpPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails != null){
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage("이미 로그인 되어있습니다.");
            return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
        }
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity("회원가입페이지", HttpStatus.OK);
    }

    @PostMapping("/api/signup")
    public ResponseEntity SignUp(@RequestBody RequestSingUpDto requestSingUpDto){

        try{
            userService.save(requestSingUpDto);
        }
        catch (IllegalArgumentException e){
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage(e.getMessage());
            return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("회원가입 완료!!", HttpStatus.OK);
    }

    @GetMapping("/api/signin")
    public ResponseEntity SignIn(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null){
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage("이미 로그인 되어있습니다.");
            return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
        }
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity("로그인페이지", HttpStatus.OK);
    }

    @GetMapping("/api/signinError")
    public ResponseEntity SignInFail(){

        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage("로그인 실패. username과 password를 확인해주세요");
        return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/")
    public User user(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails.getUsername());
        return userDetails.getUser();
    }

}
