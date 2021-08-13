package org.techtown.catsby.login.data.service;

import org.techtown.catsby.login.data.model.LoginRequest;
import org.techtown.catsby.login.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth/kakao")
    Call<LoginResponse> getCustomToken(@Body LoginRequest loginRequest);
}
