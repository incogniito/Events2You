package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Adapters.FriendListAdapter;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetRecommendedEvents;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetUsers;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FilterFriendsPage extends AppCompatActivity implements Initialiser {


    AutoCompleteTextView searchFriends;
    ImageButton addUser;
    ListView friendsList;
    ArrayList<String> friends;
    ArrayList<String> friendsSuggestions;

    Bundle bundle;
    FriendListAdapter adapter;

    SessionManager session;
    String username;

    Boolean recommenderFriends;
    ImageButton saveFriendFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_friends_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();
        populateInterestSearch();
        populateFriendsList();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.saveFriendFilter:

                StringBuilder sb = new StringBuilder();
                for (String str : friends) {
                    sb.append(str);
                    if (str != friends.get(friends.size() - 1)) {
                        sb.append(", ");
                    }
                }
                session.addrecommenderfriends(sb.toString());

                Toast.makeText(this, "Friends saved", Toast.LENGTH_SHORT).show();
                boolean fromHome = bundle.getBoolean("fromHome");
                if (fromHome != true) {
                    GetRecommendedEvents recommendEvents = new GetRecommendedEvents(this,1);
                    recommendEvents.execute(new String[]{});
                }

                break;

            case R.id.addFriends:

                String friend_username = searchFriends.getText().toString();
                if (!friendsSuggestions.contains(friend_username)) {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_LONG).show();

                    break;
                } else if (friends.contains(friend_username)) {
                    Toast.makeText(this, "You are already friends with this user", Toast.LENGTH_LONG).show();

                    break;
                } else if (friend_username.equalsIgnoreCase(username)) {
                    Toast.makeText(this, "You cannot be friends with yourself", Toast.LENGTH_LONG).show();

                    break;
                }
                if (friends.get(0).equalsIgnoreCase("You have no Friends :(")) {
                    friends.remove(0);
                }
                friends.add(friend_username);

                break;
        }
        }



    @Override
    public void variableInitialiser() {
        session = new SessionManager(this);

        HashMap<String, String> user = session.getUserDetails();

        username = user.get(SessionManager.username);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        recommenderFriends = bundle.getBoolean("friendsRecommender");


        friends = bundle.getStringArrayList("friends");
        friendsSuggestions = bundle.getStringArrayList("friends");


    }

    @Override
    public void widgetInitialiser() {
        searchFriends = (AutoCompleteTextView) findViewById(R.id.searchFriends);
        addUser = (ImageButton) findViewById(R.id.addFriends);
        addUser.setOnClickListener(this);
        friendsList = (ListView) findViewById(R.id.friendsList_lv);
        saveFriendFilter = (ImageButton) findViewById(R.id.saveFriendFilter);
        saveFriendFilter.setOnClickListener(this);
    }

    private void populateInterestSearch()
    {
            ArrayAdapter<String> usernameList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, friendsSuggestions);
            searchFriends.setAdapter(usernameList);
    }

    private void populateFriendsList(){
            adapter = new FriendListAdapter(this, friends, username, recommenderFriends);
            friendsList.setAdapter(adapter);
    }

}
