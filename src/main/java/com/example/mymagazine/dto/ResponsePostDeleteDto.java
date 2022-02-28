package com.example.mymagazine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePostDeleteDto {

    private Long user_id;
    private Long post_id;

    //post정보
    private String img_url;
    private String contents;
    private String create_date;
    private String modified_date;

    //user정보
    private String username;
    private String name;

    //like정보
    private int like_cnt;
    private Boolean like_ok;

    public ResponsePostDeleteDto(Long user_id, Long post_id, String img_url, String contents, String create_date, String modified_date,
                               String username, String name, int like_cnt, Boolean like_ok) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.img_url = img_url;
        this.contents = contents;
        this.create_date = create_date;
        this.modified_date = modified_date;
        this.username = username;
        this.name = name;
        this.like_cnt = like_cnt;
        this.like_ok = like_ok;
    }
}
