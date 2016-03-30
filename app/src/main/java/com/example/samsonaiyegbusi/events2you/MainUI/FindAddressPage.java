package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FindAddressPage extends AppCompatActivity implements Initialiser, OnMapReadyCallback {

    EditText POSTCODE_et;
    ImageButton NEXT_ib;
    ImageButton FIND_ib;
    Bundle bundle;
    Animation animateButton;
    MapFragment mapFragment;
    Marker addressMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_address_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_ib_:

                v.startAnimation(animateButton);

                if (POSTCODE_et.length() == 0) {
                    Toast.makeText(FindAddressPage.this, "Please fill in the Event location", Toast.LENGTH_SHORT).show();
                    break;
                }


                mapFragment.getMapAsync(this);
                break;

            case R.id.next_ib_:

                v.startAnimation(animateButton);

                if (POSTCODE_et.length() == 0) {
                    Toast.makeText(FindAddressPage.this, "Please fill in the Event location", Toast.LENGTH_SHORT).show();
                    break;
                }

                saveAddress();
                Intent takeUserHome = new Intent(FindAddressPage.this, EventDescriptionPage.class);
                takeUserHome.putExtras(bundle);
                startActivity(takeUserHome);

        }
    }

    @Override
    public void variableInitialiser() {
        Intent intent = getIntent();
        bundle = intent.getExtras();
         mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_gm));

    }

    @Override
    public void widgetInitialiser() {
        POSTCODE_et = (EditText) findViewById(R.id.postcode_et);
        NEXT_ib = (ImageButton) findViewById(R.id.next_ib_);
        FIND_ib = (ImageButton) findViewById(R.id.find_ib_);
        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter);


        FIND_ib.setOnClickListener(this);
        NEXT_ib.setOnClickListener(this);

        String preSetText = bundle.getString("Address");

        if (preSetText != null)
        {
            POSTCODE_et.setText(bundle.getString("Address"));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        findAddress(googleMap);
    }

    public void saveAddress()
    {
        String address = addressMarker.getTitle().toString();

        bundle.putString("Address", address);
    }

    public void findAddress(GoogleMap MAP_GM){

        String Postcode_getter = POSTCODE_et.getText().toString();
         addressMarker = null;


        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(
                    Postcode_getter, 1);
            Address add = addresses.get(0);
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            double latitude = add.getLatitude();
            double longitude = add.getLongitude();


                LatLng ADDRESS = new LatLng(latitude, longitude);
                CameraUpdate updateMap = CameraUpdateFactory.newLatLngZoom(ADDRESS, 16);
                MAP_GM.animateCamera(updateMap);

                if (address != null) {
                    if (city != null) {
                        if (country != null) {
                            if (state != null) {
                                if (postalCode != null) {
                                        addressMarker = MAP_GM.addMarker(new MarkerOptions().position(ADDRESS).title(address + ", " + city + ", " + state + ", " + country + ", " + postalCode));
                                        addressMarker.showInfoWindow();

                                } else {
                                        addressMarker=  MAP_GM.addMarker(new MarkerOptions().position(ADDRESS).title(address + ", " + country));
                                        addressMarker.showInfoWindow();

                                }
                            } else {
                                if (postalCode != null) {
                                        addressMarker=   MAP_GM.addMarker(new MarkerOptions().position(ADDRESS).title(address + ", " + city + ", " + "UK" + ", " + postalCode));
                                        addressMarker.showInfoWindow();

                                }
                            }
                        }
                    } else {
                        if (country != null) {
                            if (state != null) {
                                if (postalCode != null) {
                                        addressMarker=   MAP_GM.addMarker(new MarkerOptions().position(ADDRESS).title(address + ", " + state + ", " + country + ", " + postalCode));
                                        addressMarker.showInfoWindow();

                                } else {

                                }
                            } else {
                                if (postalCode != null) {
                                        addressMarker=  MAP_GM.addMarker(new MarkerOptions().position(ADDRESS).title(address + ", " + country + ", " + postalCode));
                                        addressMarker.showInfoWindow();

                                } else {

                                }
                            }
                        }
                    }
                }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
