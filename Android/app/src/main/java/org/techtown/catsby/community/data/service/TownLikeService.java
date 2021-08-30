package org.techtown.catsby.community.data.service;

import org.techtown.catsby.community.data.model.TownComment;
import org.techtown.catsby.community.data.model.TownLike;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TownLikeService {

    @POST("townCommunity/{id}/like/{uid}")
    Call<Void> postTownLike(@Path("id") int id, @Path("uid") String uid, @Body TownLike townLike);

    @DELETE("townCommunity/{id}/like/{uid}")
    Call<Void> deleteTownLike(@Path("id") int id, @Path("uid") String uid);

    @GET("townCommunity/{id}/like/{uid}")
    Call<Integer> getTownLike(@Path("id") int id, @Path("uid") String uid);
}
