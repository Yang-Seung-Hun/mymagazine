package com.example.mymagazine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePostUpdateDto {

    private Long user_id;
    private Long post_id;

    //post정보
    private String title;
    private String content;
    private String create_date;
    private String modified_date;

    //user정보
    private String username;
    private String name;
    private String nickname;

    //like정보
    private int like_cnt;
    private Boolean like_ok;

    public ResponsePostUpdateDto(Long user_id, Long post_id, String title, String content, String create_date, String modified_date,
                               String username, String name, String nickname, int like_cnt, Boolean like_ok) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.create_date = create_date;
        this.modified_date = modified_date;
        this.username = username;
        this.name = name;
        this.nickname = nickname;
        this.like_cnt = like_cnt;
        this.like_ok = like_ok;
    }

}
