package org.techtown.catsby.notification;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;
import org.techtown.catsby.notification.data.model.FCMToken;
import org.techtown.catsby.retrofit.ApiResponse;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.RetrofitClient;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FMCService extends FirebaseMessagingService {

    private NotificationService notificationService;

    @Override
    public void onNewToken(@NonNull @NotNull String token) {
        Log.d("FMCService", "Refreshed token: " + token);

        notificationService = RetrofitClient.getNotificationService();
        notificationService.updateFCMToken(FirebaseAuth.getInstance().getUid(), new FCMToken(token))
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if(response.isSuccessful()) {
                            Log.d("FCMService", "update fcm token  " + response.body().getResponse());

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Log.d("FCMService", "error loading from API " + t.getMessage());
                    }
                });
        super.onNewToken(token);
    }

}
