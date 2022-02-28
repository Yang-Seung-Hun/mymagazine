package com.example.mymagazine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestRegisterPostsDto {

    private String img_url;
    private String contents;

    public RequestRegisterPostsDto(String img_url, String contents) {
        this.img_url = img_url;
        this.contents = contents;
    }
}
