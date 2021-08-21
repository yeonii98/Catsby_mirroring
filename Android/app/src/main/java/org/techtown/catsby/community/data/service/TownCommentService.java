package org.techtown.catsby.community.data.service;

import org.techtown.catsby.community.data.model.TownComment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TownCommentService {

    @POST("townCommunity/{id}/comment/{uid}")
    Call<Void> postTownComment(@Path("id") int id,@Path("uid") String uid, @Body TownComment townComment);

    @PUT("townCommunity/{id}/comment/{townComment_id}")
    Call<Void> putTownComment(@Path("id") int id, @Path("townComment_id") int townComment_id, @Body TownComment townComment);

    @DELETE("townCommunity/{id}/comment/{townComment_id}")
    Call<Void> deleteTownComment(@Path("id") int id, @Path("townComment_id") int townComment_id);
}
