package com.example.samsonaiyegbusi.events2you;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.MainUI.AddFriendsPage;
import com.example.samsonaiyegbusi.events2you.MainUI.ChooseInterestPage;
import com.example.samsonaiyegbusi.events2you.MainUI.FilterFriendsPage;
import com.example.samsonaiyegbusi.events2you.MainUI.LoginPage;
import com.example.samsonaiyegbusi.events2you.MainUI.RecommenderPage;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetEventsByGenre;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetGenreList;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetRecommendedEvents;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetRecommendedEventsOnFriends;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetUpdatedUserProfiles;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetUserProfiles;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public static final String profiles = "profiles";
    public static final String profilesAdded = "profilesAdded";
    public static final String friends = "friends";
    public static final String friendsAdded = "friendsAdded";
    public static final String recommenderfriends = "recommenderfriends";
    public static final String recommenderfriendsAdded = "recommenderfriendsAdded";
    public static final String response = "response";
    public static final String mainResponse = "mainResponse";
    public static final String category = "category";
    private HashMap<String, String> user = new HashMap<String, String>();

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

    public void addProfiles(String userProfiles){
        editor.putBoolean(profilesAdded, true);

        editor.putString(profiles, userProfiles);

        editor.commit();
    }
    public void addResponse(String response_){

        editor.putString(response, response_);

        editor.commit();
    }   public void addMainResponse(String response_){

        editor.putString(mainResponse, response_);

        editor.commit();
    }


    public void addrecommenderfriends(String userProfiles){
        editor.putBoolean(recommenderfriendsAdded, true);

        editor.putString(recommenderfriends, userProfiles);

        editor.commit();
    }

    public void addcategories (String categories){
        editor.putString(category, categories);

        editor.commit();
    }

    public void addfriends(String allfriends){

        editor.putBoolean(friendsAdded, true);


        editor.putString(friends, allfriends);

        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){

        user.put(username, pref.getString(username, null));
        user.put(profiles, pref.getString(profiles, null));
        user.put(friends, pref.getString(friends, null));
        user.put(recommenderfriends, pref.getString(recommenderfriends, null));
        user.put(response, pref.getString(response, null));
        user.put(mainResponse, pref.getString(mainResponse, null));
        user.put(category, pref.getString(category, null));

        return user;
    }



    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent login = new Intent(context, LoginPage.class);
            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);


            context.startActivity(login);
        }else{
            GetUpdatedUserProfiles getUpdatedUserProfiles = new GetUpdatedUserProfiles(context);
            getUpdatedUserProfiles.execute(new String[]{});

        }


    }

    public void checkProfilesExistance(String name, String friends){
        if(!this.profilesExist()){
            GetUserProfiles userProfiles = new GetUserProfiles(context);
            userProfiles.execute(new String[]{pref.getString(username, name)});
        }else{
            if(!hasFriends()) {
                GetRecommendedEvents recommendEvents = new GetRecommendedEvents(context,0);
                recommendEvents.execute(new String[]{});
            } else {
                if (!hasrecommenderFriends())
                {

                    ArrayList<String> friendsList = new ArrayList(Arrays.asList(friends.split(",")));
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("friendsRecommender", false);
                    bundle.putStringArrayList("friends",friendsList);
                    Intent takeUserToChooseFriends = new Intent(context, FilterFriendsPage.class);
                    takeUserToChooseFriends.putExtras(bundle);
                    context.startActivity(takeUserToChooseFriends);
                } else{
                    GetRecommendedEvents recommendEvents = new GetRecommendedEvents(context,1);
                    recommendEvents.execute(new String[]{});
                }
            }
        }

    }



    public boolean isLoggedIn(){
        return pref.getBoolean(loggedIn, false);
    }

    public boolean profilesExist(){
        return pref.getBoolean(profilesAdded, false);
    }

    public boolean hasFriends(){
        return pref.getBoolean(friendsAdded, false);
    }
    public boolean hasrecommenderFriends(){
        return pref.getBoolean(recommenderfriendsAdded, false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent logUserOut = new Intent(context, LoginPage.class);
        logUserOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(logUserOut);
    }
}


