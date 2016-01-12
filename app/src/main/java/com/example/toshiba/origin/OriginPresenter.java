package com.example.toshiba.origin;

import android.content.Context;
import android.location.Location;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

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

    public void searchDestination(String address, String key) {
        originInteractor.getDestinationLocation(address, myContext, key);
    }

    @Override
    public void showDestinationLocation(LatLng location) {
        originActivity.hideSearchDestination();
        Location location1 = new Location("test");
        location1.setLatitude(location.latitude);
        location1.setLongitude(location.longitude);
        originActivity.addPassengerMarker(location1);
        originActivity.moveCamera(location);
        originActivity.showConfirmDestination();
        originActivity.setOnDestinationMarkerDragListener();
    }

    @Override
    public void startDestinationAddressService(LatLng position) {
        originInteractor.startDestinationAddressService(position);
    }


    public void showUpdatedOriginAddress(String address, Location location) {
        originActivity.addPassengerMarker(location);
        originActivity.showAddress(address);
    }

    @Override
    public void searchForDestinationStops() {
        originInteractor.findDestinationStops();
        originActivity.hideConfirmDestination();
        originActivity.showSearchOriginStops();
    }

    public void addStopMarkers(String key, GeoLocation location) {
        originActivity.addStopMarker(key, location);
        originActivity.setOnClickListener();
    }

    @Override
    public void findDestinationSectors(Marker marker) {
        originInteractor.findDestinationSectors(marker);
    }

    @Override
    public void startOriginAddressService(LatLng position) {
        originInteractor.startOriginAddressService(position);
    }


    @Override
    public void getOriginLocation() {
        originInteractor.getOriginLocation();
    }

    @Override
    public void showOriginLocation(String passengerAddress, Location currentLocation) {
        originActivity.addPassengerMarker(currentLocation);
        originActivity.moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        originActivity.showAddress(passengerAddress);
        originActivity.hideSearchOriginStops();
        originActivity.showConfirmOrigin();
        originActivity.setOnOriginMarkerDragListener();
    }

    @Override
    public void findOriginStops() {
        originInteractor.findOriginStops();
    }

    public void addOriginStopMarker(GeoLocation location, String key) {
        originActivity.addOriginStopMarker(location, key);
        originActivity.setOriginOnClickListener();
    }

    @Override
    public void selectedOriginStop(String title) {
        originInteractor.selectedOriginStop(title);
    }

    @Override
    public void requestRide() {
        originInteractor.requestRide();
    }

    @Override
    public void deliverMarquer(Marker marker) {

    }


    public void showMessage(String message) {
        originActivity.showMessage(message);
    }


}
