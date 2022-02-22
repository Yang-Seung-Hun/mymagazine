package com.example.mymagazine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
// 어떤 요청이든 '인증'
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
// 로그인 기능 허용
                .formLogin()
                .loginPage("/api/signin")
                .loginProcessingUrl("/api/signin")
                .defaultSuccessUrl("/")
                .failureUrl("/api/signinError")
                .permitAll()
                .and()
// 로그아웃 기능 허용
                .logout()
                .permitAll();
    }
}
