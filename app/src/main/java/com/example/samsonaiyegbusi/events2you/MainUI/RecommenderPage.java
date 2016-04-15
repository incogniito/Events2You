package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.samsonaiyegbusi.events2you.Adapters.PagerAdapter;
import com.example.samsonaiyegbusi.events2you.Adapters.RecommenderPagerAdapter;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetGenreList;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetRecommendedEvents;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetRecommendedEventsOnFriends;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecommenderPage extends AppCompatActivity implements Initialiser{

    ImageButton calendar_ib;
    ImageButton events_ib;
    ImageButton watch_list_ib;
    Animation animateButton;
    Animation animateSearchButton;
    ViewPager viewPager;
    List<String> genre = new ArrayList();

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommender_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        widgetInitialiser();
        initialisePaging();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rc_calendar_ib:

                Intent showCalendar = new Intent(v.getContext(), CalendarPage.class);
                startActivity(showCalendar);
                break;

            case R.id.rc_events_ib:

                Intent takeUserToRecommender = new Intent(RecommenderPage.this, Homepage.class);
                startActivity(takeUserToRecommender);

                break;

            case R.id.rc_watchlist_ib:

                Intent takeUserToCalendar = new Intent(RecommenderPage.this, WatchList.class);
                startActivity(takeUserToCalendar);

                break;

        }
    }

    @Override
    public void variableInitialiser() {



    }

    @Override
    public void widgetInitialiser() {
        viewPager = (ViewPager) findViewById(R.id.rc_view);

        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom);
        animateSearchButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_exit);

        calendar_ib = (ImageButton) findViewById(R.id.rc_calendar_ib);
        events_ib = (ImageButton) findViewById(R.id.rc_events_ib);
        watch_list_ib = (ImageButton) findViewById(R.id.rc_watchlist_ib);

        calendar_ib.setOnClickListener(this);
        events_ib.setOnClickListener(this);
        watch_list_ib.setOnClickListener(this);

        PagerTitleStrip title = (PagerTitleStrip) findViewById(R.id.rc_pager_header);
        title.setTextColor(Color.WHITE);
    }

    private void initialisePaging() {
        GetRecommendedEvents recommendedEvents = new GetRecommendedEvents(this);

        List<EventsFactory>  recommended = null;

        session = new SessionManager(this);
        HashMap<String, String> userprofile = session.getUserDetails();
        String profiles = userprofile.get(SessionManager.profiles);

        try {
              recommended = recommendedEvents.execute(new String[]{ profiles}).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (session.hasrecommenderFriends()){
            String filteredFriends = userprofile.get(SessionManager.recommenderfriends);
            String username = userprofile.get(SessionManager.username);
            List<String> friendsList = new ArrayList(Arrays.asList(filteredFriends.split(",")));

        for (String friend : friendsList) {
                GetRecommendedEventsOnFriends recommendedEventsOnFriends = new GetRecommendedEventsOnFriends(this);

                try {
                    List<EventsFactory> eventsOnFriends = recommendedEventsOnFriends.execute(new String[]{username, friend, profiles }).get();

                    if (eventsOnFriends != null) {
                        recommended.addAll(eventsOnFriends);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        for (EventsFactory events: recommended ){
            if (!genre.contains(events.getEventGenre()))
                genre.add(events.getEventGenre());
        }

        if (recommended != null) {
            final RecommenderPagerAdapter pgrAdapter = new RecommenderPagerAdapter(getSupportFragmentManager(), genre, recommended);
            viewPager.setAdapter(pgrAdapter);
        }
    }
}
