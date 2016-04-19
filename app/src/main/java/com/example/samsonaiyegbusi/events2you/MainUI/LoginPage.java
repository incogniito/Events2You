package com.example.samsonaiyegbusi.events2you.MainUI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Background.MyLocation;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetLoginStatus;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LoginPage extends AppCompatActivity implements Initialiser {

    // variables
    EditText username_et;
    EditText password_et;
    LocationManager locationManager;
    MyLocation myLocation;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    Location location;


    ImageButton login_ib;

    TextView signup_tv;
    Animation animateButton;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        getSupportActionBar().hide();
variableInitialiser();
        widgetInitialiser();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.signup_tv:
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
                break;
            case R.id.login_ib:
                v.startAnimation(animateButton);


                if (username_et.getText().length() == 0 || password_et.getText().length() == 0) {
                    Toast.makeText(LoginPage.this, "Please fill in Username/Password", Toast.LENGTH_SHORT).show();
                break;
                }
                loginButtonSetup();
                break;

        }
    }

    @Override
    public void variableInitialiser() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


    }

    @Override
    public void widgetInitialiser() {
        username_et = (EditText) findViewById(R.id.log_username_et);
        password_et = (EditText) findViewById(R.id.log_password_et);
        login_ib = (ImageButton) findViewById(R.id.login_ib);
        signup_tv = (TextView) findViewById(R.id.signup_tv);
        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter);

        signup_tv.setOnClickListener(this);
        login_ib.setOnClickListener(this);

    }

    private void loginButtonSetup() {

        myLocation = new MyLocation(this);

        String Username = username_et.getText().toString().trim();
        String Password = password_et.getText().toString().trim();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);

            return;
        } else {
            if (isNetworkEnabled) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }

            if (isGPSEnabled) {
                if (location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        }


        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String city = addresses.get(0).getLocality();

            GetLoginStatus login = new GetLoginStatus(this);
            login.execute(new String[]{Username, Password});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case 10:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isNetworkEnabled) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        location.getLongitude();
                        location.getLatitude();                    }
                    if(isGPSEnabled) {
                        if(location == null) {
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            location.getLongitude();
                            location.getLatitude();
                        }

                    }}
                break;

        }
    }

}
