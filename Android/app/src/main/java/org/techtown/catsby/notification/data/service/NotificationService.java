package org.techtown.catsby.notification.data.service;

import org.techtown.catsby.notification.data.model.FCMToken;
import org.techtown.catsby.notification.data.model.NotificationList;
import org.techtown.catsby.retrofit.ApiResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationService {

    @GET("/notification/send/{bowlId}/{uid}")
    Call<ApiResponse> sendNotification(@Path("bowlId") Long bowlId, @Path("userId") String uid);

    @GET("/notification/{uid}")
    Call<NotificationList> getNotifications(@Path("uid") String uid, @Query("page") int page);

    @PATCH("/user/token/{uid}")
    Call<ApiResponse> updateFCMToken(@Path("uid") String uid, @Body FCMToken toekn);
}
