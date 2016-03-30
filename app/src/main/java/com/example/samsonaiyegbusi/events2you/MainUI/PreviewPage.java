package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.PostEvent;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class PreviewPage extends AppCompatActivity implements Initialiser{

    ImageView preview_image;
    ImageButton preview_save;

    //TextView description;
    EditText preview_date;
    EditText preview_name;
    EditText preview_address;
    EditText preview_description;
    EditText preview_time;
    EditText preview_finish_time;
    Bundle bundle;

    SessionManager session;


    Animation animateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        widgetInitialiser();
        variableInitialiser();

    }

    @Override
    public void onClick(View v) {
        v.startAnimation(animateButton);

        switch (v.getId()) {
            case R.id.preview_save_ib:


                saveDataSetup();

                break;
        }
    }

    @Override
    public void variableInitialiser() {
        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter);
        Intent intent = getIntent();
        bundle = intent.getExtras();



        retrieveImage();
        retrieveDetails();
        retrieveLocation();
        retrieveDescription();

    }

    @Override
    public void widgetInitialiser() {
        preview_image = (ImageView) findViewById(R.id.preview_iv);
        preview_save = (ImageButton) findViewById(R.id.preview_save_ib);

        //TextView description;
        preview_date = (EditText) findViewById(R.id.preview_date_et);
        preview_name = (EditText) findViewById(R.id.preview_name_et);
        preview_address = (EditText) findViewById(R.id.preview_address_et);
        preview_description = (EditText) findViewById(R.id.preview_description_et);
        preview_time = (EditText) findViewById(R.id.preview_time_et);
        preview_finish_time = (EditText) findViewById(R.id.preview_finish_time_et);


        preview_save.setOnClickListener(this);
    }

    private void retrieveLocation(){
        String _address = bundle.getString("Address").toString();
        preview_address.setText(_address);
    }

    private void retrieveImage(){


        byte[] bytes = bundle.getByteArray("fileName");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        preview_image.setImageBitmap(bmp);

    }

    private void retrieveDetails(){

        String _name = bundle.getString("Event_name");
        String _date_ = bundle.getString("Event_Date");
        String _time = bundle.getString("Event_time");
        String _finishTime = bundle.getString("Event_finish_time");

        preview_date.setText(_date_);
        preview_name.setText(_name);
        preview_time.setText(_time);
        preview_finish_time.setText(_finishTime);

    }

    private void retrieveDescription(){

        String _description = bundle.getString("Description");
        preview_description.setText(_description);
    }

    private void saveDataSetup(){

        byte[] _image =  bundle.getByteArray("fileName");

        String eventImage = Base64.encodeToString(_image, Base64.NO_WRAP + Base64.URL_SAFE);
        String _name = preview_name.getText().toString();
        String _date = preview_date.getText().toString();
        String _starttime = preview_time.getText().toString();
        String _address = preview_address.getText().toString();
        String _description = preview_description.getText().toString();
        String _finishTime = preview_finish_time.getText().toString();
        HashMap<String, String> user = session.getUserDetails();

        String username = user.get(SessionManager.username);

        PostEvent newEvent = new PostEvent(this);
        newEvent.execute(new String[]{_name, _date, _starttime, _finishTime, _address, _description, eventImage, username});
    }
}
