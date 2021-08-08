package com.hanium.catsby.auth.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.hanium.catsby.auth.service.AuthService;
import lombok.AllArgsConstructor;
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
        String customToken = authService.createCustomToken(accessToken);
        return ResponseEntity.ok(new AuthResponse(customToken));
    }

    @Data
    static class AuthRequest {
        private String accessToken;
    }

    @Data
    @AllArgsConstructor
    static class AuthResponse {
        private String customToken;
    }
}
