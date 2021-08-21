package org.techtown.catsby.setting.data.service;

import org.techtown.catsby.setting.data.model.MyComment;
import org.techtown.catsby.setting.data.model.MyPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyWritingService {

    @GET("myPost/{uid}")
    Call<List<MyPost>> getMyPosts(@Path("uid") String uid);

    @GET("myComment/{uid}")
    Call<List<MyComment>> getMyComments(@Path("uid") String uid);
}
