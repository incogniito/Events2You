package com.example.samsonaiyegbusi.events2you.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.samsonaiyegbusi.events2you.Fragments.GenreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 16/08/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {

   public List<String> genre;
    public PagerAdapter(FragmentManager fm, List<String> genre){
        super(fm);
        this.genre = genre;

    }

    @Override
    public Fragment getItem(int position) {

          return GenreFragment.create(position+1, genre.get(position).toString(), 1);

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
