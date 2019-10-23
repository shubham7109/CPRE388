package edu.iastate.netid.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * This activity is called when the "Add Event" button is pressed on the home page
 * of this app. It pulls in the data from the activity_add_event view and uses the
 * AgendaDataSource class to create the Event object and store its contents in
 * the database.
 */
public class AddEventActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        database = AppDatabase.getAppDatabase(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_add_event, menu);
        return true;
    }

    /**
     * This is called when the Cancel button is pressed and closes
     * this activity, redirecting the user to the main activity
     * @param view
     */
    public void goBack(View view) {
        Intent homeIntent = new Intent(this, EventListActivity.class);
        startActivity(homeIntent);
    }

    /**
     * This is called when the Add button is pressed. It takes the data that
     * the user entered from the views and sends it to the data source to store
     * in the database. It then redirects the user to the main activity.
     * @param view
     */
    public void addEvent(View view) {

        // Collecting user-entered data from views
        EditText titleText = findViewById(R.id.titleInput);
        EditText locText = findViewById(R.id.locationInput);
        DatePicker startDate = findViewById(R.id.startDateInput);
        TimePicker startTime = findViewById(R.id.startTimeInput);
        DatePicker endDate = findViewById(R.id.endDateInput);
        TimePicker endTime = findViewById(R.id.endTimeInput);
        EditText detailText = findViewById(R.id.detailsInput);

        //TODO - create an event object using UI variables above. Use below helper method
        // formatDateTime() to format date strings


        //TODO - insert your newly created event into the database


        // Redirect to main activity
        Intent homeIntent = new Intent(this, EventListActivity.class);
        startActivity(homeIntent);

    }


    /*
     * Helper method to format the user input into a readable date and time
     */
    private String formatDateTime(int month, int day, int year, int hour, int minute) {
        Calendar cal = new GregorianCalendar(year, month, day, hour, minute);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy, 'at' h:mm a", Locale.US);
        return sdf.format(cal.getTime());
    }

}

