package com.example.toshiba.origin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

import com.example.toshiba.retrofit.RetrofitOriginIS;


public class OriginAddressServiceStarter {

    private LocationResultReceiver locationResultReceiver = new LocationResultReceiver(new Handler());
    private Location currentLocation;
    private OriginInteractor originInteractor;

    public void startAddressService(Context myContext, Location location, OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
        currentLocation = location;
        Intent intent = new Intent(myContext, RetrofitOriginIS.class);
        intent.putExtra("location", currentLocation);
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
            originInteractor.originAddress(address, currentLocation);
        }
    }
}
