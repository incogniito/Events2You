package com.example.samsonaiyegbusi.events2you.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.samsonaiyegbusi.events2you.Fragments.GenreFragment;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;

import java.util.List;

/**
 * Created by samsonaiyegbusi on 16/08/15.
 */
public class RecommenderPagerAdapter extends FragmentPagerAdapter {

   private List<String> genre;
   private List<EventsFactory> recommendedList;
    public RecommenderPagerAdapter(FragmentManager fm, List<String> genre, List<EventsFactory> recommendedList){
        super(fm);
        this.genre = genre;
        this.recommendedList = recommendedList;

    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            default:  return GenreFragment.create(position+1, genre.get(position).toString(), recommendedList);
        }

    }


    @Override
    public int getCount() {
        return genre.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){

            default:
                String friends = "";
                if (genre.get(position).length()>6) {
                     friends = genre.get(position).toString().substring(0, 7);
                }
                if (friends.equalsIgnoreCase("friend:"))
                {
                    String friendname = genre.get(position).toString().substring(7, genre.get(position).length());
                    return "Because you are friends with " + friendname;

                }else {
                    return "Because you like " + genre.get(position).toString();

                }
        }
    }
}
