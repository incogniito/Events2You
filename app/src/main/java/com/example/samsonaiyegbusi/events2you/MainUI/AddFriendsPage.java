package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.samsonaiyegbusi.events2you.REST_calls.GetUsers;
import com.example.samsonaiyegbusi.events2you.REST_calls.PutFriends;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import org.w3c.dom.Text;

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

    Boolean recommenderFriends;
    ImageButton saveFriendFilter;



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

                String friend_username = searchFriends.getText().toString();
                if (!userList.contains(friend_username))
                {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_LONG).show();;
                    break;
                } else if (friends.contains(friend_username))
                {
                    Toast.makeText(this, "You are already friends with this user", Toast.LENGTH_LONG).show();;
                    break;
                }  else if (friend_username.equalsIgnoreCase(username))
                {
                    Toast.makeText(this, "You cannot be friends with yourself", Toast.LENGTH_LONG).show();;
                    break;
                }

                if (recommenderFriends!=false) {
                    if (friends.get(0).equalsIgnoreCase("You have no Friends :(")) {
                        friends.remove(0);
                    }
                    searchFriends.setText("");
                    friends.add(friend_username);
                    PutFriends addFriend = new PutFriends(this);

                    StringBuilder sb = new StringBuilder();
                    for (String str : friends) {
                        sb.append(str);
                        if (str != friends.get(friends.size() - 1)) {
                            sb.append(", ");
                        }
                    }

                    session.addfriends(sb.toString());

                    addFriend.execute(new String[]{username, sb.toString()});
                    populateFriendsList();
                } else {
                    if (friends.get(0).equalsIgnoreCase("You have no Friends :(")) {
                        friends.remove(0);
                    }
                    friends.add(friend_username);
                    populateFriendsList();
                }
                break;

            case R.id.addFriendFB_ib:





                break;

            case R.id.saveFriendFilter:

                StringBuilder sb = new StringBuilder();
                for (String str : friends) {
                    sb.append(str);
                    if (str != friends.get(friends.size() - 1)) {
                        sb.append(", ");
                    }
                }
                session.addrecommenderfriends(sb.toString());

                Intent takeUserToRecommender = new Intent(this, RecommenderPage.class);
                takeUserToRecommender.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                takeUserToRecommender.putExtras(bundle);

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
        saveFriendFilter = (ImageButton) findViewById(R.id.saveFriendFilter);
        saveFriendFilter.setVisibility(View.INVISIBLE);

        if (recommenderFriends == false){
            TextView tv5 = (TextView) findViewById(R.id.textView5);
            tv5.setText("Filter Recommendation on Friends");
            tv5.setTextSize(10);
            TextView tv8 = (TextView) findViewById(R.id.textView8);
            tv8.setText("Your Friends Filter List");
            TextView tv6 = (TextView) findViewById(R.id.textView6);

            addUserFB.setVisibility(View.INVISIBLE);
            tv6.setVisibility(View.INVISIBLE);
            saveFriendFilter.setVisibility(View.VISIBLE);
            saveFriendFilter.setOnClickListener(this);
        }

    }

    private void populateInterestSearch()
    {
        if (recommenderFriends!=false) {
            ArrayAdapter<String> usernameList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userList);
            searchFriends.setAdapter(usernameList);
        } else {
            ArrayAdapter<String> usernameList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, friends);
            searchFriends.setAdapter(usernameList);
        }

    }

    private void populateFriendsList(){

        if (recommenderFriends!= false){
        adapter = new FriendListAdapter(this, friends, username);
        friendsList.setAdapter(adapter);
        }
        else{
            adapter = new FriendListAdapter(this, friends, username, recommenderFriends);
            friendsList.setAdapter(adapter);
        }
    }

}
