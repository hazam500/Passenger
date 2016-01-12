package com.example.toshiba.origin;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by TOSHIBA on 22/12/2015.
 */
public interface OriginPresenterInterface {

    void requestLocationUpdates();


    void showOriginLocation(String passengerAddress, Location currentLocation);

    void showDestinationLocation(LatLng location);

    void searchDestination(String address, String key);

    void requestRide();


    void deliverMarquer(Marker marker);

    void searchForDestinationStops();

    void findDestinationSectors(Marker stopList);

    void getOriginLocation();

    void findOriginStops();

    void startDestinationAddressService(LatLng position);

    void startOriginAddressService(LatLng position);

    void selectedOriginStop(String title);
}
