package com.example.toshiba.retrofit.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 22/12/2015.
 */
public class AddressComponent {

    private String longName = "";
    private String shortName = "";
    private List<String> types = new ArrayList<>();

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public List<String> getTypes() {
        return types;
    }
}
