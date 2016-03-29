package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.MainUI.LoginPage;

/**
 * Created by samsonaiyegbusi on 29/03/16.
 */
public class PostUser extends AsyncTask<String, Void, String> {

    String url = "/users/newuser";

    ProgressDialog progressDialog;
    Context context;

    public PostUser(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Signing You Up");
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
        String firstname = params[2];
        String surname = params[3];
        String dob = params[4];

        HTTP_Methods http_methods = new HTTP_Methods();
        String parameters = ("username="+username+"&password="+password+"&firstname="+firstname+"&surname="+surname+"&dob="+dob);

        String response = http_methods.POST(url, parameters);
        return response.replace("\n", "");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if (s != null) {


            if (s.equalsIgnoreCase("Successful")) {
                Toast.makeText(context, "Thanks! You have successfully made an account", Toast.LENGTH_SHORT).show();
                Intent takeUserToLogin = new Intent(context, LoginPage.class);
                takeUserToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(takeUserToLogin);
            } else {
                Toast.makeText(context, "The username already exists", Toast.LENGTH_SHORT).show();
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
