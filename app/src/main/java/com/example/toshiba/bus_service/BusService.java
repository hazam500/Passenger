package com.example.toshiba.bus_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.v4.content.LocalBroadcastManager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class BusService extends Service {

    private LocalBinder miBinder;
    private Handler miBusHandler;
    private Firebase viaje;
    private Firebase passengersAssigned;
    private Handler miLocationHandler;
    private String bus;

    public BusService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        miBinder = new LocalBinder();
        HandlerThread miHandlerThread = new HandlerThread("ServiceThread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        miHandlerThread.start();
        miBusHandler = new Handler(miHandlerThread.getLooper());
        miLocationHandler = new Handler(miHandlerThread.getLooper());
    }


    public class LocalBinder extends Binder {
        BusService getService() {
            return BusService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return miBinder;
    }

    public void initiateListener(final int originSector, final int destinationSector, final Firebase passengerFirebase, final String key) {

        viaje = passengerFirebase.child("Viajes").child(String.valueOf(originSector)).child(String.valueOf(destinationSector));
        passengersAssigned = passengerFirebase.child("PassengersAssigned").child(String.valueOf(originSector)).child(String.valueOf(destinationSector));

        miBusHandler.post(new Runnable() {

            @Override
            public void run() {
                //Escucha cuando se elimina el pasajero de la la lista de pasajeros no asignados
                Firebase destinationFirebase = passengerFirebase.child("PasajerosNoAsignados").child(String.valueOf(originSector)).child(String.valueOf(destinationSector)).child(key);
                destinationFirebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            getBusKey(key);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }

            private void getBusKey(String key) {
                passengersAssigned.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        bus = (String) ((HashMap) dataSnapshot.getValue()).get("Bus");
                        Intent intent = new Intent("BUS");
                        intent.putExtra("bus", bus);
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                        listenBusLocation();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
    }

    private void listenBusLocation() {
        miLocationHandler.post(new Runnable() {
            @Override
            public void run() {
                viaje.child(bus).child("Localización").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Double latitude = (Double)((HashMap) dataSnapshot.getValue()).get("latitude");
                        Double longitude = (Double)((HashMap) dataSnapshot.getValue()).get("longitude");
                        Intent intent = new Intent("LOCATION");
                        intent.putExtra("busLocation", new LatLng(latitude,longitude));
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }
        });
    }

    private Context getContext() {
        return (this);
    }
}
