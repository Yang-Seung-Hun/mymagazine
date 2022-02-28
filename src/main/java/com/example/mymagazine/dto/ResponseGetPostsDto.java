package com.example.mymagazine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetPostsDto {

    private Long user_id;
    private Long post_id;
    private String contents;
    private String img_url;
    private int like_cnt;
    private String create_date;
    private String modified_date;
    private Boolean like_ok;

    public ResponseGetPostsDto(Long user_id, Long post_id, String contents, String img_url, int like_cnt, String create_date, String modified_date, Boolean like_ok) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.contents = contents;
        this.img_url = img_url;
        this.like_cnt = like_cnt;
        this.modified_date = modified_date;
        this.create_date = create_date;
        this.like_ok = like_ok;
    }
}
