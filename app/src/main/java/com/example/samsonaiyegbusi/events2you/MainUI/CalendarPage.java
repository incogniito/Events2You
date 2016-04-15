package com.example.samsonaiyegbusi.events2you.MainUI;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.samsonaiyegbusi.events2you.Adapters.CalendarAdapter;
import com.example.samsonaiyegbusi.events2you.Adapters.WatchListAdapter;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetWatchListEventsByUsername;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.TimeZone;

public class CalendarPage extends AppCompatActivity implements Initialiser {

    ImageButton events_ib;
    ImageButton recommender_ib;
    ImageButton watch_list_ib;
    ListView eventList_date;
    CalendarView calView;
    List<EventsFactory> watchEvents;
    SessionManager usersession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        widgetInitialiser();
        variableInitialiser();
        initialiseCalendar();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.events_ib:

                Intent takeUserHome = new Intent(CalendarPage.this, Homepage.class);
                startActivity(takeUserHome);
                break;

            case R.id.recommended_ib:

                Intent takeUserToRecommender = new Intent(CalendarPage.this, RecommenderPage.class);
                startActivity(takeUserToRecommender);

                break;

            case R.id.watch_list_ib:

                Intent takeUserToCalendar = new Intent(CalendarPage.this, WatchList.class);
                startActivity(takeUserToCalendar);

                break;

        }



    }

    @Override
    public void variableInitialiser() {

        usersession = new SessionManager(this);

        HashMap<String, String> user = usersession.getUserDetails();

        String username = user.get(SessionManager.username);


        GetWatchListEventsByUsername watchList = new GetWatchListEventsByUsername(this);
        try {
            watchEvents = watchList.execute(new String[]{username}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void widgetInitialiser() {

        events_ib = (ImageButton) findViewById(R.id.events_ib);
        recommender_ib = (ImageButton) findViewById(R.id.recommended_ib);
        watch_list_ib = (ImageButton) findViewById(R.id.watch_list_ib);

        eventList_date = (ListView) findViewById(R.id.dailyView_lv);

        calView = (CalendarView) findViewById(R.id.calendarView2);



        events_ib.setOnClickListener(this);

        recommender_ib.setOnClickListener(this);

        watch_list_ib.setOnClickListener(this);


    }



    private void initialiseCalendar() {

        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
         GregorianCalendar cal = new GregorianCalendar();

        //calView.setSelectedWeekBackgroundColor(Color.BLUE);
        cal.setTimeInMillis(calView.getDate());//gets date from calendar view and sets it to greg_calendar

        Date date = cal.getTime();

        String chosenDate = sdf.format(date);
        List<EventsFactory> calEvents = new ArrayList();

        for (EventsFactory event : watchEvents)
        {
            if (event.getEventDate().equalsIgnoreCase(chosenDate))
            {

                calEvents.add(event);
            }
        }

        if (!calEvents.isEmpty())
        {
            eventList_date.setAdapter(new CalendarAdapter(CalendarPage.this,calEvents));
        }


        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                 GregorianCalendar cal2 = new GregorianCalendar();


                cal2.set(year,month,dayOfMonth);//gets date from calendar view and sets it to greg_calendar
                Date date2 = cal2.getTime();
                String chosenDate2 = sdf.format(date2);
                List<EventsFactory> calEvents2 = new ArrayList();
                for (EventsFactory event : watchEvents) {
                    if (event.getEventDate().equalsIgnoreCase(chosenDate2)) {

                        calEvents2.add(event);
                    }
                }

                if (!calEvents2.isEmpty()) {

                    eventList_date.setAdapter(new CalendarAdapter(CalendarPage.this, calEvents2));
                } else {
                  //  CalendarAdapter calendarAdapter2 = new CalendarAdapter(CalendarPage.this, null);

                 //   eventList_date.setAdapter(calendarAdapter2);
                }
            }

        });

    }

}
