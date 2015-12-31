package com.example.toshiba.retrofit;

import com.example.toshiba.retrofit.Classes.AddressResults;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by TOSHIBA on 23/12/2015.
 */
public interface RetrofitInterface {

    @GET("/maps/api/geocode/json")
    Call<AddressResults> getData(@QueryMap Map<String, String> queryMap);
}
