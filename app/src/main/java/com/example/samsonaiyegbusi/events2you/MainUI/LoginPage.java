package com.example.samsonaiyegbusi.events2you.MainUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetLoginStatus;

public class LoginPage extends AppCompatActivity implements Initialiser {

    // variables
    EditText username_et;
    EditText password_et;

    ImageButton login_ib;

    TextView signup_tv;
    Animation animateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                loginButtonSetup();
                break;

        }
    }

    @Override
    public void variableInitialiser() {

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

    private void loginButtonSetup(){

        String Username = username_et.getText().toString().trim();
        String Password = password_et.getText().toString().trim();

        if (username_et.getText().length() == 0 || password_et.getText().length() == 0) {
            Toast.makeText(LoginPage.this, "Please fill in Username/Password", Toast.LENGTH_SHORT).show();
        }

        GetLoginStatus login = new GetLoginStatus(this);
        login.execute(new String[]{Username, Password});
    }

}
