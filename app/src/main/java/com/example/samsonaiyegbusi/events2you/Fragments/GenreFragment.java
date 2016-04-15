package com.example.samsonaiyegbusi.events2you.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Adapters.EventImagesAdapter;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.MainUI.ChosenEventPage;
import com.example.samsonaiyegbusi.events2you.R;

import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * Created by samsonaiyegbusi on 17/08/15.
 */
public class GenreFragment extends ListFragment {

    private EventImagesAdapter eventImagesAdapter;
    public static final String ARG_PAGE = "ARG_PAGE";
    private String genre;
    public ImageView imageView;
      List<EventsFactory> Events = null;
    int functionID;

    Bundle bundle;

    public GenreFragment( List<EventsFactory> events)
    {
        Events = events;

    }

    public GenreFragment( )
    {

    }

    public static GenreFragment create(int page, String genre, int functionID){
        Bundle args = new Bundle();
        args.putInt("functionID",functionID);
        args.putInt(ARG_PAGE, page);
        args.putString("Genre", genre);
        GenreFragment genreFragment = new GenreFragment();
        genreFragment.setArguments(args);
        return genreFragment;
    }

    public static GenreFragment create(int page, String genre, List<EventsFactory> events){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("Genre", genre);
        GenreFragment genreFragment = new GenreFragment(events);
        genreFragment.setArguments(args);
        return genreFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        genre = getArguments().getString("Genre");
        functionID = getArguments().getInt("functionID",functionID);
        bundle = new Bundle();
        this.setRetainInstance(true);
    }

   @Override
   public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState ) {
       View rootView = inflator.inflate(R.layout.fragment_event_image_list, container, false);

       if (functionID==1) {

           eventImagesAdapter = new EventImagesAdapter(getActivity(), genre);
       } else{
           eventImagesAdapter = new EventImagesAdapter(getActivity(), genre, Events);
       }
       setListAdapter(eventImagesAdapter);
       return rootView;
   }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        imageView = (ImageView) v.findViewById(R.id.fragment_images_iv);

        //String name = (String) getListAdapter().getItem(position).toString();
        final Bitmap yourBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        final  TextView ID = (TextView) v.findViewById(R.id.identifier);

        final  TextView Event_name = (TextView) v.findViewById(R.id.Event_name_tv);

        String event_ID = ID.getText().toString();

        String event_name = Event_name.getText().toString();

        bundle.putString("ID", event_ID);
        bundle.putByteArray("image", bytes);
        bundle.putString("Event_name", event_name);

        Intent takeUsertoEvent = new Intent (v.getContext(), ChosenEventPage.class);
        takeUsertoEvent.putExtras(bundle);
        startActivity(takeUsertoEvent);
    }


}

