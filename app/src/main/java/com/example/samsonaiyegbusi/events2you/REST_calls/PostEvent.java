package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.Homepage;
import com.example.samsonaiyegbusi.events2you.MainUI.LoginPage;

/**
 * Created by samsonaiyegbusi on 30/03/16.
 */
public class PostEvent extends AsyncTask<String, Void, String> {

    String url = "/events/createevent";

    ProgressDialog progressDialog;
    Context context;

    public PostEvent(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Posting Your Event");
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
        String eventDescription = params[5];
        String eventImage = params[6];
        String username = params[7];


        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = ("eventname="+eventName+"&eventdate="+eventDate+"&eventstarttime="+eventStartTime+"&eventfinishtime="+eventFinishTime+"&eventaddress="+eventAddress+"&eventdescription="+eventDescription+"&eventimage="+eventImage+"&username="+username);

        String response = http_methods.POST(url, parameters);
        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {


            if (s.equalsIgnoreCase("Successful")) {
                Toast.makeText(context, "You have successfully created an event", Toast.LENGTH_SHORT).show();
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
