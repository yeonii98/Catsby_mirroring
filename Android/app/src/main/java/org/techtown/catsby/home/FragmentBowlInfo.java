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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlInfoTimeAdapter;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.ApiResponse;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.dto.BowlFeedList;
import org.techtown.catsby.retrofit.service.BowlService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBowlInfo extends Fragment {
    private View view;
    private ImageView imageView;
    private TextView name, location;
    private Button completedFeed;
    private NotificationService notificationService;
    private BowlService bowlService;

    Long bowlId;

    RecyclerView recyclerView;
    BowlInfoTimeAdapter adapter;

    public static FragmentBowlInfo newInstance() {
        FragmentBowlInfo fragInfo = new FragmentBowlInfo();
        return fragInfo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bowlinfo, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.ic_launcher_background);
        name = (TextView)view.findViewById(R.id.txt_bowl_name);
        location = (TextView) view.findViewById(R.id.txt_bowl_location);

        Bundle bundle = this.getArguments();
        bowlId = bundle.getLong("bowlId");
        name.setText(bundle.getString("name"));
        location.setText(bundle.getString("address"));

        notificationService = RetrofitClient.getNotificationService();
        completedFeed = (Button) view.findViewById(R.id.btn_completed_feed);
        completedFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getUid();
                Log.e("FragmentInfo", uid);
                sendNotification(bowlId, uid);
            }
        });

        bowlService = RetrofitClient.getBowlService();
        adapter = new BowlInfoTimeAdapter(new ArrayList<>());
        recyclerView = (RecyclerView)view.findViewById(R.id.bowlinfo_time_recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)) ;
        recyclerView.setAdapter(adapter);

        loadBowlFeedTime(bowlId);

        return view;
    }

    private void sendNotification(Long bowlId, String uid) {
        notificationService.sendNotification(bowlId, uid).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("FragmentBowlInfo", "send Notification " + response.body().getResponse());
                    loadBowlFeedTime(bowlId);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("FragmentBowlInfo", "error send notification from API");
            }
        });
    }

    private void loadBowlFeedTime(Long bowlId) {
        bowlService.getBowlFeed(bowlId).enqueue(new Callback<BowlFeedList>() {

            @Override
            public void onResponse(Call<BowlFeedList> call, Response<BowlFeedList> response) {
                if(response.isSuccessful()) {
                    adapter.loadBowlFeedTime(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BowlFeedList> call, Throwable t) {
                Log.e("FragmentBowlInfo", "Response Fail" + t.getMessage());

            }
        });
    }
}