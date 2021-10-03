package com.hanium.catsby.domain.user.controller;

import com.hanium.catsby.domain.common.sevice.S3Service;
import com.hanium.catsby.domain.user.dto.*;
import com.hanium.catsby.domain.user.model.Users;
import com.hanium.catsby.domain.user.repository.UserRepository;
import com.hanium.catsby.domain.user.service.UserService;
import com.hanium.catsby.domain.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final static String USER_DIR_NAME = "image/user/";

    private final UserService userService;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @PostMapping("/register")
    public BaseResponse createUser(@RequestBody CreateUserRequest request) {
        if (!userRepository.findUserToChkByUid(request.getUid()).isEmpty()) {
            return new BaseResponse("Already User Is Saved");
        } else {
            userService.savaUser(request.getUid(), request.getEmail(), request.getFcmToken());
            return new BaseResponse("success");
        }
    }

    @GetMapping("/user/{uid}")
    public Users findUser(@PathVariable("uid") String uid) {
        Users user = userService.findUsersByUid(uid);
        return user;
    }

    @PatchMapping("/user/address/{uid}")
    public UpdateUserAddressResponse updateUserAddressResponse(@PathVariable("uid") String uid, @RequestBody UpdateUserAddressRequest request) {
        userService.updateAddress(uid, request.getAddress());
        Users findUser = userService.findUsersByUid(uid);
        return new UpdateUserAddressResponse(findUser.getId(), findUser.getAddress());
    }

    @PatchMapping("/user/nickname/{uid}")
    public UpdateUserNicknameResponse updateUserNicknameResponse(@PathVariable("uid") String uid, @RequestParam String nickname) {
        userService.updateNickname(uid, nickname);
        Users findUser = userService.findUsersByUid(uid);
        return new UpdateUserNicknameResponse(findUser.getId(), findUser.getNickname());
    }

    @PatchMapping("/user/image/{uid}")
    public UpdateUserImageResponse UpdateUserImageResponse(@PathVariable("uid") String uid, @RequestPart MultipartFile file) {
        String imgUrl = s3Service.upload(file, USER_DIR_NAME, uid);
        userService.updateImage(uid, imgUrl);
        Users findUser = userService.findUsersByUid(uid);
        return new UpdateUserImageResponse(findUser.getId(), findUser.getAddress());
    }

    @PatchMapping("/user/token/{uid}")
    public ResponseEntity<BaseResponse> updateFCMToken(@PathVariable("uid") String uid, @RequestBody UpdateFcmTokenRequest request) {
        userService.updateFcmToken(uid, request.getFcmToken());
        return ResponseEntity.ok(new BaseResponse("success"));
    }
}
