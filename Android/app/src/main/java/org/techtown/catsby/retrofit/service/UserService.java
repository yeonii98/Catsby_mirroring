package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.NicknameResponse;
import org.techtown.catsby.retrofit.dto.User;
import org.techtown.catsby.retrofit.dto.UserAddressUpdate;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("/user/{uid}")
    Call<User> getUser(@Path("uid") String uid);

    @PATCH("/user/address/{uid}")
    Call<Void> putUser(
            @Path("uid") String uid,
            @Body UserAddressUpdate userAddressUpdate);

    @PATCH("/user/nickname/{uid}")
    Call<NicknameResponse> updateNickname(@Path("uid") String uid, @Query("nickname") String nickname);

    @Multipart
    @PATCH("/user/image/{uid}")
    Call<Void> updateUserImage(
            @Path("uid") String uid,
            @Part MultipartBody.Part file);

}
