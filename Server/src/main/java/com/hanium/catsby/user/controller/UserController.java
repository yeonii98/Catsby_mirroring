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

    @PutMapping("/user/{id}")
    public UpdateUserResponse updateUserResponse(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request) {
        userService.update(id, request.getNickname(), request.getAddress());
        Users findUser = userService.findUser(id);
        return new UpdateUserResponse(findUser.getId(), findUser.getNickname(), findUser.getAddress());
    }

    @Data
    static class UpdateUserRequest{
        private Long id;
        private String nickname;
        private String address;
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserResponse{
        private Long id;
        private String nickname;
        private String address;
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
