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
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Adapters.FriendListAdapter;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetUsers;
import com.example.samsonaiyegbusi.events2you.REST_calls.PutFriends;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddFriendsPage extends AppCompatActivity implements Initialiser{

    GetUsers allUsers;
    List<String> userList;

    AutoCompleteTextView searchFriends;
    ImageButton addUser;
    ImageButton addUserFB;
    ListView friendsList;
    ArrayList<String> friends;

    Bundle bundle;
    FriendListAdapter adapter;

    SessionManager session;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_page);
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
            case R.id.addFriends:

                String username = searchFriends.getText().toString();
                if (!userList.contains(username))
                {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_LONG).show();;
                    break;
                } else if (friends.contains(username))
                {
                    Toast.makeText(this, "You are already friends with this user", Toast.LENGTH_LONG).show();;
                    break;
                }

                friends.add(username);
                PutFriends addFriend = new PutFriends(this);

                StringBuilder sb = new StringBuilder();
                for (String str: friends)
                {
                    sb.append(str);
                    if (str != friends.get(friends.size() - 1))
                    {
                        sb.append(", ");
                    }
                }

                addFriend.execute(new String[]{username, sb.toString()});
                populateFriendsList();
                break;

            case R.id.addFriendFB_ib:

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

        friends = bundle.getStringArrayList("friends");

        allUsers = new GetUsers(this);
        try {
            userList =  allUsers.execute(new String[]{}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void widgetInitialiser() {
        searchFriends = (AutoCompleteTextView) findViewById(R.id.searchFriends);
        addUser = (ImageButton) findViewById(R.id.addFriends);
        addUser.setOnClickListener(this);
        addUserFB = (ImageButton) findViewById(R.id.addFriendFB_ib);
        friendsList = (ListView) findViewById(R.id.friendsList_lv);

    }

    private void populateInterestSearch()
    {

        ArrayAdapter<String> usernameList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userList);
        searchFriends.setAdapter(usernameList);

    }

    private void populateFriendsList(){

        adapter = new FriendListAdapter(this, friends, username);
        friendsList.setAdapter(adapter);
    }

}
