package org.techtown.catsby.home;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.List;

public class FragmentBowlMap extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap mgoogleMap;
    private MapView mapView;
    private Context context;

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

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap = googleMap;
        LatLng namsan = new LatLng(37.551968, 126.988500);
        MarkerOptions marker = new MarkerOptions();
        marker.position(namsan); //좌표
        marker.title("남산타워");

        mgoogleMap.addMarker(marker);

        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(namsan,16));
    }


}
