package edu.iastate.netid.thingslab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer<ZipResponse> {

    private final String TAG = "MainActivity";

    private ZipResponseViewModel zipResponseViewModel;

    private FusedLocationProviderClient fusedLocationClient;

    //TODO: add Gpio variables for the LED and light sensor

    //TODO: write a GpioCallback that overrides onGpioEdge() with your logic

    private void sendRequest(double lat, double lon) {

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    lat, lon, 1);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String postalCode = addresses.get(0).getPostalCode();
        Log.d(TAG, "Postal code: " + postalCode);

        int zipCode = Integer.parseInt(postalCode);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ZipResponse.getURLForZip(zipCode);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Request successful");
                        zipResponseViewModel.setResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error in request");
            }
        });

        queue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zipResponseViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory())
                .get(ZipResponseViewModel.class);

        zipResponseViewModel.observe(this, this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d(TAG, "Looking for location...");
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d(TAG, "Location task finished.");
                        if(location != null) {
                            Log.d(TAG, "Location found.");
                            sendRequest(location.getLatitude(), location.getLongitude());
                        } else {
                            /* Location doesn't seem to work on RPi.
                                Otherwise use Ames. */
                            sendRequest(42.0308, -93.6319);
                        }
                    }

                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed to get location.", e);
            }
        });

        //TODO: add button listeners to control the light states

        //TODO: add setup code for the Gpio variables
        // The LED gpio should be an output, initially low
        // The sensor gpio should be an input, active high, with a trigger
        // on both edges.

        //TODO: also register your GpioCallback with the sensor Gpio

        //TODO: also set the initial value of the LED Gpio based on
        // the sensor's value

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TODO: add code to close the two Gpios if they are not null

    }

    @Override
    public void onChanged(ZipResponse zipResponse) {
        double temp = zipResponse.getTemperature();
        TextView temperatureTextView = findViewById(R.id.temperatureTextView);
        temperatureTextView.setText(getString(R.string.temperature_string,
                (int)temp));
    }
}
