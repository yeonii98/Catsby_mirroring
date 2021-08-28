package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.BowlDto;
import org.techtown.catsby.retrofit.dto.BowlList;
import org.techtown.catsby.retrofit.dto.BowlLocation;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface BowlService {

    @GET("/bowls/{uid}")
    Call<BowlList> getBowls(@Path("uid") String uid);


    @POST("/bowl/enroll")
    Call<Void> postBowl(@Body BowlDto bowlDto);

    @GET("/bowl/location/{bowlId}")
    Call<BowlLocation> getBowlLocation(@Path("bowlId") Long id);

}

