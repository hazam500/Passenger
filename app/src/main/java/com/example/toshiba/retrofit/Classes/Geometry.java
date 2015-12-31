package com.example.toshiba.retrofit.Classes;

/**
 * Created by TOSHIBA on 22/12/2015.
 */
public class Geometry {

    private com.example.toshiba.retrofit.Classes.Location location = new com.example.toshiba.retrofit.Classes.Location();
    private String location_type = "";

    public Geometry() {
    }

    public com.example.toshiba.retrofit.Classes.Location getLocation() {
        return location;
    }

    public String getLocationType() {
        return location_type;
    }
}
