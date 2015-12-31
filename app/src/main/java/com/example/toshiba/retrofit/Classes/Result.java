package com.example.toshiba.retrofit.Classes;

/**
 * Created by TOSHIBA on 22/12/2015.
 */
public class Result {

    private AddressComponent[] address_components = new AddressComponent[100];
    private String formatted_address = "";
    private String place_id = "";
    private String[] types = new String[100];
    private Boolean partialMatch = false;
    private Geometry geometry = new Geometry();

    public AddressComponent[] getAddressComponents() {
        return address_components;
    }

    public String getFormattedAddress() {
        return formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getPlaceId() {
        return place_id;
    }

    public String[] getTypes() {
        return types;
    }

    public Boolean getPartialMatch() {
        return partialMatch;
    }
}
