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

    @GetMapping("/users")
    public List<Users> users() {
        return userService.findUsers();
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

    @PutMapping("/user/nickname/{uid}")
    public UpdateUserNicknameResponse updateUserNicknameResponse(@PathVariable("uid") String uid, @RequestBody UpdateUserNicknameRequest request) {
        userService.updateNickname(uid, request.getNickname());
        Users findUser = userService.findUsersByUid(uid);
        return new UpdateUserNicknameResponse(findUser.getId(), findUser.getNickname());
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
    static class UpdateUserNicknameRequest{
        private Long id;
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserNicknameResponse{
        private Long id;
        private String nickname;
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
