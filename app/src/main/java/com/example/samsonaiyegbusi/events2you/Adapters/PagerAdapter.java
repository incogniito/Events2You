package com.example.samsonaiyegbusi.events2you.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.samsonaiyegbusi.events2you.Fragments.GenreFragment;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 16/08/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {

   public List<String> genre;
    private List<EventsFactory> recommendedList;
    public PagerAdapter(FragmentManager fm, List<String> genre, List<EventsFactory> recommendedList){
        super(fm);
        this.genre = genre;
        this.recommendedList = recommendedList;

    }

    @Override
    public Fragment getItem(int position) {

          return GenreFragment.create(position+1, genre.get(position).toString(), recommendedList);

    }


    @Override
    public int getCount() {
        return genre.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            default: return genre.get(position).toString();
        }
    }
}
