package com.hanium.catsby.domain.user.dto;

import lombok.Data;

@Data
public class UpdateUserAddressRequest{
    private Long id;
    private String address;
}