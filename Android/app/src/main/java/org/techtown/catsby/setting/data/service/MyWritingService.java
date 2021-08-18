package org.techtown.catsby.setting.data.service;

import org.techtown.catsby.setting.data.model.MyComment;
import org.techtown.catsby.setting.data.model.MyPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyWritingService {

    @GET("myPost")
    Call<List<MyPost>> getMyPosts();

    @GET("myComment")
    Call<List<MyComment>> getMyComments();
}
