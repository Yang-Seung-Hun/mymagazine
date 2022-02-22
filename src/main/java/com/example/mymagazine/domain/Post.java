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

    private String title;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public void update(RequestPostUpdateDto requestPostUpdateDto){
        this.title = requestPostUpdateDto.getTitle();
        this.contents = requestPostUpdateDto.getContent();
    }
}
