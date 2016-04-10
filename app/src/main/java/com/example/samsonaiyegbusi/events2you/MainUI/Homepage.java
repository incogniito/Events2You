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
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Adapters.PagerAdapter;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetGenreList;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Homepage extends AppCompatActivity implements Initialiser, AdapterView.OnItemClickListener {

    SessionManager mainSession;

    String[] menu = {"Add Event", "Logout"};
    private ListView menuDrawerList;
    private ArrayAdapter<String> menuAdapter;

    Bundle bundle;

    ImageButton calendar_ib;
    ImageButton events_ib;
    ImageButton watch_list_ib;
    ImageButton search_ib;
    ImageButton recommender_ib;
    Animation animateButton;
    Animation animateSearchButton;
    ViewPager viewPager;
    List<String> genre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();
        addDrawerItems();
        initialisePaging();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(animateButton);
        switch (v.getId())
        {
            case R.id.watch_list_ib:

                Intent takeusertoWatch = new Intent(Homepage.this, WatchList.class);
                startActivity(takeusertoWatch);
                break;

            case R.id.recommended_ib:

                HashMap<String, String> user = mainSession.getUserDetails();
                String username = user.get(SessionManager.username);
                mainSession.checkProfilesExistance(username);

        }

    }

    @Override
    public void variableInitialiser() {

        bundle = new Bundle();

        mainSession = new SessionManager(this);

        mainSession.checkLogin();

    }

    @Override
    public void widgetInitialiser() {

        menuDrawerList = (ListView)findViewById(R.id.navList);
        menuDrawerList.setOnItemClickListener(this);

        calendar_ib = (ImageButton) findViewById(R.id.calendar_ib);
        events_ib = (ImageButton) findViewById(R.id.events_ib);
        watch_list_ib = (ImageButton) findViewById(R.id.watch_list_ib);
        search_ib = (ImageButton) findViewById(R.id.search_ib);
        recommender_ib = (ImageButton) findViewById(R.id.recommended_ib);

        viewPager = (ViewPager) findViewById(R.id.view);

        animateButton = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom);
        animateSearchButton = AnimationUtils.loadAnimation(this, R.anim.abc_popup_exit);


        PagerTitleStrip title = (PagerTitleStrip) findViewById(R.id.pager_header);
        title.setTextColor(Color.WHITE);


        calendar_ib.setOnClickListener(this);
        events_ib.setOnClickListener(this);
        watch_list_ib.setOnClickListener(this);
        search_ib.setOnClickListener(this);
        recommender_ib.setOnClickListener(this);

        bundle = new Bundle();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (menu[position].equalsIgnoreCase("Add Event")){

            Intent addEventProcess = new Intent(Homepage.this, AddEventPage.class);
            addEventProcess.putExtras(bundle);
            startActivity(addEventProcess);

        } else if (menu[position].equalsIgnoreCase("Logout"))
        {
            mainSession.logoutUser();
        }
    }

    private void addDrawerItems() {

        menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        menuDrawerList.setAdapter(menuAdapter);
    }

    private void initialisePaging() {
        GetGenreList genreList = new GetGenreList(this);

        try {
            genre = genreList.execute(new String[]{}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final PagerAdapter pgrAdapter = new PagerAdapter(getSupportFragmentManager(), genre);
        viewPager.setAdapter(pgrAdapter);
    }
}
