package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.BowlFeedList;
import org.techtown.catsby.retrofit.dto.BowlInfo;
import org.techtown.catsby.retrofit.dto.BowlList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface BowlService {

    @GET("/bowls/{uid}")
    Call<BowlList> getBowls(@Path("uid") String uid);

    @GET("/bowl/feed/{bowlId}")
    Call<BowlFeedList> getBowlFeed(@Path("bowlId") Long id);

    @Multipart
    @PATCH("/bowl/image/{bowlId}/{uid}")
    Call<Void> updateImage(@Path("bowlId") Long id, @Path("uid") String uid, @Part MultipartBody.Part file);

    @GET("/bowl/info/{bowlId}/{uid}")
    Call<BowlInfo> getBowlInfo(@Path("bowlId") Long id, @Path("uid") String uid);
}

