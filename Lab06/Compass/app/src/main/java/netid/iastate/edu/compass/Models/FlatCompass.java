package netid.iastate.edu.compass.Models;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;

/**
 * This class is used for managing a FlatCompass object, we use this to do business logic before updating the UI
 */
public class FlatCompass implements SensorEventListener {
    private SensorManager mSensorManager;//used to store the SensorManager for use throughout the model class
    private Sensor mMagField;//used to get and start/register the Sensor
    private SensorUpdateCallback mCallback;//used to keep track of the activity to callback to

    public FlatCompass(Context context, SensorUpdateCallback callback) {
        // Get the Sensor Service using the application context
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mMagField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); // Get a magnetic field sensor
        mCallback = callback;
    }

    /**
     * This method is called from the activity when the sensor listener should be registered
     */
    public void start() {
        // Register magnetic field sensor listener
        mSensorManager.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * This method is called from the activity when the sensor listener should be unregistered
     */
    public void stop() {
        // Unregister magnetic field sensor listener
        mSensorManager.unregisterListener(this, mMagField);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Calculate the orientation
        double x = event.values[0];
        double y = event.values[1];
        float orientation = (float) (Math.atan2(x, y) * 180 / Math.PI);

        mCallback.update(orientation);//use callback to call update() method in the activity
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing in our scenario
    }
}