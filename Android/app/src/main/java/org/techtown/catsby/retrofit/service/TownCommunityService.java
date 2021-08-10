package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.MyComment;
import org.techtown.catsby.retrofit.dto.MyPost;
import org.techtown.catsby.retrofit.dto.TownCommunity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TownCommunityService {

    @GET("townCommunity")
    Call<List<TownCommunity>> getTownList();
}
