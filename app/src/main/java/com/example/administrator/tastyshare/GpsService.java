package com.example.administrator.tastyshare;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pdg on 2017-06-27.
 */

public class GpsService {

    private Activity activity;

    private LatLng latLng;
    private Double latitude;
    private Double longitude;
    private LocationManager locationManager;
    private final int REQUEST_TURN_ON_GPS = 201;
    private MyLocationListener LocationListener;


    public GpsService(Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startLocationService() {

        LocationListener = new MyLocationListener();
        long minTime = 1;
        long minDistance = 1;
        try {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    LocationListener);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    LocationListener);

            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(lastLocation != null){
                latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            }

        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }


    public boolean isGpsCheck(){
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return false;

        return true;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }
        //사용 불가능 할시 메소드 호출
        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
