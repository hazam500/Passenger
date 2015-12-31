package com.example.toshiba.origin;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by TOSHIBA on 22/12/2015.
 */
public interface OriginInteractorInterface {
    public void requestLocationUpdates(Context context, OriginPresenter originPresenter);

    void searchDestination(String address, OriginPresenter originPresenter, Context myContext, String key);

    void showOriginAddress(String address, Location currentLocation);

    void requestRide();

    void identifyOriginSector(Location originLocation);

    void showDestinationLocation(LatLng destinationLocation);

    void identifyDestinationSector(LatLng destinationLocation);

    void initFirebase();

    void updateServerLocation(Location location);

    void showMessage(String message);
}
