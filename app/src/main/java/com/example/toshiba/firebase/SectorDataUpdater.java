package com.example.toshiba.firebase;

import android.location.Location;

import com.example.toshiba.origin.OriginInteractor;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.ServerValue;
import com.firebase.client.Transaction;
import com.google.android.gms.maps.model.LatLng;


public class SectorDataUpdater {
    private final OriginInteractor originInteractor;

    public SectorDataUpdater(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void updateSectorData(int originSector, int destinationSector, final String key, final Location originLocation, Firebase passengerFirebase) {

        Firebase firebase = passengerFirebase.child("SectoresDatos").child(String.valueOf(originSector)).child(String.valueOf(destinationSector));
        firebase.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.child("size").setValue(1);
                    mutableData.child("espera").setValue(ServerValue.TIMESTAMP);
                    mutableData.child("keyEspera").setValue(key);
                    mutableData.child("localizacionEspera").setValue(new LatLng(originLocation.getLatitude(),originLocation.getLongitude()));
                } else {
                    long size = (Long) mutableData.child("size").getValue() + 1;
                    mutableData.child("size").setValue(size);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                if (b) {

                }
            }
        });
    }
}
