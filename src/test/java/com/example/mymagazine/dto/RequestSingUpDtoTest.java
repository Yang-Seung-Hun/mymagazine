package com.example.mymagazine.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestSingUpDtoTest {

    @Autowired
    private RequestSingUpDto requestSingUpDto = new RequestSingUpDto();

    @Test
    public void is_validate_username(){
        //given
        String username1 = "12";
        String username2 = "1a23!@#";
        String username3 = "abc2";
        //when
        Boolean result1 = requestSingUpDto.isValidateUsername(username1);
        Boolean result2 = requestSingUpDto.isValidateUsername(username2);
        Boolean result3 = requestSingUpDto.isValidateUsername(username3);
        //then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
        assertThat(result3).isTrue();
    }

    @Test
    public void is_same_password(){
        //given
        String password1 = "12345";
        String password2 = "123456";
        String password3 = "12345";

        //when
        Boolean result1 = requestSingUpDto.isSame(password1, password2);
        Boolean result2 = requestSingUpDto.isSame(password2, password3);
        Boolean result3 = requestSingUpDto.isSame(password1, password3);

        //then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
        assertThat(result3).isTrue();
    }

    @Test
    public void is_validate_password(){


        String username = "abc";
        String password1 = "12";
        String password2 = "abc12";
        String password3 = "12345";

        Boolean result1 = requestSingUpDto.isValidatePassword(username, password1);
        Boolean result2 = requestSingUpDto.isValidatePassword(username, password2);
        Boolean result3 = requestSingUpDto.isValidatePassword(username, password3);


        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
        assertThat(result3).isTrue();

    }

}