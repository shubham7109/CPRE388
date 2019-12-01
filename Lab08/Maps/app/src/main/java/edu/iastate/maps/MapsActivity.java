package edu.iastate.maps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocation;
    private double userLat = 0.0, userLong = 0.0;
    private int locationRequestCode = 1000;
    private Geocoder geocoder;
    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocation = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);

        } else {
            //Permission is already granted
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void updateMarker(Location location) {
        userLat = location.getLatitude();
        userLong = location.getLongitude();

        // Add a marker in Sydney and move the camera
        LatLng userLocation = new LatLng(userLat, userLong);
        mMap.addMarker(new MarkerOptions().position(userLocation ).title("User location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));


        geocoder = new Geocoder(this,Locale.getDefault());

        mMap.setOnMapClickListener(point -> {
            try {
                float[] distance = new float[3];
                addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                Location.distanceBetween(userLat,userLong,point.latitude,point.longitude,distance);

                Toast.makeText(MapsActivity.this, "It's "+  (Math.round((distance[0]/1609.344) * 100.0) / 100.0) + "miles to "+ addresses.get(0).getLocality(), Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Toast.makeText(MapsActivity.this, "Address not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        Toast.makeText(this, "Tap on location, to find distance!", Toast.LENGTH_SHORT).show();



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(
                this, R.raw.mapstyle_mine));
        fusedLocation.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                updateMarker(location);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                //updateMarker();
                Log.e("test","test");
            else
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
