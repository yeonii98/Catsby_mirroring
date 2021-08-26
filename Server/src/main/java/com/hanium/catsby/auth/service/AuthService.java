package com.hanium.catsby.auth.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hanium.catsby.auth.dto.AuthResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.firebase.auth.UserRecord.*;

@Service
public class AuthService {

    // firebase custom token 생성
    public AuthResponse createCustomToken(String accessToken) throws FirebaseAuthException {
        AuthResponse response = new AuthResponse();

        String email = getKakaoEmail(accessToken);
        if (email == null)
            return null;

        String uid = createUser(email);
        if (uid == null) {
            // 기존 사용자 uid
            uid = FirebaseAuth.getInstance().getUserByEmail(email).getUid();
            response.setSaved(true);
        } else {
            response.setSaved(false);
        }

        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        response.setCustomToken(customToken);
        return response;
    }

    public String getKakaoEmail(String accessToken) {
        String requestUrl = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonObject kakaoAccount = parser.parse(result).getAsJsonObject().get("kakao_account").getAsJsonObject();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

            return email;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // firebase 인증 사용자 생성
    public String createUser(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return null;
        } catch (FirebaseAuthException e) {
            CreateRequest request = new CreateRequest().setEmail(email);
            try {
                return FirebaseAuth.getInstance().createUser(request).getUid();
            } catch (FirebaseAuthException ex) {
                ex.printStackTrace();
            }
        }
        return  null;
    }
}
