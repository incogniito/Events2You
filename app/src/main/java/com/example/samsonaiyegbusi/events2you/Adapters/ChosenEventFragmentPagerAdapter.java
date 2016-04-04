package com.example.samsonaiyegbusi.events2you.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.samsonaiyegbusi.events2you.Fragments.ChosenEventInformationFragment;
import com.example.samsonaiyegbusi.events2you.Fragments.EventImageFragment;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 18/10/15.
 */
public class ChosenEventFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    byte[] bytes;

    String event_ID;
    Context context;



    public ChosenEventFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, String event_ID, byte[] bytes, Context context )
    {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.bytes = bytes;
        this.event_ID = event_ID;
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {



        switch (position)
        {
            case 0: return EventImageFragment.create(0, bytes);
            case 1: return ChosenEventInformationFragment.create(1, event_ID, context, bytes);

            default:  return EventImageFragment.create(0, bytes);
        }

    }

}
