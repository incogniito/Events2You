package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.MainUI.Homepage;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/03/16.
 */
public class GetEventsByGenre extends AsyncTask<String, Void, String> {



    String url = "/events/eventsbygenre?genre=";
    List<EventsFactory> eventsList;
    EventsFactory events;

    String text;

    ProgressDialog progressDialog;
    Context context;
    SessionManager session;

    public GetEventsByGenre(Context context)
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
        progressDialog.show();    }

    @Override
    protected String doInBackground(String... params) {

        session = new SessionManager(context);
        HashMap<String, String> category = session.getUserDetails();
        String genre = category.get(SessionManager.category);

        HTTP_Methods http_methods = new HTTP_Methods();
        String response = http_methods.GET(url + genre.replace(" ",""));

        return response;

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
        } else {
            session.addMainResponse(strings);
            HashMap<String, String> user = session.getUserDetails();
            String username = user.get(SessionManager.username);
            Toast.makeText(context, "Welcome back " + username, Toast.LENGTH_SHORT).show();

            Intent takeUserToMain = new Intent(context, Homepage.class);
            context.startActivity(takeUserToMain);
        }
    }
}
