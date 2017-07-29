package com.example.administrator.tastyshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap tastyMap;
    private MarkerOptions markerOptions;
    private GpsService gpsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        gpsService = new GpsService(this);

            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    tastyMap = googleMap;

                    try {
                        gpsService.startLocationService();
                        showCurrentLocation(gpsService.getLatLng());
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
                MapsInitializer.initialize(this);
            } catch (Exception e) {
                e.printStackTrace();
            }




    }//end onCreate

    private void showCurrentLocation(LatLng currentLatLng) {
        if(currentLatLng != null) {
            zoomMap(currentLatLng);
            markerOptions = new MarkerOptions();
            markerOptions.position(currentLatLng);
            markerOptions.title("현재위치");
            tastyMap.clear();
            tastyMap.addMarker(markerOptions);
        }
        else{

        }
    }

    private void zoomMap(LatLng latLng) {
        tastyMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.get_location_btn){
            showCurrentLocation(gpsService.getLatLng());
            Toast.makeText(getApplicationContext(),"현 위치 찾기 버튼 클릭",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
