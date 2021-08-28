package org.techtown.catsby.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlInfoTimeAdapter;
import org.techtown.catsby.home.model.BowlInfoTimeItem;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.ApiResponse;
import org.techtown.catsby.notification.data.service.NotificationService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBowlInfo extends Fragment {
    private View view;
    private ImageView imageView;
    private TextView name, time, location;
    private Button completedFeed;
    private NotificationService notificationService;

    RecyclerView timeRecyclerView;
    BowlInfoTimeAdapter bowlInfoTimeAdapter = null;

    ArrayList<BowlInfoTimeItem> itemTimeList;
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

        //Intent intent = getIntent();

        Bundle bundle = this.getArguments();
        name = (TextView)view.findViewById(R.id.name);

        if (bundle != null) {
            name.setText(bundle.getString("name"));
        } else {
            name.setText("밥그릇~^^");
        }


        //time = (TextView) view.findViewById(R.id.time);
        //time.setText("10분 전");

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


        /*
        *
        * Time RecyclerView
        *
        * */

        timeRecyclerView = (RecyclerView)view.findViewById(R.id.bowlinfo_time_recycler_view);
        itemTimeList = new ArrayList<>();

        bowlInfoTimeAdapter = new BowlInfoTimeAdapter(itemTimeList);
        timeRecyclerView.setAdapter(bowlInfoTimeAdapter);

        timeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)) ;
        addItem("여기에 추가하기면 됩니다");
        addItem("여기에 추가하기면 됩니다");
        addItem("여기에 추가하기면 됩니다");
        bowlInfoTimeAdapter.notifyDataSetChanged();

        return view;
    }

    private void addItem(String ptext) {
        BowlInfoTimeItem timeItem = new BowlInfoTimeItem();
        timeItem.setTimeItem(ptext);

        System.out.println("Text = " + ptext);
        itemTimeList.add(timeItem);
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