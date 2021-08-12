package org.techtown.catsby.notification;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

public class FMCService extends FirebaseMessagingService {

    private String TAG = "MessagingService";

    @Override
    public void onNewToken(@NonNull @NotNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        super.onNewToken(token);
    }

    public void getFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d("token", token);
                    }
                });
    }
}
