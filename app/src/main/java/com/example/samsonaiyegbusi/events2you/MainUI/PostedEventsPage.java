package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Adapters.WatchListAdapter;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetPostedEventsCount;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetPostedListEventsByUsername;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetWatchListEventsByUsername;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostedEventsPage extends AppCompatActivity implements Initialiser, AdapterView.OnItemClickListener{

    ListView postlist_lv;
    TextView textv15;

    SessionManager usersession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_events_page);
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

        postlist_lv = (ListView) findViewById(R.id.listView2);
        postlist_lv.setOnItemClickListener(this);

        textv15 = (TextView) findViewById(R.id.textView16);
        textv15.setVisibility(View.INVISIBLE);


        HashMap<String, String> user = usersession.getUserDetails();

        String username = user.get(SessionManager.username);

        GetPostedListEventsByUsername getwatchlist = new GetPostedListEventsByUsername(this);
        try {
            List<EventsFactory> watchlist = getwatchlist.execute(new String[]{username}).get();
            if (watchlist.isEmpty())
            {
                textv15.setVisibility(View.VISIBLE);
                textv15.setText("You have not posted any events");
                TextView tv = (TextView) findViewById(R.id.textView17);
                tv.setVisibility(View.INVISIBLE);
            }
            postlist_lv.setAdapter(new WatchListAdapter(this, watchlist));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        TextView eventId = (TextView) v.findViewById(R.id.watchlist_ID_tv);
        String event_ID = eventId.getText().toString();

        GetPostedEventsCount postCount = new GetPostedEventsCount(this);
        try {
            String count = postCount.execute(new String[]{event_ID}).get();
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Views");
            builder.setMessage(count + " people currently watching your event");
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //close tag
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
