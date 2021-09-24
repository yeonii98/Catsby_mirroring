package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.BowlDetail;
import org.techtown.catsby.retrofit.dto.BowlFeedList;
import org.techtown.catsby.retrofit.dto.BowlImage;
import org.techtown.catsby.retrofit.dto.BowlList;
import org.techtown.catsby.retrofit.dto.BowlLocation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;


public interface BowlService {

    @GET("/bowls/{uid}")
    Call<BowlList> getBowls(@Path("uid") String uid);

    @GET("/bowl/feed/{bowlId}")
    Call<BowlFeedList> getBowlFeed(@Path("bowlId") Long id);

    @PATCH("/bowl/image/{bowlId}/{uid}")
    Call<Void> updateImage(@Path("bowlId") Long id, @Path("uid") String uid, @Body BowlImage image);

    @GET("/bowl/detail/{bowlId}/{uid}")
    Call<BowlDetail> getBowlDetail(@Path("bowlId") Long id, @Path("uid") String uid);
}

