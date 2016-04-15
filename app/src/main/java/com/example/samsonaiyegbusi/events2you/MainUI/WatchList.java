package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Adapters.WatchListAdapter;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.DeleteWatchEvent;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetWatchListEventsByUsername;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WatchList extends AppCompatActivity implements Initialiser, AdapterView.OnItemClickListener{

    ListView watchlist_lv;
    ImageButton Home_ib;
    ImageButton recommender_ib;
    ImageButton calendar_ib;
    TextView textv13;

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
        switch(v.getId())
        {
            case R.id.wl_home_ib:

                Intent takeUserHome = new Intent(WatchList.this, Homepage.class);
                startActivity(takeUserHome);
                break;

            case R.id.wl_recommended_ib:

                Intent takeUserToRecommender = new Intent(WatchList.this, RecommenderPage.class);
                startActivity(takeUserToRecommender);

                break;

            case R.id.wl_calendar_ib:

                Intent takeUserToCalendar = new Intent(WatchList.this, CalendarPage.class);
                startActivity(takeUserToCalendar);

                break;
        }
    }

    @Override
    public void variableInitialiser() {
        usersession = new SessionManager(this);

    }

    @Override
    public void widgetInitialiser() {

        watchlist_lv = (ListView) findViewById(R.id.watchlist_lv);
        watchlist_lv.setOnItemClickListener(this);

        textv13 = (TextView) findViewById(R.id.textView13);
        textv13.setVisibility(View.INVISIBLE);
        Home_ib = (ImageButton) findViewById(R.id.wl_home_ib);
        recommender_ib = (ImageButton) findViewById(R.id.wl_recommended_ib);
        calendar_ib = (ImageButton) findViewById(R.id.wl_calendar_ib);
        Home_ib.setOnClickListener(this);
        recommender_ib.setOnClickListener(this);
        calendar_ib.setOnClickListener(this);

        HashMap<String, String> user = usersession.getUserDetails();

        String username = user.get(SessionManager.username);

        GetWatchListEventsByUsername getwatchlist = new GetWatchListEventsByUsername(this);
        try {
            List<EventsFactory> watchlist = getwatchlist.execute(new String[]{username}).get();
            if (watchlist.isEmpty())
            {
                textv13.setVisibility(View.VISIBLE);
                textv13.setText("There are no events for you to watch");
            }
            watchlist_lv.setAdapter(new WatchListAdapter(this, watchlist));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

        final CharSequence[] items = {"View Event", "Remove Event"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Event options");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close tag
                dialogInterface.dismiss();
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
              if (items[item].toString().equalsIgnoreCase("View Event"))
              {
                  viewEvent(view);
              } else if(items[item].toString().equalsIgnoreCase("Remove Event"))
              {
                  removeEvent(view);
              }

            }
        });
        builder.show();
    }



    public void viewEvent(View v)
    {
        TextView eventId = (TextView) v.findViewById(R.id.watchlist_ID_tv);

        Bundle bundle = new Bundle();

        ImageView imageView = (ImageView) v.findViewById(R.id.watchlist_iv);

        //String name = (String) getListAdapter().getItem(position).toString();
        final Bitmap yourBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();


        final  TextView Event_name = (TextView) v.findViewById(R.id.watchlist_tv);

        String event_ID = eventId.getText().toString();

        String event_name = Event_name.getText().toString();

        bundle.putString("ID", event_ID);
        bundle.putByteArray("image", bytes);
        bundle.putString("Event_name", event_name);

        Intent takeUsertoEvent = new Intent (v.getContext(), ChosenEventPage.class);
        takeUsertoEvent.putExtras(bundle);
        startActivity(takeUsertoEvent);

    }

    public void removeEvent(View v){

        TextView eventId = (TextView) v.findViewById(R.id.watchlist_ID_tv);
        String event_ID = eventId.getText().toString();

        HashMap<String, String> user = usersession.getUserDetails();

        String username = user.get(SessionManager.username);

        DeleteWatchEvent remove = new DeleteWatchEvent(this);
        remove.execute(new String[]{username, event_ID});
    }
}
