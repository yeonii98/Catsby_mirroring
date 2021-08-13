package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.CatProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatService {


    @GET("cat")
    Call<List<CatProfile>> getCatProfile();


}
