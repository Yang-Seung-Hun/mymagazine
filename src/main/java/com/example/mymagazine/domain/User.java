package com.example.mymagazine.domain;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User extends Timestamped{

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String username;//사용자 아이디

    private String name;

    @NotNull
    private String password;

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
}
