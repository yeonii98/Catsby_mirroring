package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.User;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("/user/{uid}")
    Call<User> getUser(@Path("uid") String uid);



}
