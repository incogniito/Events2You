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
import com.example.samsonaiyegbusi.events2you.MainUI.WatchList;
import com.example.samsonaiyegbusi.events2you.R;

import java.util.List;

/**
 * Created by samsonaiyegbusi on 03/04/16.
 */
public class WatchListAdapter extends BaseAdapter {

    Context context;
    List<EventsFactory> watchlist;

    public WatchListAdapter(Context context, List<EventsFactory> watchlist){
        this.context = context;
        this.watchlist = watchlist;
    }

    @Override
    public int getCount() {
        return watchlist.size();
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
            v = inflator.inflate(R.layout.watch_list_item, null);
        }

        ImageView watchlist_iv = (ImageView) v.findViewById(R.id.watchlist_iv);
        TextView watchlist_ID_iv = (TextView) v.findViewById(R.id.watchlist_ID_tv);
        watchlist_ID_iv.setVisibility(View.INVISIBLE);
        TextView watchlist_tv = (TextView) v.findViewById(R.id.watchlist_tv);

        byte[] imagebytes =  watchlist.get(position).getEventImage();
        String eventName = watchlist.get(position).getEventName();
        String eventID = watchlist.get(position).getEventID();

        ByteArrayToBitmap parseBytes = new ByteArrayToBitmap(watchlist_iv);
        parseBytes.execute(imagebytes);

        watchlist_ID_iv.setText(eventID);
        watchlist_tv.setText(eventName);


        return v;
    }
}
