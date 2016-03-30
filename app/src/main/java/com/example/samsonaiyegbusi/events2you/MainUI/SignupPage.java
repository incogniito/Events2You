package com.example.samsonaiyegbusi.events2you.MainUI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.PostUser;

import java.util.Calendar;

public class SignupPage extends AppCompatActivity implements Initialiser {

    EditText username_et;
    EditText password_et;
    EditText dob_et;
    EditText firstName_et;
    EditText lastName_et;


    ImageButton signup_ib;



    Animation animateButton;
    Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
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
        v.startAnimation(animateButton);

        switch (v.getId()) {
            case R.id.signup_ib:
                //check every field as an entry
                if (firstName_et.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SignupPage.this, "Please fill in First name", Toast.LENGTH_SHORT).show();
                    break;
                } else if (lastName_et.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SignupPage.this, "Please fill in Surname", Toast.LENGTH_SHORT).show();
                    break;
                } else if (username_et.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SignupPage.this, "Please fill in Username", Toast.LENGTH_SHORT).show();
                    break;
                } else if (dob_et.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SignupPage.this, "Please fill in Email", Toast.LENGTH_SHORT).show();
                    break;
                } else if (password_et.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SignupPage.this, "Please fill in Password", Toast.LENGTH_SHORT).show();
                    break;
                }
                signupUser();
                break;

            case R.id.dob_et:
                DatePickerDialog returnDatePicker;
                returnDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        dob_et.setText(selectedday + "-" + (selectedmonth + 1) + "-" + selectedyear);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                returnDatePicker.setTitle("Select Date");
                returnDatePicker.show();

                break;
        }
    }

    @Override
    public void variableInitialiser() {
        cal = cal.getInstance();
        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter);

    }

    @Override
    public void widgetInitialiser() {
        //initialise
        username_et = (EditText) findViewById(R.id.username_et);
        password_et = (EditText) findViewById(R.id.password_et);
        dob_et = (EditText) findViewById(R.id.dob_et);
        dob_et.setFocusable(false);
        dob_et.setClickable(true);
        dob_et.setOnClickListener(this);

        firstName_et = (EditText) findViewById(R.id.firstname_et);
        lastName_et = (EditText) findViewById(R.id.surname_et);
        signup_ib = (ImageButton) findViewById(R.id.signup_ib);
        signup_ib.setOnClickListener(this);

    }

    private void signupUser(){
        String username = username_et.getText().toString();
        String password = password_et.getText().toString();
        String dob = dob_et.getText().toString();
        String firstName = firstName_et.getText().toString();
        String surname = lastName_et.getText().toString();

        PostUser newUser = new PostUser(this);
        newUser.execute(new String[]{username, password, firstName, surname, dob });

    }

}
