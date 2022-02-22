package com.example.mymagazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestRegisterPostsDto {

    private String title;
    private String content;

    public RequestRegisterPostsDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
