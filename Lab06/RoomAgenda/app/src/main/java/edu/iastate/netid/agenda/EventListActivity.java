package edu.iastate.netid.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the main activity that is in control when the application is opened.
 * Its corresponding view is activity_event_list, and it displays a list of the titles
 * of the events stored in the database.
 */
public class EventListActivity extends AppCompatActivity {

    private AppDatabase database;
    private List<Event> eventList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        database = AppDatabase.getAppDatabase(this);

        eventList = new ArrayList<>();
        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO - get the selected event's id and pass as an extra in an Intent to start
                // EventDetailsActivity.class
                Intent myIntent = new Intent(EventListActivity.this, EventDetailsActivity.class);
                int uid = eventList.get(i).getUid();
                myIntent.putExtra("uid", uid );
                startActivity(myIntent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO - assign a list of all the events retrieved from database.eventDao().getAll() method to eventList
        eventList = database.eventDao().getAll();

        //TODO - delete old events before they are displayed by the list adapter.
        // Use getEndTimeAsDate and Date's before() to do the comparison. Note that
        // the default constructor of Date creates a Date for the current instant.
        for(Event event : eventList){
            if (event.getEndTimeAsDate().before(new Date())){
                database.eventDao().delete(event);
            }
        }

        eventList = database.eventDao().getAll();

        // Set source of ListView to List of Events in database
        ArrayAdapter<Event> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, eventList);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_event_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_event:
                Intent addIntent = new Intent(this, AddEventActivity.class);
                startActivity(addIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
