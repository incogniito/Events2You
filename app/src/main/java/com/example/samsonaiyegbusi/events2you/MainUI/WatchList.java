package com.example.samsonaiyegbusi.events2you.MainUI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.samsonaiyegbusi.events2you.Adapters.WatchListAdapter;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetWatchListEventsByUsername;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WatchList extends AppCompatActivity implements Initialiser, AdapterView.OnItemClickListener{

    ListView watchlist_lv;

    SessionManager usersession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        variableInitialiser();
        widgetInitialiser();

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void variableInitialiser() {
        usersession = new SessionManager(this);

    }

    @Override
    public void widgetInitialiser() {

        watchlist_lv = (ListView) findViewById(R.id.watchlist_lv);
        watchlist_lv.setOnItemClickListener(this);


        HashMap<String, String> user = usersession.getUserDetails();

        String username = user.get(SessionManager.username);

        GetWatchListEventsByUsername getwatchlist = new GetWatchListEventsByUsername(this);
        try {
            List<EventsFactory> watchlist = getwatchlist.execute(new String[]{username}).get();
            watchlist_lv.setAdapter(new WatchListAdapter(this, watchlist));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
