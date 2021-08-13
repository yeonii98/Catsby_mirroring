package org.techtown.catsby.notification.data.service;

import org.techtown.catsby.notification.data.model.NotificationList;
import org.techtown.catsby.notification.data.model.NotificationResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationService {

    @GET("/notification/send/{bowlId}/{userId}")
    Call<NotificationResponse> sendNotification(@Path("bowlId") Long bowlId, @Path("userId") Long userId);

    @GET("/notification/{uid}")
    Call<NotificationList> getNotifications(@Path("uid") String uid, @Query("page") int page);
}
