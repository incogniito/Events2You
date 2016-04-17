package Parsers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * Created by samsonaiyegbusi on 17/04/16.
 */
public class XMLParser {

    public List<EventsFactory> parseXMLforList(String xml, Context context)
    {

       ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();
        XmlPullParserFactory factory;
       List<EventsFactory> eventsList = new ArrayList();
        EventsFactory events = null;
        String text = "";

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
                        }else if (tag.equalsIgnoreCase("category"))
                        {
                            events.setEventGenre(
                                    text);
                        }
                        break;
                }
                eventType = parser.next();
            }
            progressDialog.dismiss();
            return eventsList;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  List<String> parseXMLforGenre(String xml)
    {
        XmlPullParserFactory factory;
       List<String> genreList = new ArrayList();
        String text = "";


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

                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (tag.equalsIgnoreCase("category"))
                        {
                            genreList.add(text);
                        }

                        break;
                }
                eventType = parser.next();
            }
            return genreList;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
