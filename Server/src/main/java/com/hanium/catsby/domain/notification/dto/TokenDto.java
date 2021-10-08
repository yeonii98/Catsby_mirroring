package com.hanium.catsby.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    Long userId;
    String token;
}
