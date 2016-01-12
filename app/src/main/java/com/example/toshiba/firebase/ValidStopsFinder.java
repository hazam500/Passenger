package com.example.toshiba.firebase;

import com.example.toshiba.origin.OriginInteractor;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoLocation;

import java.util.ArrayList;
import java.util.HashMap;

public class ValidStopsFinder {
    private final OriginInteractor originInteractor;

    public ValidStopsFinder(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void findValidStops(Firebase passengerFirebase, final String key, final ArrayList<String> sectorList, final GeoLocation location) {

        final Firebase firebase = passengerFirebase.child("OriginStopsData").child(key).child("Sectors");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String sector : sectorList) {
                    if (((HashMap) dataSnapshot.getValue()).containsKey(sector)) {
                        originInteractor.createValidStopsList(key, location,sector);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
