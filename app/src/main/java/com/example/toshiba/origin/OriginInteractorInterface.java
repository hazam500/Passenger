package com.example.toshiba.origin;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


/**
 * Created by TOSHIBA on 22/12/2015.
 */
public interface OriginInteractorInterface {
    public void requestLocationUpdates(Context context, OriginPresenter originPresenter);

    void checkIfRequestedService(String address, OriginPresenter originPresenter, Context myContext, String key);

    void originAddress(String address, Location currentLocation);

    void requestRide();

    void showDestinationLocation(LatLng location);

    void identifyDestinationSector(LatLng location);

    void updateServerLocation(Location location);

    void showMessage(String message);

    void getDestinationLocation(String address, Context myContext, String key);

    void findDestinationStops();

    void findDestinationSectors(Marker stopList);

    void findOriginStops();

    void getOriginLocation();

    void startDestinationAddressService(LatLng position);

    void startOriginAddressService(LatLng position);

    void selectedOriginStop(String title);
}
