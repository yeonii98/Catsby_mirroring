package com.hanium.catsby.notification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    Long userId;
    String token;
}
