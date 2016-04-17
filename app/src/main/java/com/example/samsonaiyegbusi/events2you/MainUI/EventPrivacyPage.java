package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.media.Image;
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
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Adapters.FriendListAdapter;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class EventPrivacyPage extends AppCompatActivity implements Initialiser{

    ImageButton addfriendsIB;
    ImageButton next;
    AutoCompleteTextView autv;
    ListView lv;
    Boolean recommenderFriends;
    ArrayList<String> friends;
    ArrayList<String> friendsSuggestions;
    Bundle bundle;
    FriendListAdapter adapter;
    SessionManager session;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_privacy_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        variableInitialiser();
        widgetInitialiser();
        populateInterestSearch();
        populateFriendsList();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.add_friends:

                String friend_username = autv.getText().toString();
                 if (friends.contains(friend_username)) {
                    Toast.makeText(this, "You are already friends with this user", Toast.LENGTH_LONG).show();

                    break;
                } else if (friend_username.equalsIgnoreCase(username)) {
                    Toast.makeText(this, "You cannot be friends with yourself", Toast.LENGTH_LONG).show();

                    break;
                } else if (!friends.contains(friend_username))
                 {
                     Toast.makeText(this, "This person is not your friend", Toast.LENGTH_LONG).show();

                     break;
                 }
                if (friends.get(0).equalsIgnoreCase("You have no Friends :(")) {
                    friends.remove(0);
                }
                friends.add(friend_username);

                break;

            case R.id.next_ib:

                if(friends.isEmpty())
                {
                    Toast.makeText(this, "Please choose an invitee",Toast.LENGTH_SHORT).show();
                    break;
                }

                StringBuilder sb = new StringBuilder();
                for (String str : friends) {
                    sb.append(str);
                    if (str != friends.get(friends.size() - 1)) {
                        sb.append(", ");
                    }
                }
                bundle.putString("invitees", sb.toString());
                Intent makePrivateEvent = new Intent (this, AddEventPage.class);
                makePrivateEvent.putExtras(bundle);
                startActivity(makePrivateEvent);

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
        addfriendsIB = (ImageButton) findViewById(R.id.add_friends);
        addfriendsIB.setOnClickListener(this);
        next = (ImageButton) findViewById(R.id.next_ib);
        next.setOnClickListener(this);
        autv = (AutoCompleteTextView) findViewById(R.id.search_friends);
        lv = (ListView) findViewById(R.id.listView);
    }

    private void populateInterestSearch()
    {
        ArrayAdapter<String> usernameList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, friendsSuggestions);
        autv.setAdapter(usernameList);
    }

    private void populateFriendsList(){
        adapter = new FriendListAdapter(this, friends, username, recommenderFriends);
        lv.setAdapter(adapter);
    }

}
