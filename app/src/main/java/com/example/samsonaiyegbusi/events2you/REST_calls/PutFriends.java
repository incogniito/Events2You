package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.RecommenderPage;
import com.example.samsonaiyegbusi.events2you.SessionManager;

/**
 * Created by samsonaiyegbusi on 30/03/16.
 */
public class PutFriends extends AsyncTask<String, Void, String> {

    String url = "/users/addFriends";

    ProgressDialog progressDialog;
    Context context;
    SessionManager session;

    public PutFriends(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Updating your Friends List");
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
        String friends = params[1];



        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = ("username="+username+"&friends="+friends);

        String response = http_methods.PUT(url, parameters);

        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {

            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

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
