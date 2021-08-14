package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.Bowl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BowlService {

    @GET("bowls")
    Call<List<Bowl>> getBowls();

    @POST("bowl/enroll")
    Call<Void> postBowl(@Body Bowl bowl);
}
