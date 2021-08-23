package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommentUpdate;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlCommunityPost;
import org.techtown.catsby.retrofit.dto.BowlCommunityUpdatePost;
import org.techtown.catsby.retrofit.dto.BowlLike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BowlCommunityService {

    @POST("bowl-community/write/{bowlId}/{uid}")
    Call<List<BowlCommunity>> saveCommunity(@Path("bowlId") int bowlId, @Path("uid") String uid, @Body BowlCommunityPost bowlCommunityPost);

    @GET("bowl-communities/{uid}")
    Call<List<BowlCommunity>> getCommunities(@Path("uid") String uid);

    @DELETE("/bowl-community/{communityId}")
    Call<Void> deleteCommunity(@Path("communityId") long communityId);

    @PUT("/bowl-community/{communityId}")
    Call<Void> updateCommunity(@Path("communityId") int communityId, @Body BowlCommunityUpdatePost bowlCommunityUpdatePost);

    //@GET("/bowl-communities/like/{communityId}")
    //Call<Long> getLikes(@Path("communityId") Long communityId);

    @POST("/bowl-comment/{uid}/{communityId}")
    Call<List<BowlComment>> saveComment(@Path("uid") String uid, @Path("communityId") int communityId, @Body BowlCommentPost bowlCommentPost);


    @GET("bowl-comments/{communityId}")
    Call<List<BowlComment>> getComments(@Path("communityId") long communityId);

    @PUT("/bowl-comment/{commentId}")
    Call<Void> putComment(@Path("commentId") long commentId, @Body BowlCommentUpdate bowlCommentUpdate);

    @DELETE("/bowl-comment/{commentId}")
    Call<Void> deleteComment(@Path("commentId") long commentId);



}
