package com.example.toshiba.firebase;

import com.example.toshiba.origin.OriginInteractor;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

/**
 * Created by TOSHIBA on 02/01/2016.
 */
public class RecordChecker {

    private final OriginInteractor originInteractor;
    private boolean requested;

    public RecordChecker(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void checkIfRequestedService(final Firebase passengerFirebase, final String key) {

        Firebase firebase = passengerFirebase.child("PasajerosLocalizacion");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot hash : snapshot.getChildren()) {
                            HashMap hashmap = (HashMap) hash.getValue();
                            if (hashmap.containsKey(key)) {
                                requested = true;
                            }
                        }
                    }
                    if(requested){
                        originInteractor.showMessage("Ya se está procesando una solicitud previa");
                    }else {
                        //originInteractor.getDestinationLocation(address, myContext, key);
                    }
                } else {
                    //originInteractor.getDestinationLocation(address, myContext, key);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });

    }
}
