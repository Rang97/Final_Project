package com.example.demo.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

    private Long userId;
    private String loginId;
    private String password;
    private String nickname;
    private Role role;

    public User(String loginId, String password, String nickname, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public enum Role {
        USER,
        ADMIN
    }
}
