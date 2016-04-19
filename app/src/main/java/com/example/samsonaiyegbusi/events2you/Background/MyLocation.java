package com.example.samsonaiyegbusi.events2you.Background;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by samsonaiyegbusi on 18/03/16.
 */
public class MyLocation extends Service implements LocationListener {

    Context context;


    GoogleMap map;


TextView tv;
    public double latitude;
   public  double longitude;
    public double destinLat;
    public double destinLong;

    ProgressDialog progressDialog;

    public MyLocation(Context c )
    {

        this.map = map;
        this.context = c;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Finding Your Location");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();

                //ProgressDialog.show(this.context, "Finding Your Location", "Please Wait..." );


    }

    @Override
    public void onLocationChanged(Location location) {


        if (location != null)
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            if (latitude != 0 && longitude != 0)
            {

                String currentlat = Double.toString(latitude);
                String currentlong = Double.toString(longitude);

            }
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent showUserLocationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(showUserLocationSettings);

    }

    private void updateMap(String branch){

        LatLng destin_ADDRESS = new LatLng(destinLat, destinLong);
        LatLng current_ADDRESS = new LatLng(latitude, longitude);

        List<Marker> markers= new ArrayList();
        Marker destinMarker = map.addMarker(new MarkerOptions().position(destin_ADDRESS).title(branch));
        Marker currentMarker = map.addMarker(new MarkerOptions().position(current_ADDRESS).title("You"));

        markers.add(destinMarker);
        markers.add(currentMarker);

        destinMarker.showInfoWindow();
        currentMarker.showInfoWindow();

        PolylineOptions routeLine = new PolylineOptions().width(7).color(Color.RED);

        routeLine.add(destin_ADDRESS);
        routeLine.add(current_ADDRESS);
        map.addPolyline(routeLine);

        LatLngBounds.Builder mBuilder = new LatLngBounds.Builder();
        for (Marker m : markers) {
            mBuilder.include(m.getPosition());
        }
        LatLngBounds bounds = mBuilder.build();

        CameraUpdate updateMap = CameraUpdateFactory.newLatLngBounds(bounds, 400, 400, 5);
        map.animateCamera(updateMap);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
