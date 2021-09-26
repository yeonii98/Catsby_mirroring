package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.NicknameResponse;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.dto.UserAddressUpdate;
import org.techtown.catsby.retrofit.dto.UserImageUpdate;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("/user/{uid}")
    Call<User> getUser(@Path("uid") String uid);

    @PUT("/user/address/{uid}")
    Call<Void> putUser(
            @Path("uid") String uid,
            @Body UserAddressUpdate userAddressUpdate);

    @PATCH("/user/nickname/{uid}")
    Call<NicknameResponse> updateNickname(@Path("uid") String uid, @Query("nickname") String nickname);

    @PUT("/user/image/{uid}")
    Call<Void> updateUserImage(
            @Path("uid") String uid,
            @Body UserImageUpdate userImageUpdate);

}
