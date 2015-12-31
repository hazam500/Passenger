package com.example.toshiba.sector;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 25/12/2015.
 */
public class Sector {

    private List<List<LatLng>> polyList = new ArrayList<>();

    private List<LatLng> Sector1 = new ArrayList<>();
    private List<LatLng> Sector2 = new ArrayList<>();
    private int sector;

    public int identifySector(LatLng latLng) {

        Sector1.add(new LatLng(10.459278, -66.581092));
        Sector1.add(new LatLng(10.459257, -66.570105));
        Sector1.add(new LatLng(10.468774, -66.569805));
        Sector1.add(new LatLng(10.470145, -66.578924));

        Sector2.add(new LatLng(10.499610, -66.853555));
        Sector2.add(new LatLng(10.499715, -66.850047));
        Sector2.add(new LatLng(10.496297, -66.849961));
        Sector2.add(new LatLng(10.496498, -66.853035));

        polyList.add(Sector1);
        polyList.add(Sector2);

        for (int i = 0; i < polyList.size(); i++) {

            Boolean result = PolyUtil.containsLocation(latLng, polyList.get(i), true);

            if (result.equals(true)) {
                sector = i + 1;
                i = polyList.size();
            }
        }

        return sector;
    }
}
