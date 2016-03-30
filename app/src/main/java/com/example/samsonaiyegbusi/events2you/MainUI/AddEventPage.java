package com.example.samsonaiyegbusi.events2you.MainUI;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddEventPage extends AppCompatActivity implements Initialiser {

    ImageView uploadImage;
    ImageButton save_ib;

    Calendar cal;
    //TextView description;
    EditText event_date;
    EditText event_name;
    EditText event_start_time;
    EditText event_finish_time;

    int count;

    Bundle bundle;

    Animation animateButton;

    public static final int image_loaded = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        variableInitialiser();

        widgetInitialiser();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_iv:

                imageOptions();

                break;

            case R.id.event_start_time_et:

                TimePickerDialog startTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int chosenHr, int chosenMin) {

                        String am_pm = "";
                        if (cal.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (cal.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";

                        cal.set(Calendar.HOUR_OF_DAY, chosenHr);
                        cal.set(Calendar.MINUTE, chosenMin);

                        String hr = (cal.get(Calendar.HOUR) == 0) ?"12":cal.get(Calendar.HOUR)+"";
                        event_start_time.setText( hr + ":" + chosenMin+ " " + am_pm);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
                startTime.show();

                break;

            case R.id.event_finish_time_et:

                TimePickerDialog finishTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int chosenHr, int chosenMin) {

                        String am_pm = "";
                        if (cal.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (cal.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";

                        cal.set(Calendar.HOUR_OF_DAY, chosenHr);
                        cal.set(Calendar.MINUTE, chosenMin);

                        String hr = (cal.get(Calendar.HOUR) == 0) ?"12":cal.get(Calendar.HOUR)+"";
                        event_finish_time.setText( hr + ":" + chosenMin+ " " + am_pm);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
                finishTime.show();

                break;

            case R.id.event_date_et:

                DatePickerDialog eventDatePicker;
                eventDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {


                        event_date.setText( selectedday + "-" + selectedmonth + "-" + selectedyear);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                eventDatePicker.setTitle("Select Date");
                eventDatePicker.show();

                break;

            case R.id.next_ib_:

                v.startAnimation(animateButton);

                //check every field as an entry
                if (uploadImage.getDrawable() == null){
                    Toast.makeText(AddEventPage.this, "Please click the ", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (event_name.length() == 0) {
                    Toast.makeText(AddEventPage.this, "Please fill in Event name", Toast.LENGTH_SHORT).show();
                    break;
                } else if (event_date.length() == 0) {
                    Toast.makeText(AddEventPage.this, "Please fill in Event date", Toast.LENGTH_SHORT).show();
                    break;
                } else if (event_start_time.length() == 0) {
                    Toast.makeText(AddEventPage.this, "Please fill in Event date", Toast.LENGTH_SHORT).show();
                    break;
                }


                getDetails();
                getImage();
                Intent takeUserHome = new Intent(AddEventPage.this, FindAddressPage.class);
                takeUserHome.putExtras(bundle);
                startActivity(takeUserHome);


        }
    }

    @Override
    public void variableInitialiser() {

        cal = cal.getInstance();

        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_enter);

        Intent intent = getIntent();
        bundle = intent.getExtras();

    }

    @Override
    public void widgetInitialiser() {

        uploadImage = (ImageView) findViewById(R.id.image_iv);
        save_ib = (ImageButton) findViewById(R.id.next_ib_);
        event_name = (EditText) findViewById(R.id.event_name_et);
        event_date = (EditText) findViewById(R.id.event_date_et);
        event_start_time = (EditText) findViewById(R.id.event_start_time_et);
        event_finish_time = (EditText) findViewById(R.id.event_finish_time_et);

        uploadImage.setOnClickListener(this);
        save_ib.setOnClickListener(this);

        event_finish_time.setOnClickListener(this);
        event_finish_time.setFocusable(false);
        event_finish_time.setClickable(true);


        event_start_time.setOnClickListener(this);
        event_start_time.setFocusable(false);
        event_start_time.setClickable(true);

        event_date.setOnClickListener(this);
        event_date.setFocusable(false);
        event_date.setClickable(true);


        String preSetText = bundle.getString("Event_name");

        if (preSetText != null)
        {

            event_name.setText(bundle.getString("Event_name"));

            // Create a column named "Description"
            event_date.setText(bundle.getString("Event_Date"));

            event_start_time.setText(bundle.getString("Event_time"));

            event_finish_time.setText(bundle.getString("Event_finish_time"));


            byte[] bytes = bundle.getByteArray("fileName");

            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            uploadImage.setImageBitmap(bmp);


        }

    }

    private void imageOptions() {
        final CharSequence[] items = {"Take a picture", "Choose from gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddEventPage.this);
        builder.setTitle("Add an image:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take a picture")) {
                    count = 1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, image_loaded);
                } else if (items[item].equals("Choose from gallery")) {
                    count = 0;
                    Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGallery, image_loaded);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image_loaded && resultCode == RESULT_OK && data != null) {
            if (count == 0) {
                Uri selectedImage = data.getData();
                uploadImage.setBackground(null);
                uploadImage.setImageURI(selectedImage);
            } else if (count == 1){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                uploadImage.setBackground(null);
                uploadImage.setImageBitmap(photo);

            }
        }

    }

    public void getDetails() {

        String Name = event_name.getText().toString().trim();
        String event_Date = event_date.getText().toString().trim();
        String startTime = event_start_time.getText().toString();
        String finishTime = event_finish_time.getText().toString();

        // Create a column named "Description"
        bundle.putString("Event_name", Name);

        // Create a column named "Description"
        bundle.putString("Event_Date", event_Date);

        bundle.putString("Event_time", startTime);

        bundle.putString("Event_finish_time", finishTime);

    }

    public void getImage(){
        // Locate the image in res > drawable-hdpi
        Bitmap event_image = ((BitmapDrawable) uploadImage.getDrawable()).getBitmap();
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Compress image to lower quality scale 1 - 100
        event_image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        bundle.putByteArray("fileName", image);
    }


}
