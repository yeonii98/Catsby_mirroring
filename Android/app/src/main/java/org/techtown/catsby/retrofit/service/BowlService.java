package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.BowlDto;
import org.techtown.catsby.retrofit.dto.BowlFeed;
import org.techtown.catsby.retrofit.dto.BowlFeedList;
import org.techtown.catsby.retrofit.dto.BowlList;
import org.techtown.catsby.retrofit.dto.BowlLocation;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface BowlService {

    @GET("/bowls/{uid}")
    Call<BowlList> getBowls(@Path("uid") String uid);

    @GET("/bowl/location/{bowlId}")
    Call<BowlLocation> getBowlLocation(@Path("bowlId") Long id);

    @GET("/bowl/feed/{bowlId}")
    Call<BowlFeedList> getBowlFeed(@Path("bowlId") Long id);
}

