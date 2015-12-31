package com.example.toshiba.firebase;

import android.location.Location;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;


public class ServerLocationUpdater {

    public static void updateLocation(Firebase passengerFirebase, Location location, int originSector, int destinationSector, String key) {
        GeoFire geoFire = new GeoFire(passengerFirebase.child("PasajerosLocalizacion").child(String.valueOf(originSector)).child(String.valueOf(destinationSector)));
        geoFire.setLocation(key, new GeoLocation(location.getLatitude(), location.getLongitude()));
    }
}
