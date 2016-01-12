package com.example.toshiba.origin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

import com.example.toshiba.retrofit.RetrofitOriginIS;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by TOSHIBA on 09/01/2016.
 */
public class DestinationAddressServiceStarter {

    private LocationResultReceiver locationResultReceiver = new LocationResultReceiver(new Handler());
    private OriginInteractor originInteractor;
    private Location location;

    public DestinationAddressServiceStarter(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void startAddressService(Context myContext, LatLng latLng) {
        location = new Location("test");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        Intent intent = new Intent(myContext, RetrofitOriginIS.class);
        intent.putExtra("location", location);
        intent.putExtra("receiver", locationResultReceiver);
        myContext.startService(intent);
    }

    @SuppressLint("ParcelCreator")
    public class LocationResultReceiver extends ResultReceiver {
        public LocationResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String address = resultData.getString("address");
            originInteractor.showUpdatedOriginAddress(address, location);
        }
    }
}
