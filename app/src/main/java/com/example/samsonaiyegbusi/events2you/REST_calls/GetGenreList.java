package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.example.samsonaiyegbusi.events2you.SessionManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

import Parsers.XMLParser;

/**
 * Created by samsonaiyegbusi on 13/03/16.
 */
public class GetGenreList extends AsyncTask<String, Void, String> {

    String url = "/category";
    List<String> genreList;

    String text;

    ProgressDialog progressDialog;
    Context context;

    SessionManager session;
    public GetGenreList(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Retrieving Category list");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();    }

    @Override
    protected String doInBackground(String... params) {

        HTTP_Methods http_methods = new HTTP_Methods();
        String response = http_methods.GET(url);

        return response;

    }



    @Override
    protected void onPostExecute(String strings) {
        progressDialog.dismiss();
        if (strings == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Our servers are down at the moment, we deeply apologise for this inconvenience");
            builder.setTitle("Sorry");
            builder.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //close tag
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            session = new SessionManager(context);

            XMLParser parseGenre = new XMLParser();
           List<String> genre = parseGenre.parseXMLforGenre(strings);

            StringBuilder sb = new StringBuilder();
            for (String str: genre)
            {
                sb.append(str);
                if (str != genre.get(genre.size() - 1))
                {
                    sb.append(", ");
                }
            }

            session.addcategories(sb.toString());

            GetEventsByGenre getEventsByGenre = new GetEventsByGenre(context);
            getEventsByGenre.execute(new String[]{});
        }
        }
}
