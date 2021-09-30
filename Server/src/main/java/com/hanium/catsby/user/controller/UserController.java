package com.hanium.catsby.user.controller;

import com.hanium.catsby.user.domain.Users;
import com.hanium.catsby.user.repository.UserRepository;
import com.hanium.catsby.user.service.UserService;
import com.hanium.catsby.util.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public BaseResponse createUser(@RequestBody CreateUserRequest request) {
        if (!userRepository.findUserToChkByUid(request.getUid()).isEmpty()) {
            return new BaseResponse("Already User Is Saved");
        } else {
            userService.savaUser(request.getUid(), request.getEmail(), request.getFcmToken());
            return new BaseResponse("success");
        }
    }

    @Data
    static class CreateUserRequest{
        private String uid;
        private String email;
        private String fcmToken;
    }

    @GetMapping("/user/{uid}")
    public Users findUser(@PathVariable("uid") String uid) {
        Users user = userService.findUsersByUid(uid);
        return user;
    }

    @PutMapping("/user/address/{uid}")
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

    @PutMapping("/user/image/{uid}")
    public UpdateUserImageResponse UpdateUserImageResponse(@PathVariable("uid") String uid, @RequestBody UpdateUserImageRequest request) {
        userService.updateImage(uid, request.getImage());
        Users findUser = userService.findUsersByUid(uid);
        return new UpdateUserImageResponse(findUser.getId(), findUser.getAddress());
    }

    @Data
    static class UpdateUserAddressRequest{
        private Long id;
        private String address;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserAddressResponse{
        private Long id;
        private String address;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserNicknameResponse{
        private Long id;
        private String nickname;
    }

    @Data
    static class UpdateUserImageRequest{
        private Long id;
        private String image;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserImageResponse{
        private Long id;
        private String image;
    }

    @PatchMapping("/user/token/{uid}")
    public ResponseEntity<BaseResponse> updateFCMToken(@PathVariable("uid") String uid, @RequestBody UpdateFcmTokenRequest request) {
        userService.updateFcmToken(uid, request.getFcmToken());
        return ResponseEntity.ok(new BaseResponse("success"));
    }

    @Data
    private static class UpdateFcmTokenRequest {
        private String fcmToken;
    }
}
