package com.pankaj.getlocation.FusedLocationAPI;

import android.location.Location;

/**
 * Created by APPZLOGIC on 9/8/2017.
 */

public interface LocationResult {
    void onLocationResult(Location location);
    void locationServicesConnectionResult(int result);
}
