package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.REST_calls.GetGenreList;
import com.example.samsonaiyegbusi.events2you.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoriseEventPage extends AppCompatActivity  implements Initialiser{

    ImageButton addTag;
    ImageButton next;
    Spinner category_sp;
    AutoCompleteTextView tagSearch;
    TextView tags;
    ArrayList<String> suggestions;
    List<String> tagsList;
    List<String> categoryList;
    SessionManager session;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorise_event_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();
        PopulateSuggestions();
        PopulateSpinner();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.addTags_ib:
                if(tagSearch.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(this,"Enter a Tag",Toast.LENGTH_SHORT).show();
                    break;
                }

                String tag = tagSearch.getText().toString();

                if (!suggestions.contains(tag))
                {
                    Toast.makeText(this,"You can not use this tag",Toast.LENGTH_SHORT).show();
                    break;
                }


                tagsList.add(tag);
                StringBuilder sb = new StringBuilder();
                for (String str: tagsList)
                {
                    sb.append(str);
                    if (str != tagsList.get(tagsList.size() - 1))
                    {
                        sb.append(", ");
                    }
                }

                tags.setText(sb.toString());
                tagSearch.setText("");
                break;

            case R.id.toEventAddress_ib:

                bundle.putString("tags",tags.getText().toString());
                bundle.putString("category", category_sp.getSelectedItem().toString());

                Intent takeUserHome = new Intent(this, FindAddressPage.class);
                takeUserHome.putExtras(bundle);
                startActivity(takeUserHome);

                break;

            case R.id.tags_tv:

            RemoveTagDialog();

                break;
        }

    }

    @Override
    public void variableInitialiser() {
        tagsList = new ArrayList();

        Intent intent = getIntent();
        bundle = intent.getExtras();
        session = new SessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        String categories = user.get(SessionManager.category);


             categoryList = new ArrayList(Arrays.asList(categories.replace(" ","").split(",")));

    }

    @Override
    public void widgetInitialiser() {
        addTag = (ImageButton) findViewById(R.id.addTags_ib);
        addTag.setOnClickListener(this);
        next = (ImageButton) findViewById(R.id.toEventAddress_ib);
        next.setOnClickListener(this);
        category_sp = (Spinner) findViewById(R.id.category_sp);
        tagSearch = (AutoCompleteTextView) findViewById(R.id.tags_auto);
        tags = (TextView) findViewById(R.id.tags_tv);
        tags.setText("No Tags Chosen");
        tags.setClickable(true);
        tags.setOnClickListener(this);
    }

    public void PopulateSuggestions()
    {
        suggestions = bundle.getStringArrayList("SuggestionList");
        ArrayAdapter<String> suggestionsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, suggestions);
        tagSearch.setAdapter(suggestionsAdapter);

    }

    public void PopulateSpinner()
    {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter(this, R.layout.spinner_xml, categoryList);
        category_sp.setAdapter(categoryAdapter);
    }

    private void RemoveTagDialog()
    {
        final CharSequence[] items = tagsList.toArray(new CharSequence[tagsList.size()]);

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
                tagsList.remove(item);

                StringBuilder sb = new StringBuilder();
                for (String str: tagsList)
                {
                    sb.append(str);
                    if (str != tagsList.get(tagsList.size() - 1))
                    {
                        sb.append(", ");
                    }
                }
                if(tagsList.isEmpty())
                {
                    tags.setText("No Tags Chosen");
                }
                else{tags.setText(sb.toString());}


            }
        });
        builder.show();

    }

}
