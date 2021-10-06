package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.CatProfile;
import org.techtown.catsby.retrofit.dto.CatInfo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CatService {

    @GET("cat/{cat_id}")
    Call<CatProfile> getCatProfile(
            @Path("cat_id") int cat_id
    );

    @GET("cat")
    Call<List<CatProfile>> getCatProfileList(
    );

    @Multipart
    @POST("cat/register/{uid}")
    Call<CatProfile> setPost(@Path("uid") String uid, @Part("catInfo") CatInfo catInfo, @Part MultipartBody.Part file);


    @GET("cat/{cat_id}")
    Call<CatProfile> getCatName(
            @Path("cat_id") int cat_id
    );

/*
    @GET("cat")
    Call<CatProfile> getCatId(
            @Query("cat_id") int cat_id
    );


    @GET("cat/{cat_id}")
    Call<CatProfile> getCatHealth(
            @Query("health") String health
    );

    @GET("cat/{cat_id}")
    Call<CatProfile> getCatAddress(
            @Query("address") String address
    );

    @GET("cat/{cat_id}")
    Call<CatProfile> getGender(
            @Query("gender") Boolean gender
    );

    @GET("cat/{cat_id}")
    Call<CatProfile> getImage(
            @Query("image") Blob image
    );

    @GET("cat/{cat_id}")
    Call<CatProfile> getSpayed(
            @Query("spayed") Boolean spayed
    );

    @GET("cat/{cat_id}")
    Call<CatProfile> getContent(
            @Query("content") String content
    );

    */

}
