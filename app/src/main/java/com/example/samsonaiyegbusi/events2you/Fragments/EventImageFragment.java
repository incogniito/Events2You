package com.example.samsonaiyegbusi.events2you.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.samsonaiyegbusi.events2you.R;


public class EventImageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int pages;
    private byte[] bytes;

    ImageView showImage;
    public static EventImageFragment create(int page, byte[] bytes){
        Bundle args = new Bundle();
        args.putByteArray("fileName", bytes);
        args.putInt(ARG_PAGE, page);

        EventImageFragment eventImageFragment = new EventImageFragment();
        eventImageFragment.setArguments(args);
        return eventImageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        pages = getArguments().getInt(ARG_PAGE);
        // displayImage(getView());
    }


    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState ) {
        if(container == null){
            return null;
        }

        View view = inflator.inflate(R.layout.fragment_event_image, container, false);

        showImage = (ImageView) view.findViewById(R.id.chosen_image_iv);
        bytes = getArguments().getByteArray("fileName");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        showImage.setImageBitmap(bmp);
        return view;
    }

}
