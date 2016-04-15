package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.Homepage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by samsonaiyegbusi on 30/03/16.
 */
public class PostWatchedEvents extends AsyncTask<String, Void, String> {

    String url = "/watchedevents/addtowatch";

    ProgressDialog progressDialog;
    Context context;

    public PostWatchedEvents(Context context){
        this.context = context;
    }

    String username;
    String eventDescription;
    String tags;

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Adding Event to Your Watch List");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String eventName = params[0];
        String eventDate = params[1];
        String eventStartTime = params[2];
        String eventFinishTime = params[3];
        String eventAddress = params[4];
         eventDescription = params[5];
        String eventImage = params[6];
         username = params[7];
        String eventID = params[8];
        String eventOwner = params[9];
         tags = params[10];


        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = null;
        try {
            parameters = ("eventname="+eventName+"&eventdate="+eventDate+"&eventstarttime="+eventStartTime+"&eventfinishtime="+eventFinishTime+"&eventaddress="+eventAddress+"&eventdescription="+ URLEncoder.encode(eventDescription,"UTF-8")+"&eventimage="+eventImage+"&username="+username+"&eventowner="+eventOwner+"&eventid="+eventID+"&tags="+tags);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String response = http_methods.POST(url, parameters);
        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {
            if (s.equalsIgnoreCase("Successful")) {
                PutBehaviouralUserProfile updateUserProfile = new PutBehaviouralUserProfile(context);
                updateUserProfile.execute(new String[]{eventDescription, username, tags});
            } else {
                Toast.makeText(context, "There has been a problem", Toast.LENGTH_SHORT).show();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Our servers are down at the moment, we deeply apologise for this inconvenience");
            builder.setTitle("Sorry");
            builder.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //close tag
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
