package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlLike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BowlCommunityService {

    @POST("bowl-community/write/{uid}")
    Call<List<BowlCommunity>> saveCommunity(@Path("uid") String uid, @Body BowlCommunity bowlCommunity);

    @GET("bowl-communities/{uid}")
    Call<List<BowlCommunity>> getCommunities(@Path("uid") String uid);

    @GET("/bowl-communities/like/{communityId}")
    Call<Long> getLikes(@Path("communityId") Long communityId);

    @POST("/bowl-comment/{uid}/{communityId}")
    Call<List<BowlComment>> saveComment(@Path("uid") String uid, @Path("communityId") int communityId, @Body BowlCommentPost bowlCommentPost);

    @GET("bowl-comments/{communityId}")
    Call<List<BowlComment>> getComments(@Path("communityId") int communityId);


}
