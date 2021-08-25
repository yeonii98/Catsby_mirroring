package org.techtown.catsby.community.data.service;

import org.techtown.catsby.community.data.model.TownCommunity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TownCommunityService {

    @GET("townCommunity")
    Call<List<TownCommunity>> getTownList();

    @GET("townCommunity/{id}")
    Call<TownCommunity> getTown(@Path("id") int id);

    @POST("townCommunity/write/{uid}")
    Call<Void> postTown(@Body TownCommunity townCommunity, @Path("uid") String uid);

    @PUT("townCommunity/{id}")
    Call<Void> putTown(@Path("id") int id, @Body TownCommunity townCommunity);

    @DELETE("townCommunity/{id}")
    Call<Void> deleteTown(@Path("id") int id);
}
