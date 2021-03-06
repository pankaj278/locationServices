package com.pankaj.getlocation.FusedLocationAPI;

/**
 * Created by APPZLOGIC on 9/8/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class FusedLocationServices implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private boolean oneTimeLocation;
    private Context context;
    private LocationResult locationResult;
    private GoogleApiClient googleApiClient;
    private static final int ON_CONNECTED = 1,ON_SUSPENDED = 2,ON_FAILED = 3;
    public static final int PERMISSION_REQUEST_CODE =101;
    private Activity activity;


    public FusedLocationServices(boolean oneTimeLocation, Context context, LocationResult locationResult) {
        this.oneTimeLocation = oneTimeLocation;
        this.context = context;
        this.locationResult = locationResult;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        if (checkLocationPermission())
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationResult.locationServicesConnectionResult(ON_CONNECTED);
        try{
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(0);
            mLocationRequest.setFastestInterval(0);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(0);
            LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, mLocationRequest,this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        locationResult.locationServicesConnectionResult(ON_SUSPENDED);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        locationResult.locationServicesConnectionResult(ON_FAILED);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (oneTimeLocation){
            locationResult.onLocationResult(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }else {
            locationResult.onLocationResult(location);
        }
    }

    private Boolean checkLocationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ((Activity)context).requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
                return false;
            }else
                return true;
        }else
            return true;
    }
}
