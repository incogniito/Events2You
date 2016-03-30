package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;

public class EventDescriptionPage extends AppCompatActivity implements Initialiser {

    EditText description;
    ImageButton Preview;
    Bundle bundle;
    Animation animateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preview_ib:

                v.startAnimation(animateButton);

                if (description.getText().toString().length() == 0) {
                    Toast.makeText(EventDescriptionPage.this, "Describe your event", Toast.LENGTH_SHORT).show();
                    break;
                }

                uploadDescription();

                Intent TakeUserToPreview = new Intent(EventDescriptionPage.this, PreviewPage.class);
                TakeUserToPreview.putExtras(bundle);
                startActivity(TakeUserToPreview);

                break;
        }
    }

    @Override
    public void variableInitialiser() {
        Intent intent = getIntent();
        bundle = intent.getExtras();
    }

    @Override
    public void widgetInitialiser() {

        description = (EditText) findViewById(R.id.description_et);
        Preview = (ImageButton) findViewById(R.id.preview_ib);
        ImageView Event_image = (ImageView) findViewById(R.id.event_image_iv);



        String preSetText = bundle.getString("Description");

        if (preSetText != null)
        {
            description.setText(bundle.getString("Description"));
        }

        // Get the ImageView from
        // main.xml

        retrieveImage(Event_image);

        Preview.setOnClickListener(this);

        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter);

    }

    public void retrieveImage(final ImageView view) {
        // String Name = add_event.event_name.getText().toString();
        // Set the Bitmap into the
        // ImageView
        byte[] bytes = bundle.getByteArray("fileName");

        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        view.setImageBitmap(bmp);
    }

    public void uploadDescription(){
        String Description = description.getText().toString();
        bundle.putString("Description", Description);
    }

}
