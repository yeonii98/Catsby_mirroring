package org.techtown.catsby.home;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.techtown.catsby.R;
import org.techtown.catsby.retrofit.RetrofitClient;
import org.techtown.catsby.retrofit.dto.BowlLocation;
import org.techtown.catsby.retrofit.service.BowlService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBowlMap extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap mgoogleMap;
    private MapView mapView;
    private Context context;
    private BowlService bowlService;

    Long id;
    String name;

    public static FragmentBowlMap newInstance(){
        FragmentBowlMap fragGoogleMap = new FragmentBowlMap();
        return fragGoogleMap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bowlmap,container,false);
        mapView=(MapView)view.findViewById(R.id.googleMap);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

//        Bundle bundle = getArguments();
//        String name = bundle.getString("name");
//        Log.e("FragmentBowlMap", "name : " + name);
        id = 2L;  //////
//        name = "sss";
        bowlService = RetrofitClient.getBowlService();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        bowlService.getBowlLocation(id).enqueue(new Callback<BowlLocation>() {

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
