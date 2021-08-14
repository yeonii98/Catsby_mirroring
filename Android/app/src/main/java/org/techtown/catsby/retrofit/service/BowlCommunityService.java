package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.Bowl;
import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlLike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BowlCommunityService {

    @GET("bowl-communities")
    Call<List<BowlCommunity>> getCommunities();

    @GET("bowl-comments")
    Call<List<BowlComment>> getComments();

    @GET("bowl-likes")
    Call<List<BowlLike>> getLikes();

}
