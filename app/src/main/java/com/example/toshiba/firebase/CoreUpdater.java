package com.example.toshiba.firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class CoreUpdater {

    public void updateCore(final Firebase passengerFirebase, String selectedOriginStop, Map<String, String> mapStopSector) {

        final String sector = mapStopSector.get(selectedOriginStop);

        Firebase firebaseGetCore = passengerFirebase.child("OriginStopsData").child(selectedOriginStop).child("Core");
        firebaseGetCore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String core = (String) dataSnapshot.getValue();
                update(core,passengerFirebase,sector);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void update(String core, Firebase passengerFirebase, String sector) {
        Firebase firebaseCore = passengerFirebase.child("Cores").child(core).child(sector);
        firebaseCore.child("Quantity").runTransaction(new Transaction.Handler() {
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
    }
}
