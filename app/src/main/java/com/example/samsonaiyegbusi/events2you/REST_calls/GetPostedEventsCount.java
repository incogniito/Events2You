package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/03/16.
 */
public class GetPostedEventsCount extends AsyncTask<String, Void, String> {

    String url = "/watchedevents/count?eventid=";
    List<EventsFactory> eventsList;
    EventsFactory events;

    String text;

    ProgressDialog progressDialog;
    Context context;

    public GetPostedEventsCount(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Retrieving events");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String eventid = params[0];

        HTTP_Methods http_methods = new HTTP_Methods();
        String response = http_methods.GET(url + eventid);

        return response.replace("\n", "");

    }



    @Override
    protected void onPostExecute(String strings) {
        progressDialog.dismiss();
        if (strings == null) {
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
