package org.techtown.catsby.qrcode.data.service;

import org.techtown.catsby.qrcode.data.model.Bowl;
import org.techtown.catsby.qrcode.data.model.BowlResponse;
import org.techtown.catsby.qrcode.data.model.BowlUserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QRBowlService {

    @POST("/bowl")
    Call<BowlResponse> saveBowl(@Body Bowl bowl);

    @POST("/bowl/{uid}")
    Call<BowlResponse> saveBowlUser(@Path("uid") String uid, @Body BowlUserRequest request);
}
