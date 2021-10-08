package com.hanium.catsby.domain.user.dto;

import lombok.Data;

@Data
public class CreateUserRequest{
    private String uid;
    private String email;
    private String fcmToken;
}
