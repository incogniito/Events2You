package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;

import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.MainUI.RecommenderPage;
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
public class GetRecommendedEvents extends AsyncTask<String, Void, String> {



    String url = "/events/recommendonuserprofiles?";

    ProgressDialog progressDialog;
    Context context;
    SessionManager session;
    String profiles;
    HashMap<String, String> userprofile;
    int functionID ;

    public GetRecommendedEvents(Context context, int functionID)
    {
        this.context = context;
        this.functionID = functionID;
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
         userprofile = session.getUserDetails();
        profiles = userprofile.get(SessionManager.profiles);


        String parameters = "userprofiles="+profiles.replaceAll(" ","");

        HTTP_Methods http_methods = new HTTP_Methods();
        String response = http_methods.GET(url + parameters);


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
            if (functionID == 0) {
                Intent recommendEvents = new Intent(context, RecommenderPage.class);
                session.addResponse(strings);
                context.startActivity(recommendEvents);
            } else {
                if (session.hasrecommenderFriends()) {
                    String filteredFriends = userprofile.get(SessionManager.recommenderfriends);
                    String username = userprofile.get(SessionManager.username);
                    GetRecommendedEventsOnFriends recommendedEventsOnFriends = new GetRecommendedEventsOnFriends(context, strings);
                    recommendedEventsOnFriends.execute(new String[]{username, filteredFriends, profiles});
                }


            }
        }
    }
}
