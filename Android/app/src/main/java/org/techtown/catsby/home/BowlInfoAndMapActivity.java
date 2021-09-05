package org.techtown.catsby.home;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.techtown.catsby.R;
import org.techtown.catsby.home.adapter.BowlInfoTimeAdapter;
import org.techtown.catsby.home.model.BowlInfoTimeItem;
import org.techtown.catsby.notification.data.service.NotificationService;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlLocation;
import org.techtown.catsby.retrofit.service.BowlService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BowlInfoAndMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private TextView name, time, location;
    private Button completedFeed;
    RecyclerView timeRecyclerView;
    BowlInfoTimeAdapter bowlInfoTimeAdapter = null;
    ArrayList<BowlInfoTimeItem> itemTimeList;
    private NotificationService notificationService;
    private BowlService bowlService = RetrofitClient.getBowlService();
    private GoogleMap mgoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bowlinfoandmap);

        Intent intent = getIntent();

        name = (TextView)findViewById(R.id.name);
        name.setText(intent.getStringExtra("name"));

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        timeRecyclerView = (RecyclerView)findViewById(R.id.bowlinfo_time_recycler_view);
        itemTimeList = new ArrayList<>();

        bowlInfoTimeAdapter = new BowlInfoTimeAdapter(itemTimeList);
        timeRecyclerView.setAdapter(bowlInfoTimeAdapter);

        timeRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;
        addItem("10분전");
        bowlInfoTimeAdapter.updateNotifications();
        bowlInfoTimeAdapter.notifyDataSetChanged();

        notificationService = RetrofitClient.getNotificationService();
        completedFeed = (Button) findViewById(R.id.btn_completed_feed);
        completedFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sendNotification(bowlId, FirebaseAuth.getInstance().getUid());
            }
        });
    }
    private void addItem(String ptext) {
        BowlInfoTimeItem timeItem = new BowlInfoTimeItem();
        timeItem.setTimeItem(ptext);

        System.out.println("Text = " + ptext);
        itemTimeList.add(timeItem);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        bowlService.getBowlLocation(2L).enqueue(new Callback<BowlLocation>() {

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
