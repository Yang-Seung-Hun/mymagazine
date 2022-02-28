package com.example.mymagazine.controller;

import com.example.mymagazine.domain.User;
import com.example.mymagazine.dto.RequestSignInDto;
import com.example.mymagazine.dto.RequestSingUpDto;
import com.example.mymagazine.exception.RestApiException;
import com.example.mymagazine.jwt.JwtTokenProvider;
import com.example.mymagazine.repository.UserRepository;
import com.example.mymagazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class JwtController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EntityManager em;

//    @GetMapping("/")
//    @Transactional
//    public String test(){
//        User user = new User("뭐지", "ㅜㅜㅜ", "123456");
//        em.persist(user);
//        return "hello";
//    }

    @PostMapping("/api/signup")
    public ResponseEntity jwtRegister(@RequestBody RequestSingUpDto requestSingUpDto){
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

    @PostMapping("/api/signin")
    public ResponseEntity jwtLogin(@RequestBody RequestSignInDto requestSignInDto, HttpServletRequest request) {
        Optional<User> user = userRepository.findByUsername(requestSignInDto.getUsername());

        if (!user.isPresent()) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage("등록되지 않은 아이디입니다.");
            return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
        }

        if (!BCrypt.checkpw(requestSignInDto.getPassword(), user.get().getPassword())) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage("비밀번호 확인해주세요");
            return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
        }

        //토큰 있는데 로그인 보냄
        String token = jwtTokenProvider.resolveToken(request);
        if(token != null){
            if(jwtTokenProvider.validateToken(token)){
                RestApiException restApiException = new RestApiException();
                restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
                restApiException.setErrorMessage("이미로그인 되어있습니다.");
                return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(jwtTokenProvider.createToken(requestSignInDto.getUsername()), HttpStatus.OK);
    }
}
