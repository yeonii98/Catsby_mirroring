package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.MyComment;
import org.techtown.catsby.retrofit.dto.MyPost;
import org.techtown.catsby.retrofit.dto.TownCommunity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyWritingService {

    @GET("myPost")
    Call<List<MyPost>> getMyPosts();

    @GET("myComment")
    Call<List<MyComment>> getMyComments();
}
