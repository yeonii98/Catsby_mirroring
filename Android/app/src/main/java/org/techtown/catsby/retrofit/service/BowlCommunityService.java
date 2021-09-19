package org.techtown.catsby.retrofit.service;

import org.techtown.catsby.retrofit.dto.BowlComment;
import org.techtown.catsby.retrofit.dto.BowlCommentPost;
import org.techtown.catsby.retrofit.dto.BowlCommentUpdate;
import org.techtown.catsby.retrofit.dto.BowlCommunity;
import org.techtown.catsby.retrofit.dto.BowlCommunityPost;
import org.techtown.catsby.retrofit.dto.BowlCommunityUpdatePost;
import org.techtown.catsby.retrofit.dto.BowlLike;
import org.techtown.catsby.retrofit.dto.BowlLikeResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface BowlCommunityService {

    @Multipart
    @POST("bowl-community/write/{bowlId}/{uid}")
    Call<List<BowlCommunity>> saveCommunity(@Part MultipartBody.Part file,  @Path("bowlId") int bowlId, @Path("uid") String uid, @PartMap HashMap<String, RequestBody> content);

    @GET("/bowl-communities/{bowlId}")
    Call<List<BowlCommunity>> getCommunitiesByBowl(@Path("bowlId") int bowlId);

    @DELETE("/bowl-community/{communityId}")
    Call<Void> deleteCommunity(@Path("communityId") long communityId);

    @PUT("/bowl-community/{communityId}")
    Call<Void> updateCommunity(@Path("communityId") int communityId, @Body BowlCommunityUpdatePost bowlCommunityUpdatePost);

    @GET("/bowl-like/community/{communityId}")
    Call<Integer> getTotalLikes(@Path("communityId") int communityId);

    @POST("/bowl-comment/{uid}/{communityId}")
    Call<List<BowlComment>> saveComment(@Path("uid") String uid, @Path("communityId") int communityId, @Body BowlCommentPost bowlCommentPost);

    @GET("bowl-comments/{communityId}")
    Call<List<BowlComment>> getComments(@Path("communityId") long communityId);

    @PUT("/bowl-comment/{commentId}")
    Call<Void> putComment(@Path("commentId") long commentId, @Body BowlCommentUpdate bowlCommentUpdate);

    @DELETE("/bowl-comment/{commentId}")
    Call<Void> deleteComment(@Path("commentId") long commentId);

    @POST("/bowl-like/{uid}/{communityId}")
    Call<BowlLikeResponse> saveLike(@Path("uid") String uid, @Path("communityId") int communityId);

    @GET("/bowl-likes/{uid}")
    Call<List<BowlLike>> getLikes(@Path("uid") String userId);

    @DELETE("/bowl-like/{likeId}")
    Call<Void> deleteLike(@Path("likeId") long likeId);

}
