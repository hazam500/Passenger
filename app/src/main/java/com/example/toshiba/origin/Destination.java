package com.example.toshiba.origin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

import com.example.toshiba.retrofit.RetrofitDestinationIS;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by TOSHIBA on 25/12/2015.
 */
public class Destination {

    private final OriginInteractor originInteractor;
    private DirectionResultReceiver directionResultReceiver = new DirectionResultReceiver(new Handler());
    private LatLng location;

    public Destination(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void getDestination(String address, Context myContext) {
        Intent intent = new Intent(myContext, RetrofitDestinationIS.class);
        intent.putExtra("receiver", directionResultReceiver);
        intent.putExtra("address", address);
        myContext.startService(intent);
    }

    @SuppressLint("ParcelCreator")
    class DirectionResultReceiver extends ResultReceiver {
        public DirectionResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            location = resultData.getParcelable("location");
            originInteractor.showDestinationLocation(location);
        }
    }

}
