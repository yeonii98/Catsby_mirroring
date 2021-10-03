package com.hanium.catsby.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserAddressResponse{
    private Long id;
    private String address;
}
