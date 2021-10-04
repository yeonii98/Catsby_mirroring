package org.techtown.catsby.community.data.service;

import org.techtown.catsby.community.data.model.TownCommunity;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface TownCommunityService {

    @GET("townCommunity/{uid}")
    Call<List<TownCommunity>> getTownList(@Path("uid") String uid);

    @Multipart
    @POST("townCommunity/write/{uid}")
    Call<TownCommunity> postTown(@Part MultipartBody.Part file, @PartMap HashMap<String, RequestBody> content, @Path("uid") String uid);

    @Multipart
    @PUT("townCommunity/{id}")
    Call<TownCommunity> putTown(@Part MultipartBody.Part file, @PartMap HashMap<String, RequestBody> content, @Path("id") int id);

    @DELETE("townCommunity/{id}")
    Call<Void> deleteTown(@Path("id") int id);
}
