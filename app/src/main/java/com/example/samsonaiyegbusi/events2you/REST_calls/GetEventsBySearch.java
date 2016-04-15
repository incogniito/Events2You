package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsonaiyegbusi on 13/03/16.
 */
public class GetEventsBySearch extends AsyncTask<String, Void, List<EventsFactory>> {

    String url = "/events/search?";
    List<EventsFactory> eventsList;
    EventsFactory events;

    String text;

    ProgressDialog progressDialog;
    Context context;

    public GetEventsBySearch(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Retrieving events");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();    }

    @Override
    protected List<EventsFactory> doInBackground(String... params) {

        String phrase = params[0];
        String location = params[1];
        String range = params[2];

        String parameters = "phrase="+phrase.replace(" ", "+")+"&location="+location+"&range="+range;

        HTTP_Methods http_methods = new HTTP_Methods();
        String response = http_methods.GET(url + parameters);

        return parseXML(response);

    }

    public  List<EventsFactory> parseXML(String xml)
    {
        XmlPullParserFactory factory;
        eventsList = new ArrayList();

        try {
            factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            if (xml == null)
            {
                return null;

            }

            parser.setInput( new StringReader( xml ) );
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = parser.getName();
                switch(eventType)
                {
                    case XmlPullParser.START_TAG:
                        if (tag.equalsIgnoreCase("events"))
                        {
                            events = new EventsFactory();
                        }
                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (tag.equalsIgnoreCase("eventID"))
                        {
                            events.setEventID(text);
                        } else if (tag.equalsIgnoreCase("eventImage"))
                        {
                            byte[] eventImage = Base64.decode(text, Base64.DEFAULT);

                            events.setEventImage(eventImage);
                        } else if (tag.equalsIgnoreCase("eventName"))
                        {
                            events.setEventName(text);
                            eventsList.add(events);
                        } else if (tag.equalsIgnoreCase("stringEventID"))
                    {
                        events.setEventID(text);
                    }
                        break;
                }
                eventType = parser.next();
            }
            return eventsList;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<EventsFactory> strings) {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("No results found");
            builder.setTitle("Sorry");
            builder.setPositiveButton("Search Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //close tag
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
