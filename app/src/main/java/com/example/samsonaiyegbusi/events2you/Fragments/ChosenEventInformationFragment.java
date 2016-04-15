package com.example.samsonaiyegbusi.events2you.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetEventsByID;
import com.example.samsonaiyegbusi.events2you.REST_calls.PostWatchedEvents;
import com.example.samsonaiyegbusi.events2you.SessionManager;


import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by samsonaiyegbusi on 17/08/15.
 */
public class ChosenEventInformationFragment extends Fragment implements View.OnClickListener{

        public static final String ARG_PAGE = "ARG_PAGE";
        public int page;
        public String event_ID;
        public byte[] imageBytes;

    String startTime;
    String finishTime;
    String date;
    EditText EventAddress;
    TextView EventDescription;

    EditText EventName;

    EditText EventDate;

    EditText Username;
    TextView tags;


   static SessionManager userSession;

    private ImageButton watchevent_ib;

     EventsFactory events;



     static Context c;

    public static ChosenEventInformationFragment create(int page, String event_ID, Context context, byte[] bytes){
        c = context;
        userSession = new SessionManager(context);

        Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            args.putString("ID", event_ID);
        args.putByteArray("image", bytes);
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

        imageBytes = getArguments().getByteArray("image");
        GetEventsByID eventsByID = new GetEventsByID(c);

        try {
            events = eventsByID.execute(new String []{event_ID}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        tags = (TextView) v.findViewById(R.id.showTags);
        tags.setText(events.getTags());
        tags.setFocusable(false);

        startTime = events.getEventStartTime();
        finishTime = events.getEventFinishTime();
        date = events.getEventDate();

        EventName = (EditText) v.findViewById(R.id.showEventName);
        EventName.setText( events.getEventName());
        EventName.setFocusable(false);

        EventDate = (EditText) v.findViewById(R.id.showDate_et);
        if (finishTime == null)
        {
            EventDate.setText("Starts at " + startTime + " On " + date);
        }else {
            EventDate.setText("Starts at " + startTime + " to " + finishTime + " On " + date);

        }
        EventDate.setFocusable(false);

        EventAddress = (EditText) v.findViewById(R.id.showAddress_et);
        EventAddress.setText(events.getEventAddress());
        EventAddress.setFocusable(false);

        EventDescription = (TextView) v.findViewById(R.id.showDescription_tv);
        EventDescription.setText(events.getEventDescription());
        EventDescription.setFocusable(false);

        // EventDescription.setMovementMethod(new ScrollingMovementMethod());

        Username = (EditText) v.findViewById(R.id.username_et);
        Username.setText(events.getEventUsername());
        Username.setFocusable(false);


        watchevent_ib = (ImageButton) v.findViewById(R.id.watchevent_ib);
        watchevent_ib.setOnClickListener(this);
    }

        @Override
        public void onSaveInstanceState(Bundle outState){
            super.onSaveInstanceState(outState);
        }

    @Override
    public void onClick(View v) {

        HashMap<String, String> user = userSession.getUserDetails();

        String username = user.get(SessionManager.username);
        String eventName = EventName.getText().toString();
        String eventDate = date;
        String eventStartTime = startTime;
        String eventFinishTime = finishTime;
        String eventAddress =  EventAddress.getText().toString();
        String eventDescription =  EventDescription.getText().toString();
        String eventImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP + Base64.URL_SAFE);
        String eventID = event_ID;
        String eventOwner = Username.getText().toString();
        String eventTags = tags.getText().toString();

        PostWatchedEvents watchEvent = new PostWatchedEvents(c);
        watchEvent.execute(new String[]{eventName, eventDate, eventStartTime, eventFinishTime, eventAddress, eventDescription, eventImage, username, eventID, eventOwner, eventTags});




    }

}

