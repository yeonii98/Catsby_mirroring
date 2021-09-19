package org.techtown.catsby.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlInfoTimeAdapter;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.ApiResponse;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlFeedList;
import org.techtown.catsby.retrofit.dto.BowlLocation;
import org.techtown.catsby.retrofit.service.BowlService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BowlActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private GoogleMap mgoogleMap;

    private ImageView imageView;
    private TextView bowlName, bowlLocation;
    private Button completedFeed;

    private Boolean isPermission = true;
    private static final int PICK_FROM_ALBUM = 1;

    RecyclerView recyclerView;
    BowlInfoTimeAdapter adapter;

    private NotificationService notificationService;
    private BowlService bowlService;

    Long bowlId;
    String name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowl);

        imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.ic_launcher_background);
        bowlName = (TextView) findViewById(R.id.txt_bowl_name);
        bowlLocation = (TextView) findViewById(R.id.txt_bowl_location);

        Intent intent = getIntent();
        bowlId = Long.valueOf(intent.getIntExtra("id", 0));
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");

        bowlName.setText(name);
        bowlLocation.setText(address);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        notificationService = RetrofitClient.getNotificationService();
        completedFeed = (Button) findViewById(R.id.btn_completed_feed);
        completedFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getUid();
                Log.e("FragmentInfo", uid);
                sendNotification(bowlId, uid);
            }
        });

        findViewById(R.id.btn_bowlpic_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

        bowlService = RetrofitClient.getBowlService();
        adapter = new BowlInfoTimeAdapter(new ArrayList<>());
        recyclerView = (RecyclerView) findViewById(R.id.bowlinfo_time_recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;
        recyclerView.setAdapter(adapter);

        loadBowlFeedTime(bowlId);
    }

    private void sendNotification(Long bowlId, String uid) {
        notificationService.sendNotification(bowlId, uid).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("FragmentBowlInfo", "send Notification " + response.body().getResponse());
                    Toast.makeText(getApplicationContext(), "밥그릇에 급여 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    loadBowlFeedTime(bowlId);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("FragmentBowlInfo", "error send notification from API");
            }
        });
    }

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        bowlService.getBowlLocation(bowlId).enqueue(new Callback<BowlLocation>() {

            @Override
            public void onResponse(Call<BowlLocation> call, Response<BowlLocation> response) {
                if (response.isSuccessful()) {
                    mgoogleMap = googleMap;
                    LatLng place = new LatLng(response.body().getLatitude(), response.body().getLongitude());
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(place); //좌표
                    marker.title(response.body().getName());

                    mgoogleMap.addMarker(marker);

                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));

                }
            }

            @Override
            public void onFailure(Call<BowlLocation> call, Throwable t) {

            }
        });
    }
}