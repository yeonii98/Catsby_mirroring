package com.hanium.catsby.auth.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.hanium.catsby.auth.dto.AuthRequest;
import com.hanium.catsby.auth.dto.AuthResponse;
import com.hanium.catsby.auth.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/kakao")
    public ResponseEntity<AuthResponse> authKakao(@RequestBody AuthRequest request) throws FirebaseAuthException {
        String accessToken = request.getAccessToken();
        AuthResponse response = authService.createCustomToken(accessToken);
        return ResponseEntity.ok(response);
    }
}
