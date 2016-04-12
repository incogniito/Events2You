package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.Homepage;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by samsonaiyegbusi on 30/03/16.
 */
public class PutBehaviouralUserProfile extends AsyncTask<String, Void, String> {

    String url = "/users/behavioruserprofile";

    ProgressDialog progressDialog;
    Context context;
    SessionManager session;

    public PutBehaviouralUserProfile(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Updating your User Profile");
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

        String eventdescription = params[0];
        String username = params[1];
        String tags = params[2];



        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = null;
        try {
            parameters = ("username="+username+"&eventdescription="+ URLEncoder.encode(eventdescription,"UTF-8")+"&tags="+tags);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String response = http_methods.PUT(url, parameters);

        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {
            if (s.equalsIgnoreCase("Complete")) {
                Toast.makeText(context, "This event has been added to your watch list", Toast.LENGTH_SHORT).show();
                Intent takeUserToLogin = new Intent(context, Homepage.class);
                takeUserToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(takeUserToLogin);
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
