package com.example.samsonaiyegbusi.events2you.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetEventsByID;


import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by samsonaiyegbusi on 17/08/15.
 */
public class ChosenEventInformationFragment extends Fragment {

        public static final String ARG_PAGE = "ARG_PAGE";
        public int page;
        public String event_ID;

     EventsFactory events;

     static Context c;

    public static ChosenEventInformationFragment create(int page, String event_ID, Context context){
        c = context;
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            args.putString("ID", event_ID);
        ChosenEventInformationFragment chosenEventInfoFragment = new ChosenEventInformationFragment();
        chosenEventInfoFragment.setArguments(args);
            return chosenEventInfoFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            page = getArguments().getInt(ARG_PAGE);
            this.setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState ) {
            View rootView = inflator.inflate(R.layout.fragment_chosen_event_info, container, false);

            displayData(rootView);

            return rootView;
        }

    private void displayData(final View v){


        event_ID = getArguments().getString("ID");

        GetEventsByID eventsByID = new GetEventsByID(c);

        try {
            events = eventsByID.execute(new String []{event_ID}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String startTime = events.getEventStartTime();
        String finishTime = events.getEventFinishTime();


        EditText EventName = (EditText) v.findViewById(R.id.showEventName);
        EventName.setText( events.getEventName());

        EditText EventDate = (EditText) v.findViewById(R.id.showDate_et);
        EventDate.setText("Starts at " + startTime + " to " + finishTime + " On " + events.getEventDate());

        EditText EventAddress = (EditText) v.findViewById(R.id.showAddress_et);
        EventAddress.setText(events.getEventAddress());

        TextView EventDescription = (TextView) v.findViewById(R.id.showDescription_tv);
        EventDescription.setText(events.getEventDescription());

        EditText username = (EditText) v.findViewById(R.id.username_et);
        username.setText(events.getEventUsername());
    }

        @Override
        public void onSaveInstanceState(Bundle outState){
            super.onSaveInstanceState(outState);
        }
    }

