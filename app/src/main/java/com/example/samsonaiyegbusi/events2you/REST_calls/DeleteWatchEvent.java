package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.Homepage;
import com.example.samsonaiyegbusi.events2you.MainUI.WatchList;
import com.example.samsonaiyegbusi.events2you.SessionManager;

/**
 * Created by samsonaiyegbusi on 29/03/16.
 */
public class DeleteWatchEvent extends AsyncTask<String, Void, String> {

    String url = "/watchedevents/remove?";
    String username;

    ProgressDialog progressDialog;
    Context context;
    SessionManager loginSession;


    public DeleteWatchEvent(Context context){
        this.context = context;
        loginSession = new SessionManager(this.context);

    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Removing Your Event");
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
        String id = params[1];
        this.username = username;
        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = ("id="+id+"&username="+username);

        String response = http_methods.DELETE(url + parameters);
        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {


            if (s.equalsIgnoreCase("Successful")) {
                loginSession.createLoginSession(username);
                Toast.makeText(context, "Event has been removed", Toast.LENGTH_SHORT);
                Intent takeUserToHome = new Intent(context, WatchList.class);
                takeUserToHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(takeUserToHome);
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT);
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
