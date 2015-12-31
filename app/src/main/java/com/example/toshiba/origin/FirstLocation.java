package com.example.toshiba.origin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.ResultReceiver;

import com.example.toshiba.retrofit.RetrofitOriginIS;

/**
 * Created by TOSHIBA on 25/12/2015.
 */
public class FirstLocation {
    private final OriginInteractor originInteractor;
    private LocationListener firstLocationListener;
    private LocationResultReceiver locationResultReceiver = new LocationResultReceiver(new Handler());
    private Location currentLocation;


    public FirstLocation(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    private LocationManager mLocationManager;


    public void getFirstLocation(final Context myContext) {
        mLocationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);

        firstLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mLocationManager.removeUpdates(firstLocationListener);
                Intent intent = new Intent(myContext, RetrofitOriginIS.class);
                intent.putExtra("location", location);
                intent.putExtra("receiver", locationResultReceiver);
                myContext.startService(intent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, firstLocationListener);
    }

    @SuppressLint("ParcelCreator")
    public class LocationResultReceiver extends ResultReceiver {
        public LocationResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String address = resultData.getString("address");
            originInteractor.showOriginAddress(address, currentLocation);
        }
    }
}
