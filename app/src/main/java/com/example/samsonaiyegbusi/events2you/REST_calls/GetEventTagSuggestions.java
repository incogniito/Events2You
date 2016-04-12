package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.samsonaiyegbusi.events2you.MainUI.CategoriseEventPage;

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
public class GetEventTagSuggestions extends AsyncTask<String, Void, List<String>> {

    String url = "/interestsuggestions";
    List<String> suggestions;

    String text;

    ProgressDialog progressDialog;
    Context context;

    Bundle bundle;

    public GetEventTagSuggestions(Context context, Bundle bundle)
    {
        this.context = context;
        this.bundle = bundle;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Retrieving Suggestions");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();    }

    @Override
    protected List<String> doInBackground(String... params) {

        HTTP_Methods http_methods = new HTTP_Methods();
        String response = http_methods.GET(url);

        return parseXML(response);

    }

    public  List<String> parseXML(String xml)
    {
        XmlPullParserFactory factory;
        suggestions = new ArrayList();

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

                        if (tag.equalsIgnoreCase("suggestion"))
                        {
                            suggestions.add(text);
                        }

                        break;
                }
                eventType = parser.next();
            }
            return suggestions;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
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
        }else{
            ArrayList<String> suggestions = (ArrayList<String>) strings;
            bundle.putStringArrayList("SuggestionList", suggestions);
            Intent takeUserToChooseCategory = new Intent(context, CategoriseEventPage.class);
            takeUserToChooseCategory.putExtras(bundle);
            context.startActivity(takeUserToChooseCategory);
        }
        }
}
