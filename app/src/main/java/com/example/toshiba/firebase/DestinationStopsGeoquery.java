package com.example.toshiba.firebase;

import com.example.toshiba.origin.OriginInteractor;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by TOSHIBA on 07/01/2016.
 */
public class DestinationStopsGeoquery {

    private final OriginInteractor originInteractor;
    private GeoQuery destinationGeoQuery;
    private GeoQueryEventListener destinationEventListener;

    public DestinationStopsGeoquery(OriginInteractor originInteractor) {
        this.originInteractor = originInteractor;
    }

    public void findDestinationStops(LatLng destinationLocation, GeoFire destinationGeoFire) {

        destinationGeoQuery = destinationGeoFire.queryAtLocation(new GeoLocation(destinationLocation.latitude, destinationLocation.longitude), 0.4);

        destinationEventListener = new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                originInteractor.addStopMarkers(key,location);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                destinationGeoQuery.removeGeoQueryEventListener(destinationEventListener);
            }

            @Override
            public void onGeoQueryError(FirebaseError error) {

            }
        };


        destinationGeoQuery.addGeoQueryEventListener(destinationEventListener);

    }
}
