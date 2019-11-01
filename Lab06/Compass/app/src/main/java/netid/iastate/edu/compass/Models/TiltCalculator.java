package netid.iastate.edu.compass.Models;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;

/**
 * This class is used for managing a TiltCalculator object, we use this to do business logic before updating the UI
 */
public class TiltCalculator implements SensorEventListener {
    private SensorManager mSensorManager;//used to store the SensorManager for use throughout the model class
    private Sensor mAcc;//used to get and start/register the Sensor
    private SensorUpdateCallback mCallback;//used to keep track of the activity to callback to

    public TiltCalculator(Context context, SensorUpdateCallback callback) {

        // Get the Sensor Service using the application context
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // Get an accelerometer
        mAcc =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mCallback = callback;
    }

    /**
     * This method is called from the activity when the sensor listener should be registered
     */
    public void start() {
        // Register accelerometer sensor listener
        mSensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * This method is called from the activity when the sensor listener should be unregistered
     */
    public void stop() {
        // Unregister accelerometer sensor listener
        mSensorManager.unregisterListener(this, mAcc);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float orientation = 0.0f;

        double Gx = event.values[0];
        double Gy = event.values[1];
        double Gz = event.values[2];

        // Determine pitch from accelerometer
        double pitch = Math.atan2(Gy, Math.sqrt(Math.pow(Gx, 2) + Math.pow(Gz, 2)));

        // Determine roll from accelerometer
        double roll = Math.atan2(-Gx, Gz);

        // Determine orientation from pitch and roll
        orientation = (float)(Math.atan2(pitch, roll) * 180 / Math.PI)
                +90.0f;
        
        mCallback.update(orientation);//use callback to call update() in activity
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
