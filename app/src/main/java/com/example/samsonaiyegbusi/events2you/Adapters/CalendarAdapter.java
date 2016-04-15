package com.example.samsonaiyegbusi.events2you.Adapters;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Background.ByteArrayToBitmap;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.R;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 03/04/16.
 */
public class CalendarAdapter extends BaseAdapter {

    Context context;
    List<EventsFactory> calendarlist;

    public CalendarAdapter(Context context, List<EventsFactory> watchlist){
        this.context = context;

        this.calendarlist = watchlist;

    }

    @Override
    public int getCount() {
        return calendarlist.size();
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
            v = inflator.inflate(R.layout.calendar_item, null);
        }

        TextView EventName = (TextView) v.findViewById(R.id.calendarTitle_tv);
        TextView time = (TextView) v.findViewById(R.id.calenderTime_tv);

        String name = calendarlist.get(position).getEventName();

        EventName.setText(name);
        time.setText("Starts at "+calendarlist.get(position).getEventStartTime());
        return v;

    }
}
