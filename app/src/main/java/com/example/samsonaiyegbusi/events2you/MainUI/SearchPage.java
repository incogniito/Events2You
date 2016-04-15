package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Adapters.EventImagesAdapter;
import com.example.samsonaiyegbusi.events2you.GettersAndSetters.EventsFactory;
import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetEventsBySearch;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetLocationSuggestions;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchPage extends AppCompatActivity implements Initialiser, AdapterView.OnItemClickListener{

    ImageButton search;
    AutoCompleteTextView search_phrase;
    AutoCompleteTextView search_location;
    EditText range;
    ListView search_results;
    List<String> locationList;
    List<String> searchSuggestions;

Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();
        populateInterestSearch();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.search_events_ib:

                if (search_phrase.getText().toString().length() == 0){
                    Toast.makeText(this, "Please enter a search phrase", Toast.LENGTH_SHORT).show();
                    break;

                } else  if (search_location.getText().toString().length() == 0){
                    Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
                    break;
                } else  if (range.getText().toString().length() == 0){
                    Toast.makeText(this, "Please enter the range of your search (km)", Toast.LENGTH_SHORT).show();
                    break;
                } else if (!locationList.contains(search_location.getText().toString()))
                {
                    Toast.makeText(this, "This city does not exist", Toast.LENGTH_SHORT).show();
                    break;
                }

                String phrase = search_phrase.getText().toString();
                String location_ = search_location.getText().toString();
                String range_ = range.getText().toString();

                GetEventsBySearch getSearchResult = new GetEventsBySearch(this);
                List<EventsFactory> searchResults = null;
                try {
                    searchResults = getSearchResult.execute(new String[]{phrase, location_, range_}).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (!searchResults.isEmpty()) {
                    search_results.setAdapter(new EventImagesAdapter(this, searchResults));
                } else {

                }

                break;
        }
    }

    @Override
    public void variableInitialiser() {

        Intent intent = getIntent();
        bundle = intent.getExtras();

        searchSuggestions = bundle.getStringArrayList("searchSuggestions");
        GetLocationSuggestions locations = new GetLocationSuggestions(this);
        try {
            locationList = locations.execute(new String[]{}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void widgetInitialiser() {

        search = (ImageButton) findViewById(R.id.search_events_ib);
        search.setOnClickListener(this);
        search_phrase = (AutoCompleteTextView) findViewById(R.id.search_phrase);
        search_location = (AutoCompleteTextView) findViewById(R.id.location_search_phrase);
        range = (EditText) findViewById(R.id.distance_search_phrase);
        search_results = (ListView) findViewById(R.id.search_results_lv);
        search_results.setOnItemClickListener(this);

    }

    private void populateInterestSearch()
    {

        ArrayAdapter<String> searchList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, searchSuggestions);
        search_phrase.setAdapter(searchList);

        ArrayAdapter<String> usernameList = new ArrayAdapter(this, android.R.layout.simple_spinner_item, locationList);
        search_location.setAdapter(usernameList);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


       ImageView imageView = (ImageView) findViewById(R.id.fragment_images_iv);

        //String name = (String) getListAdapter().getItem(position).toString();
        final Bitmap yourBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        final TextView ID = (TextView) findViewById(R.id.identifier);

        final  TextView Event_name = (TextView) findViewById(R.id.Event_name_tv);

        String event_ID = ID.getText().toString();

        String event_name = Event_name.getText().toString();

        bundle.putString("ID", event_ID);
        bundle.putByteArray("image", bytes);
        bundle.putString("Event_name", event_name);

        Intent takeUsertoEvent = new Intent (this, ChosenEventPage.class);
        takeUsertoEvent.putExtras(bundle);
        startActivity(takeUsertoEvent);


    }
}
