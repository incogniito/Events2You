package com.example.samsonaiyegbusi.events2you.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Background.ByteArrayToBitmap;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetEventsByGenre;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by samsonaiyegbusi on 13/09/15.
 */
public class EventImagesAdapter extends BaseAdapter{

    List<EventsFactory> events = new ArrayList();
    Context context;

    public EventImagesAdapter(Context context, final String genre){

        this.context = context;
    GetEventsByGenre getEventsByGenre = new GetEventsByGenre(context);
        try {
            events = getEventsByGenre.execute(new String[]{genre}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public EventImagesAdapter(Context context, final String genre, List<EventsFactory> events){

        this.context = context;
        for (EventsFactory event : events){
            if (event.getEventGenre().equalsIgnoreCase(genre))
            {
                this.events.add(event);
            }
        }
    }


    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        notifyDataSetChanged();
        if (v == null){
            LayoutInflater inflator = LayoutInflater.from(context);
            v = inflator.inflate(R.layout.fragment_event_image_list_item, null);
        }
        ImageView eventImage = (ImageView)v.findViewById(R.id.fragment_images_iv);
        byte[] imageFile = events.get(position).getEventImage();
        // Resize the image to 64x64.

        ByteArrayToBitmap convertByteArray = new ByteArrayToBitmap(eventImage);
        convertByteArray.execute(imageFile);

        TextView EventID = (TextView)v.findViewById(R.id.identifier);
        EventID.setText(events.get(position).getEventID());
        EventID.setVisibility(View.INVISIBLE);

        TextView EventName = (TextView)v.findViewById(R.id.Event_name_tv);
        EventName.setText(events.get(position).getEventName());

        return v;
    }
}

