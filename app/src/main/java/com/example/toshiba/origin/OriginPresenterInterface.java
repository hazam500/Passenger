package com.example.toshiba.origin;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by TOSHIBA on 22/12/2015.
 */
public interface OriginPresenterInterface {

    void requestLocationUpdates();


    void showLocationAddress(String passengerAddress, Location currentLocation);

    void showDestinationLocation(LatLng location);

    void searchDestination(String address, OriginActivity originActivity, String key);

    void requestRide();
}
