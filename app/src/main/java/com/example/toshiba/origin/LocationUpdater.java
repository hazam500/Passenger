package com.example.toshiba.origin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by TOSHIBA on 26/12/2015.
 */
public class LocationUpdater {
    private final OriginInteractor originInteractor;
    private LocationListener updatedLocationListener;

    public LocationUpdater(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    private LocationManager mLocationManager;

    public void getLocation(final Context myContext) {

        mLocationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);


        updatedLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                originInteractor.updateServerLocation(location);
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

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 40, updatedLocationListener);

    }
}
