package com.example.toshiba.origin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;

import com.example.toshiba.bus_service.BusServiceStarter;
import com.example.toshiba.firebase.PassengerRecordCreator;
import com.example.toshiba.firebase.SectorDataUpdater;
import com.example.toshiba.firebase.ServerLocationUpdater;
import com.example.toshiba.sector.Sector;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

public class OriginInteractor implements OriginInteractorInterface {

    private OriginPresenter originPresenter;

    public Location originLocation;
    ;
    private FirstLocation firstLocation = new FirstLocation(this);
    private Context myContext;
    private Destination destination = new Destination(this);
    private LatLng destinationLocation;
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


    @Override
    public void requestLocationUpdates(final Context myContext, final OriginPresenter originPresenter) {
        this.originPresenter = originPresenter;
        firstLocation.getFirstLocation(myContext);
        this.myContext = myContext;
    }

    @Override
    public void showOriginAddress(String address, Location currentLocation) {
        originAddress = address;
        originLocation = currentLocation;
        originPresenter.showLocationAddress(address, currentLocation);
        identifyOriginSector(currentLocation);
    }

    @Override
    public void identifyOriginSector(Location originLocation) {
        Sector sector = new Sector();
        originSector = sector.identifySector(new LatLng(originLocation.getLatitude(), originLocation.getLongitude()));
    }

    @Override
    public void searchDestination(String address, OriginPresenter originPresenter, Context myContext, String key) {
        passengerRecordCreator.checkIfRequestedService(originLocation, destinationAddress, originSector, destinationSector, passengerFirebase, key);
        this.originPresenter = originPresenter;
        this.key = key;
        destinationAddress = address;
        destination.getDestination(address, myContext);
    }

    @Override
    public void showDestinationLocation(LatLng destinationLocation) {
        this.destinationLocation = destinationLocation;
        originPresenter.showDestinationLocation(destinationLocation);
        identifyDestinationSector(destinationLocation);
    }

    @Override
    public void identifyDestinationSector(LatLng destinationLocation) {
        Sector sector = new Sector();
        destinationSector = sector.identifySector(destinationLocation);
        initFirebase();
    }

    @Override
    public void initFirebase() {
        passengerRecordCreator.createPassengerRecord(originLocation, destinationAddress, originSector, destinationSector, passengerFirebase, key);
        sectorDataUpdater.updateSectorData(originSector, destinationSector, key, originLocation, passengerFirebase);
        locationUpdater.getLocation(myContext);
    }

    @Override
    public void updateServerLocation(Location location) {
        ServerLocationUpdater.updateLocation(passengerFirebase, location, originSector, destinationSector, key);
    }

    @Override
    public void requestRide() {
        busServiceStarter.startService(myContext, passengerFirebase, originSector, destinationSector, key);
        LocalBroadcastManager.getInstance(myContext).registerReceiver(miBusMessageReceiver, new IntentFilter("BUS"));
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

