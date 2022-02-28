package com.example.mymagazine.dto;


import lombok.*;

@Getter
@Setter
public class RequestSingUpDto {

    private String username;//회원 아이디
    private String name;
    private String password;
    private String check_password;

    //     - 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
    //     - 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기

    public Boolean isSame(String password, String check_password){
        if (password.equals(check_password)){
            return true;
        }
        return false;
    }

    public Boolean isValidateUsername(String username){
        if(username.length() <3){
            return false;
        }

        for(int i=0; i<username.length(); i++){
            if(!Character.isAlphabetic(username.charAt(i))){
                if(!Character.isDigit(username.charAt(i))){
                    return false;
                }
                else{
                    if( Character.getNumericValue(username.charAt(i)) > 9 ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Boolean isValidatePassword(String username, String password){

        if(password.length() <4){
            return false;
        }

        if (password.contains(username)){
            return false;
        }
        return true;
    }
}
