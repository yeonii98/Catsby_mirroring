package org.techtown.catsby.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.ApiResponse;
import org.techtown.catsby.notification.data.service.NotificationService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBowlInfo extends Fragment {
    private View view;
    private ImageView imageView;
    private TextView name, time, location;
    private Button completedFeed;
    private NotificationService notificationService;

    public static FragmentBowlInfo newInstance() {
        FragmentBowlInfo fragInfo = new FragmentBowlInfo();
        return fragInfo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bowlinfo, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ic_launcher_background);

        Bundle bundle = getArguments();

        name = (TextView) view.findViewById(R.id.name);
        if (bundle != null) {
            name.setText(bundle.getString("name"));
        } else {
            name.setText("밥그릇~^^");
        }


        time = (TextView) view.findViewById(R.id.time);
        time.setText("10분 전");

        location = (TextView) view.findViewById(R.id.location);
        location.setText("남산타워");

        notificationService = RetrofitClient.getNotificationService();
        completedFeed = (Button) view.findViewById(R.id.btn_completed_feed);
        completedFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendNotification(bowlId, FirebaseAuth.getInstance().getUid());
            }
        });
        return view;
    }

    private void sendNotification(Long bowlId, String uid) {
        notificationService.sendNotification(bowlId, uid).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("FragmentBowlInfo", "send Notification " + response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("NotificationActivity", "error send notification from API");
            }
        });
    }
}