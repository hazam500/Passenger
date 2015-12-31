package com.example.toshiba.retrofit;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

import com.example.toshiba.retrofit.Classes.AddressResults;
import com.example.toshiba.retrofit.Classes.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RetrofitOriginIS extends IntentService {

    private Map<String, String> queryMap = new HashMap<>();
    private Response<AddressResults> response;

    public RetrofitOriginIS() {
        super("RetrofitOriginIS");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ResultReceiver mReceiver = intent.getParcelableExtra("receiver");
            Location location = intent.getParcelableExtra("location");

            queryMap.put("latlng", location.getLatitude() + "," + location.getLongitude());
            queryMap.put("language", "es");
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
            String address = results[0].getFormattedAddress();

            Bundle bundle = new Bundle();
            bundle.putString("address", address);
            mReceiver.send(0, bundle);
        }
    }
}
