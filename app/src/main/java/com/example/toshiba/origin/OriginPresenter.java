package com.example.toshiba.origin;

import android.content.Context;
import android.location.Location;
import android.support.design.widget.Snackbar;

import com.google.android.gms.maps.model.LatLng;

public class OriginPresenter implements OriginPresenterInterface {


    private final OriginInteractorInterface originInteractor;
    private final OriginActivity originActivity;
    private final Context myContext;

    public OriginPresenter(OriginActivity originActivity) {
        myContext = originActivity.getBaseContext();
        this.originActivity = originActivity;
        this.originInteractor = new OriginInteractor();
    }

    @Override
    public void requestLocationUpdates() {
        originInteractor.requestLocationUpdates(myContext, this);
    }

    @Override
    public void showLocationAddress(String passengerAddress, Location currentLocation) {
        originActivity.addMarker(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        originActivity.showAddress(passengerAddress);
        originActivity.showSearchDestination();
    }


    public void searchDestination(String address, OriginActivity originActivity, String key) {
        this.originActivity.showLoading();
        originInteractor.searchDestination(address, this, myContext, key);
    }

    @Override
    public void showDestinationLocation(LatLng location) {
        originActivity.hideLoading();
        originActivity.hideSearchDestination();
        originActivity.addMarker(location);
        originActivity.showRequestRide();
    }

    @Override
    public void requestRide() {
        originInteractor.requestRide();
    }

    public void showMessage(String message) {
        originActivity.showMessage(message);
    }
}
