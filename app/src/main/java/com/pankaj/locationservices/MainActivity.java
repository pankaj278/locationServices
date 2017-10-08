package com.pankaj.locationservices;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pankaj.getlocation.FusedLocationAPI.FusedLocationServices;
import com.pankaj.getlocation.FusedLocationAPI.LocationResult;

public class MainActivity extends AppCompatActivity implements LocationResult {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FusedLocationServices(true,this,this);
    }

    @Override
    public void onLocationResult(Location location) {
        Log.d("Location",location.getLatitude()+" ,"+location.getLongitude());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void locationServicesConnectionResult(int result) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (FusedLocationServices.PERMISSION_REQUEST_CODE == requestCode &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED){
            new FusedLocationServices(true,this,this);
        }else {
            Log.d("Permission","Denied");
        }
    }
}
