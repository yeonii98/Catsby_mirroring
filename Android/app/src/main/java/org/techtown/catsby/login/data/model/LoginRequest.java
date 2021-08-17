package org.techtown.catsby.login.data.model;

public class LoginRequest {

    private String accessToken;

    public LoginRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
