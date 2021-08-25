package org.techtown.catsby.retrofit.service;

import android.media.Image;

import org.techtown.catsby.retrofit.dto.CatProfile;

import java.sql.Blob;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CatService {

    @GET("cat/{cat_id}")
    Call<CatProfile> getCatProfile(
            @Path("cat_id") int cat_id
    );

    @GET("cat")
    Call<List<CatProfile>> getCatProfileList(
    );

    @FormUrlEncoded
    @POST("cat/register")
    Call<CatProfile> setPost(
            @Field("name") String name,
            @Field("health") String health,
            @Field("address") String address,
            @Field("gender") boolean gender,
            @Field("image") String image,
            @Field("content") String content,
            @Field("spayed") boolean spayed
            );



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
