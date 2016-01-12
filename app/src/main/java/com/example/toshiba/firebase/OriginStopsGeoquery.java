package com.example.toshiba.firebase;

import android.location.Location;

import com.example.toshiba.origin.OriginInteractor;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;

/**
 * Created by TOSHIBA on 07/01/2016.
 */
public class OriginStopsGeoquery {

    private final OriginInteractor originInteractor;
    private GeoQuery originGeoQuery;
    private GeoQueryEventListener originEventListener;

    public OriginStopsGeoquery(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void findOriginStops(Location originLocation, GeoFire originGeoFire) {

        originGeoQuery = originGeoFire.queryAtLocation(new GeoLocation(originLocation.getLatitude(), originLocation.getLongitude()), 0.4);

        originEventListener = new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                originInteractor.findValidStops(key,location);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                originGeoQuery.removeGeoQueryEventListener(originEventListener);
            }

            @Override
            public void onGeoQueryError(FirebaseError error) {

            }
        };

        originGeoQuery.addGeoQueryEventListener(originEventListener);
    }
}
