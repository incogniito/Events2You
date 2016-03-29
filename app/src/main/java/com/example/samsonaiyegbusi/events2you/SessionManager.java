package com.example.samsonaiyegbusi.events2you;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.example.samsonaiyegbusi.events2you.MainUI.LoginPage;


import java.util.HashMap;

/**
 * Created by samsonaiyegbusi on 15/03/16.
 */
public class SessionManager  {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context context;


    int mode = 0;

    public static final String username = "username";
    public static final String loggedIn = "loggedIn";

    private int funtionCounter = 0;
    private int count = 0;

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("pref", mode);
        editor = pref.edit();

    }

    public void createLoginSession(String name){
        editor.putBoolean(loggedIn, true);

        editor.putString(username, name);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(username, pref.getString(username, null));

        return user;
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent login = new Intent(context, LoginPage.class);
            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);


            context.startActivity(login);
        }

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(loggedIn, false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent logUserOut = new Intent(context, LoginPage.class);
        logUserOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(logUserOut);
    }
}


