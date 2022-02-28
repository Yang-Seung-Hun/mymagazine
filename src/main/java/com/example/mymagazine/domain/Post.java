package com.example.mymagazine.domain;

import com.example.mymagazine.dto.RequestPostUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String img_url;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String img_url, String contents, User user) {
        this.img_url = img_url;
        this.contents = contents;
        this.user = user;
    }

    public void update(RequestPostUpdateDto requestPostUpdateDto){
        this.img_url = requestPostUpdateDto.getImg_url();
        this.contents = requestPostUpdateDto.getContents();
    }
}
