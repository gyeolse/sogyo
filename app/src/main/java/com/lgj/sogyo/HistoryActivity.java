package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HistoryActivity extends AppCompatActivity implements OnMapReadyCallback {

    //1
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //2
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.fragment_main_mv);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //3
        LatLng location = new LatLng(37.451376,126.655875);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("인하대상권")
                .snippet("상세정보")
                .position(location);
        googleMap.addMarker(markerOptions);

        View infoWindow = getLayoutInflater().inflate(R.layout.hitsoryinfo,null);
        DriverInfoAdapter driverInfoAdapter = new DriverInfoAdapter(infoWindow);
        googleMap.setInfoWindowAdapter(driverInfoAdapter);

        //마커를 비추고 있는 화면을 비추는 카메라를 띄워준다고 생각하자.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));

    }
}
