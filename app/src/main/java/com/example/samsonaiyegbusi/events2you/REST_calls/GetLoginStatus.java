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
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.HashMap;

/**
 * Created by samsonaiyegbusi on 29/03/16.
 */
public class GetLoginStatus extends AsyncTask<String, Void, String> {

    String url = "/users/login?";
    String username;

    ProgressDialog progressDialog;
    Context context;
    SessionManager loginSession;


    public GetLoginStatus(Context context){
        this.context = context;
        loginSession = new SessionManager(this.context);

    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Logging You In");
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
        String password = params[1];
        this.username = username;
        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = ("username="+username+"&password="+password);

        String response = http_methods.GET(url + parameters);
        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {


            if (s.equalsIgnoreCase("Valid")) {
                loginSession.createLoginSession(username);

                GetGenreList genreList = new GetGenreList(context);
                genreList.execute(new String[]{});




            } else {
                Toast.makeText(context, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
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
