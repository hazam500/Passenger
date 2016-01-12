package com.example.toshiba.origin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;

import com.example.toshiba.bus_service.BusServiceStarter;
import com.example.toshiba.firebase.CoreUpdater;
import com.example.toshiba.firebase.DestinationSectorsFinder;
import com.example.toshiba.firebase.DestinationStopsGeoquery;
import com.example.toshiba.firebase.OriginStopsGeoquery;
import com.example.toshiba.firebase.PassengerRecordCreator;
import com.example.toshiba.firebase.PassengerToStop;
import com.example.toshiba.firebase.RecordChecker;
import com.example.toshiba.firebase.SectorDataUpdater;
import com.example.toshiba.firebase.ServerLocationUpdater;
import com.example.toshiba.firebase.ValidStopsFinder;
import com.example.toshiba.myapplication.backend.myApi.model.MyResult;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OriginInteractor implements OriginInteractorInterface {

    private OriginPresenter originPresenter;

    public Location originLocation;
    ;
    private FirstLocation firstLocation = new FirstLocation(this);
    private Context myContext;
    private Destination destination = new Destination(this);
    private PassengerRecordCreator passengerRecordCreator = new PassengerRecordCreator(this);
    private Firebase passengerFirebase = new Firebase("https://passenger.firebaseio.com");
    private String originAddress;
    private int destinationSector;
    private int originSector;
    private String key;
    private SectorDataUpdater sectorDataUpdater = new SectorDataUpdater(this);
    private LocationUpdater locationUpdater = new LocationUpdater(this);
    private BusServiceStarter busServiceStarter = new BusServiceStarter(this);
    private String destinationAddress;
    private LatLng destinationLocation;
    private String pickUpAddress;
    private Location pickUpLocation;
    private GeoFire destinationGeoFire = new GeoFire(passengerFirebase.child("DestinationStopsLocation"));
    private GeoFire originGeoFire = new GeoFire(passengerFirebase.child("DepartureStopsLocation"));
    private ArrayList<String> validStops = new ArrayList<>();
    private Marker marker;
    private ArrayList<String> stopsTitles = new ArrayList();
    private ArrayList<String> sectorList = new ArrayList<>();
    private GeoQuery destinationGeoQuery;
    private GeoQuery originGeoQuery;
    private String selectedOriginStop;

    @Override
    public void requestLocationUpdates(final Context myContext, final OriginPresenter originPresenter) {
        this.originPresenter = originPresenter;
        final MyResult response = new MyResult();
        firstLocation.getFirstLocation(myContext);
        this.myContext = myContext;
    }

    @Override
    public void originAddress(String address, Location currentLocation) {
        originAddress = address;
        originLocation = currentLocation;
    }

    @Override
    public void getDestinationLocation(String address, Context myContext, String key) {
        this.destinationAddress = address;
        this.key = key;
        destination.getDestination(destinationAddress, this.myContext);
    }

    @Override
    public void showDestinationLocation(LatLng location) {
        destinationLocation = location;
        originPresenter.showDestinationLocation(destinationLocation);
    }

    //Called in case user decides to modify the proposed destination via UI
    @Override
    public void startDestinationAddressService(LatLng position) {
        DestinationAddressServiceStarter destinationAddressServiceStarter = new DestinationAddressServiceStarter(this);
        destinationAddressServiceStarter.startAddressService(myContext, position);
    }

    //Called in case user decides to modify the proposed destination via UI
    public void showUpdatedOriginAddress(String address, Location location) {
        originPresenter.showUpdatedOriginAddress(address, location);
        destinationLocation = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void findDestinationStops() {
        DestinationStopsGeoquery destinationStopsGeoquery = new DestinationStopsGeoquery(this);
        destinationStopsGeoquery.findDestinationStops(destinationLocation, destinationGeoFire);
    }

    public void addStopMarkers(String key, GeoLocation location) {
        originPresenter.addStopMarkers(key, location);
    }

    @Override
    public void findDestinationSectors(Marker marker) {
        DestinationSectorsFinder destinationSectorsFinder = new DestinationSectorsFinder(this);
        destinationSectorsFinder.findDestinationSectors(passengerFirebase, marker.getTitle());
    }

    public void createSectorsList(String sector) {
        if (!sectorList.contains(sector)) {
            sectorList.add(sector);
        }
    }

    @Override
    public void getOriginLocation() {
        originPresenter.showOriginLocation(originAddress, originLocation);
    }

    @Override
    public void startOriginAddressService(LatLng position) {
        Location location = new Location("Dummy");
        location.setLatitude(position.latitude);
        location.setLongitude(position.longitude);
        OriginAddressServiceStarter addressServiceStarter = new OriginAddressServiceStarter();
        addressServiceStarter.startAddressService(myContext, location, this);
    }

    @Override
    public void findOriginStops() {
        OriginStopsGeoquery originStopsGeoquery = new OriginStopsGeoquery(this);
        originStopsGeoquery.findOriginStops(originLocation, originGeoFire);
    }

    public void findValidStops(final String key, GeoLocation location) {
        ValidStopsFinder validStopsFinder = new ValidStopsFinder(this);
        validStopsFinder.findValidStops(passengerFirebase, key, sectorList, location);
    }

    final Map<String, String> mapStopSector = new HashMap<>();
    public void createValidStopsList(String key, GeoLocation location, String sector) {
        mapStopSector.put(key, sector);
        originPresenter.addOriginStopMarker(location, key);
    }

    @Override
    public void selectedOriginStop(String title) {
        this.selectedOriginStop = title;
    }

    @Override
    public void requestRide() {
        PassengerToStop.assignPassengertoStop(selectedOriginStop,key,passengerFirebase, mapStopSector);
        CoreUpdater coreUpdater = new CoreUpdater();
        coreUpdater.updateCore(passengerFirebase,selectedOriginStop,mapStopSector);
    }


    /*-----------------------------------------------------------------------*/

    @Override
    public void checkIfRequestedService(String destinationAddress, OriginPresenter originPresenter, Context myContext, String key) {
        RecordChecker recordChecker = new RecordChecker(this);
        recordChecker.checkIfRequestedService(passengerFirebase, key);
        this.originPresenter = originPresenter;
        this.key = key;
        this.destinationAddress = destinationAddress;
    }


    @Override
    public void identifyDestinationSector(LatLng location) {

    }

    public void requestRide2() {
        //createPassengerRecord();
        busServiceStarter.startService(myContext, passengerFirebase, originSector, destinationSector, key);
        LocalBroadcastManager.getInstance(myContext).registerReceiver(miBusMessageReceiver, new IntentFilter("BUS"));
    }


    public void createPassengerRecord2() {
        passengerRecordCreator.createPassengerRecord(originLocation, destinationAddress, originSector, destinationSector, passengerFirebase, key);
        locationUpdater.getLocation(myContext);
        sectorDataUpdater.updateSectorData(originSector, destinationSector, key, originLocation, passengerFirebase);
    }

    @Override
    public void updateServerLocation(Location location) {
        ServerLocationUpdater.updateLocation(passengerFirebase, location, originSector, destinationSector, key);
    }

    private Notificator notificator = new Notificator();
    private BroadcastReceiver miBusMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String bus = intent.getStringExtra("bus");
            notificator.createBusNotification(bus, myContext);
        }
    };

    @Override
    public void showMessage(String message) {
        originPresenter.showMessage(message);
    }


}

