package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.Homepage;
import com.example.samsonaiyegbusi.events2you.MainUI.RecommenderPage;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 30/03/16.
 */
public class PostInterests extends AsyncTask<String, Void, String> {

    String url = "/users/staticuserprofile";

    ProgressDialog progressDialog;
    Context context;
    SessionManager session;

    public PostInterests(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Creating Your User Profile");
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

        String interests = params[0];
        String username = params[1];



        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = ("interests="+interests+"&username="+username);

        String response = http_methods.PUT(url, parameters);
        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {

            Toast.makeText(context, "Your interests have been saved", Toast.LENGTH_SHORT).show();
            session = new SessionManager(context);
            session.addProfiles(s);
            HashMap<String, String> user = session.getUserDetails();

            String username = user.get(SessionManager.username);
            String friends = user.get(SessionManager.friends);

            session.checkProfilesExistance(username,friends);

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
