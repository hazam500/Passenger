package com.example.toshiba.origin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class FirstLocation {
    private final OriginInteractor originInteractor;
    private LocationListener firstLocationListener;

    private LocationManager mLocationManager;

    public FirstLocation(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void getFirstLocation(final Context myContext) {
        mLocationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);

        firstLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mLocationManager.removeUpdates(firstLocationListener);
                OriginAddressServiceStarter addressServiceStarter = new OriginAddressServiceStarter();
                addressServiceStarter.startAddressService(myContext, location, originInteractor);
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
}
