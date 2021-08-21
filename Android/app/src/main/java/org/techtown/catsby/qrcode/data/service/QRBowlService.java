package org.techtown.catsby.qrcode.data.service;

import org.techtown.catsby.qrcode.data.model.BowlResponse;
import org.techtown.catsby.qrcode.data.model.BowlUserRequest;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface QRBowlService {

    @Multipart
    @POST("/bowl")
    Call<BowlResponse> saveBowl(@PartMap Map<String, RequestBody> bowl, @Part MultipartBody.Part files);

    @POST("/bowl/{uid}")
    Call<BowlResponse> saveBowlUser(@Path("uid") String uid, @Body BowlUserRequest request);
}