package com.example.toshiba.retrofit;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

import com.example.toshiba.retrofit.Classes.AddressResults;
import com.example.toshiba.retrofit.Classes.Geometry;
import com.example.toshiba.retrofit.Classes.Location;
import com.example.toshiba.retrofit.Classes.Result;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RetrofitDestinationIS extends IntentService {

    private Map<String, String> queryMap = new HashMap<>();
    private Response<AddressResults> response;

    public RetrofitDestinationIS() {
        super("DestinationIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            ResultReceiver mReceiver = intent.getParcelableExtra("receiver");
            String address = intent.getStringExtra("address");

            queryMap.put("address", address);
            queryMap.put("language", "es");
            queryMap.put("components", "country:VE");
            queryMap.put("key", "AIzaSyCLMiBZXLDO2WCQoyi66Q2uxgRKNrRalmg");

            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com").addConverterFactory(GsonConverterFactory.create()).build();

            RetrofitInterface service = retrofit.create(RetrofitInterface.class);
            Call<AddressResults> call = service.getData(queryMap);
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Result[] results = response.body().getResults();
            Geometry geometry = results[0].getGeometry();
            com.example.toshiba.retrofit.Classes.Location location = (Location)geometry.getLocation();

            Bundle bundle = new Bundle();
            bundle.putParcelable("location",new LatLng(location.getLat(),location.getLng()));
            mReceiver.send(0, bundle);
        }
    }
}
