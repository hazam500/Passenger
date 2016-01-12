package com.example.toshiba.firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

import java.util.Map;

public class PassengerToStop {
    public static void assignPassengertoStop(String selectedOriginStop, String key, Firebase passengerFirebase, Map<String, String> sectorStop) {

        String sector = sectorStop.get(selectedOriginStop);

        Firebase firebase = passengerFirebase.child("OriginStopsData").child(selectedOriginStop).child("Sectors").child(sector);

        firebase.child("Quantity").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    long size = (Long) mutableData.getValue() + 1;
                    mutableData.setValue(size);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                if (b) {

                }
            }
        });

        firebase.child("Passengers").push().setValue(key);
    }
}
