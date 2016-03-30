package com.example.samsonaiyegbusi.events2you.Background;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by samsonaiyegbusi on 14/03/16.
 */
public class ByteArrayToBitmap extends AsyncTask<byte[], Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;

    public ByteArrayToBitmap(ImageView imageView){
        imageViewReference = new WeakReference<ImageView>(imageView);
    }



    @Override
    protected Bitmap doInBackground(byte[]... params) {

        byte[] haircutImage = params[0];

        Bitmap bmp = BitmapFactory.decodeByteArray(haircutImage, 0, haircutImage.length);

        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}


