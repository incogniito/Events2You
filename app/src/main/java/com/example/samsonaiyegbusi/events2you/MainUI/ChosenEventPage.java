package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.samsonaiyegbusi.events2you.Adapters.ChosenEventFragmentPagerAdapter;
import com.example.samsonaiyegbusi.events2you.CustomWidgets.VerticalViewPager;
import com.example.samsonaiyegbusi.events2you.Fragments.ChosenEventInformationFragment;
import com.example.samsonaiyegbusi.events2you.Fragments.EventImageFragment;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;

import java.util.List;
import java.util.Vector;

public class ChosenEventPage extends AppCompatActivity implements Initialiser{

    private ChosenEventFragmentPagerAdapter pgrAdapter;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_event_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        variableInitialiser();
        initialisePaging();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void variableInitialiser() {
        Intent intent = getIntent();
        bundle = intent.getExtras();



        String Event_name = bundle.getString("Event_name");
        setTitle(Event_name);
    }

    @Override
    public void widgetInitialiser() {

    }

    private void initialisePaging() {
        List<Fragment> fragments =  new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, ChosenEventInformationFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, EventImageFragment.class.getName()));

        String ID = bundle.getString("ID");
        byte[] bytes = bundle.getByteArray("image");
        // byte[] image = bundle.getByteArray("fileName");

        pgrAdapter = new ChosenEventFragmentPagerAdapter(this.getSupportFragmentManager(), fragments,ID, bytes, this);
        //ViewPager pager = (ViewPager) findViewById(R.id.pager2);
        //ager.setPageTransformer(true, new OrientationChanger());
        //pager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //pgrMod = (ViewPager) findViewById(R.id.pager2);

        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.pager2);
        verticalViewPager.setAdapter(pgrAdapter);

    }
}
