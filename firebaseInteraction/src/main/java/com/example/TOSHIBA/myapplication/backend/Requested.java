package com.example.TOSHIBA.myapplication.backend;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;

import java.util.HashMap;

/**
 * Created by TOSHIBA on 12/01/2016.
 */
public class Requested {
    private final MyEndpoint myEndpoint;
    Firebase firebase = new Firebase("https://passenger.firebaseio.com");

    public Requested(MyEndpoint myEndpoint) {
        this.myEndpoint = myEndpoint;
    }

    public void checkIfRequested(String core, String sector) {

        final Firebase firebaseCore = firebase.child("Cores").child(core).child(sector);
        firebaseCore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean requested = (boolean) ((HashMap) dataSnapshot.getValue()).get("Requested");
                if (!requested) {
                    firebaseCore.child("Requested").setValue(true);
                    findAvailableShuttle();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void findAvailableShuttle() {
        GeoFire geoFireShuttle = new GeoFire(firebase.child("BusDisponibleLocalización"));
        //geoFireShuttle.queryAtLocation();
    }
}
