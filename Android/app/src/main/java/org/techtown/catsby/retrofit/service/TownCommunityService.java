package org.techtown.catsby.retrofit.service;

import com.google.gson.annotations.SerializedName;

import org.techtown.catsby.retrofit.dto.BowlDto;
import org.techtown.catsby.retrofit.dto.MyComment;
import org.techtown.catsby.retrofit.dto.MyPost;
import org.techtown.catsby.retrofit.dto.TownCommunity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TownCommunityService {

    @GET("townCommunity")
    Call<List<TownCommunity>> getTownList();

    @POST("townCommunity/write")
    Call<Void> postTown(@Body TownCommunity townCommunity);
}
