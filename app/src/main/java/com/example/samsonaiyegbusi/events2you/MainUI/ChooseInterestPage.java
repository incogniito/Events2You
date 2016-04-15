package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetRecommendedEvents;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetSuggestions;
import com.example.samsonaiyegbusi.events2you.REST_calls.PostEvent;
import com.example.samsonaiyegbusi.events2you.REST_calls.PostInterests;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChooseInterestPage extends AppCompatActivity implements Initialiser {

    AutoCompleteTextView interestSearch;
    ImageButton addInterest_ib;
    ImageButton recommendEvents_ib;
    TextView interestList_tv;

    GetSuggestions getSuggestions;
    PostInterests postInterests;
    List<String> suggestions;
    List<String> chosenInterests;

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_interest_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();
        populateInterestSearch();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.add_interest_ib:

                String interest =  interestSearch.getText().toString();

                interestSearch.setText("");
                chosenInterests.add(interest);
                addInterestToInterestList();

                break;

            case R.id.recommend_events_ib:

                HashMap<String, String> user = session.getUserDetails();

                String username = user.get(SessionManager.username);

                String interests = interestList_tv.getText().toString();

                postInterests = new PostInterests(this);

                postInterests.execute(new String[]{interests, username});

                break;

            case R.id.interestList_tv:
                RemoveInterestDialog();
                break;
        }
            }

    @Override
    public void variableInitialiser() {
        session = new SessionManager(this);
        chosenInterests = new ArrayList();

        getSuggestions = new GetSuggestions(this);
        try {
            suggestions = getSuggestions.execute(new String[]{}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void widgetInitialiser() {

        interestSearch = (AutoCompleteTextView) findViewById(R.id.interestSearch);
        addInterest_ib = (ImageButton) findViewById(R.id.add_interest_ib);
        addInterest_ib.setOnClickListener(this);
        recommendEvents_ib = (ImageButton) findViewById(R.id.recommend_events_ib);
        recommendEvents_ib.setOnClickListener(this);
        interestList_tv = (TextView) findViewById(R.id.interestList_tv);
        interestList_tv.setOnClickListener(this);
    }

    public void populateInterestSearch()
    {

        ArrayAdapter<String> suggestionList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, suggestions);
        interestSearch.setAdapter(suggestionList);

    }

    public void addInterestToInterestList()
    {

        StringBuilder sb = new StringBuilder();
        for (String str: chosenInterests)
        {
            sb.append(str);
            if (str != chosenInterests.get(chosenInterests.size() - 1))
            {
                sb.append(", ");
            }
        }
        interestList_tv.setText(sb.toString());
    }

    private void RemoveInterestDialog()
    {
        final CharSequence[] items = chosenInterests.toArray(new CharSequence[chosenInterests.size()]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Tag to Remove");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close tag
                dialogInterface.dismiss();
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                chosenInterests.remove(item);

                StringBuilder sb = new StringBuilder();
                for (String str: chosenInterests)
                {
                    sb.append(str);
                    if (str != chosenInterests.get(chosenInterests.size() - 1))
                    {
                        sb.append(", ");
                    }
                }
                if(chosenInterests.isEmpty())
                {
                    interestList_tv.setText("No Interests Chosen");
                }
                else{interestList_tv.setText(sb.toString());}


            }
        });
        builder.show();

    }
}
