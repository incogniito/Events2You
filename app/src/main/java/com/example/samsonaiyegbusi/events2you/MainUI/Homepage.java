package com.example.samsonaiyegbusi.events2you.MainUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.samsonaiyegbusi.events2you.Initialiser;
import com.example.samsonaiyegbusi.events2you.R;
import com.example.samsonaiyegbusi.events2you.SessionManager;

public class Homepage extends AppCompatActivity implements Initialiser, AdapterView.OnItemClickListener {

    SessionManager mainSession;

    String[] menu = {"Add Event", "Logout"};
    private ListView menuDrawerList;
    private ArrayAdapter<String> menuAdapter;

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        variableInitialiser();
        widgetInitialiser();
        addDrawerItems();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void variableInitialiser() {

        bundle = new Bundle();

        mainSession = new SessionManager(this);

        mainSession.checkLogin();

    }

    @Override
    public void widgetInitialiser() {

        menuDrawerList = (ListView)findViewById(R.id.navList);
        menuDrawerList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (menu[position].equalsIgnoreCase("Add Event")){

            Intent addEventProcess = new Intent(Homepage.this, AddEventPage.class);
            addEventProcess.putExtras(bundle);
            startActivity(addEventProcess);

        } else if (menu[position].equalsIgnoreCase("Logout"))
        {
            mainSession.logoutUser();
        }
    }

    private void addDrawerItems() {

        menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        menuDrawerList.setAdapter(menuAdapter);
    }
}
