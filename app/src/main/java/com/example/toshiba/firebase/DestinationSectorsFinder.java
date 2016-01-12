package com.example.toshiba.firebase;

import com.example.toshiba.origin.OriginInteractor;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

/**
 * Created by TOSHIBA on 07/01/2016.
 */
public class DestinationSectorsFinder {

    private final OriginInteractor originInteractor;

    public DestinationSectorsFinder(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void findDestinationSectors(Firebase passengerFirebase, String title) {

        Firebase firebase = passengerFirebase.child("DestinationStopsData").child(title);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                originInteractor.createSectorsList((String) ((HashMap) dataSnapshot.getValue()).get("Sector"));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
