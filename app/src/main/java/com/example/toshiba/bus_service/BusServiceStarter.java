package com.example.toshiba.bus_service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.toshiba.origin.OriginInteractor;
import com.firebase.client.Firebase;

/**
 * Created by TOSHIBA on 26/12/2015.
 */
public class BusServiceStarter {

    private final OriginInteractor originInteractor;
    private Firebase passengerFirebase;
    private int originSector;
    private int destinationSector;
    private String key;

    public BusServiceStarter(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void startService(Context myContext, Firebase passengerFirebase, int originSector, int destinationSector, String key) {
        this.passengerFirebase = passengerFirebase;
        this.originSector = originSector;
        this.destinationSector = destinationSector;
        this.key = key;

        Intent intent = new Intent(myContext, BusService.class);
        myContext.bindService(intent, miServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private boolean bound = false;
    private BusService miService;
    private ServiceConnection miServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bound = true;
            BusService.LocalBinder binder = (BusService.LocalBinder) service;
            miService = binder.getService();
            miService.initiateListener(originSector, destinationSector, passengerFirebase, key);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };
}
