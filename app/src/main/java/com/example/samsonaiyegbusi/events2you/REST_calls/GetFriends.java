package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.AddFriendsPage;
import com.example.samsonaiyegbusi.events2you.MainUI.Homepage;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 29/03/16.
 */
public class GetFriends extends AsyncTask<String, Void, String> {

    String url = "/users/friends?username=";
    String username;

    ProgressDialog progressDialog;
    Context context;
    SessionManager loginSession;
    Bundle bundle;

    public GetFriends(Context context){
        this.context = context;
        loginSession = new SessionManager(this.context);

    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Finding Your friends");
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

        String username = params[0];
        this.username = username;
        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = (username);

        String response = http_methods.GET(url + parameters);
        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {

            ArrayList<String> friendsList = new ArrayList(Arrays.asList(s.split(",")));

            bundle = new Bundle();

            bundle.putStringArrayList("friends",friendsList);
            bundle.putBoolean("friendsRecommender", true);
            Intent takeUserToHome = new Intent(context, AddFriendsPage.class);
            takeUserToHome.putExtras(bundle);
            context.startActivity(takeUserToHome);


        }else if (s == null){
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
