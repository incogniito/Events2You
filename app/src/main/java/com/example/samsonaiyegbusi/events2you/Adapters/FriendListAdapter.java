package com.example.samsonaiyegbusi.events2you.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Background.ByteArrayToBitmap;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetFriends;
import com.example.samsonaiyegbusi.events2you.REST_calls.PutFriends;

import java.util.List;

/**
 * Created by samsonaiyegbusi on 03/04/16.
 */
public class FriendListAdapter extends BaseAdapter implements View.OnClickListener{

    Context context;
    List<String> friendlist;
    TextView username_tv;
    String username;

    public FriendListAdapter(Context context, List<String> friendlist, String username){
        this.context = context;
        this.friendlist = friendlist;
        this.username = username;
    }


    @Override
    public int getCount() {
        return friendlist.size();
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
            v = inflator.inflate(R.layout.friend_list_item, null);
        }


         username_tv = (TextView) v.findViewById(R.id.friendUsername);
        ImageButton removeFriend = (ImageButton) v.findViewById(R.id.removefriend_ib);
        removeFriend.setOnClickListener(this);

        String friendsName = friendlist.get(position);


        username_tv.setText(friendsName);
        return v;
    }

    @Override
    public void onClick(View v) {
        notifyDataSetChanged();
      String username =   username_tv.getText().toString();
        if (friendlist.contains(username))
        {
            friendlist.remove(username);
        }

        StringBuilder sb = new StringBuilder();
        for (String str: friendlist)
        {
            sb.append(str);
            if (str != friendlist.get(friendlist.size() - 1))
            {
                sb.append(", ");
            }
        }

        PutFriends putFriends = new PutFriends(context);
        putFriends.execute(new String[]{username, sb.toString()});

    }
}
