package com.example.mymagazine.security;

import com.example.mymagazine.jwt.JwtAuthenticationFilter;
import com.example.mymagazine.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/api/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests();

        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다


//        http.csrf().disable();
//
//        http.authorizeRequests()
//// 어떤 요청이든 '인증'
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//// 로그인 기능 허용
//                .formLogin()
//                .loginPage("/api/signin")//여기가 처음에 get으로 화면 보여주는곳
//                .loginProcessingUrl("/api/signin")//그담에 포스트
//                .defaultSuccessUrl("/")//로그인 성공하면 여길로가니까
//                .failureUrl("/api/signinError")
//                .permitAll()
//                .and()
//// 로그아웃 기능 허용
//                .logout()
//                .permitAll();
    }
}
