package netid.iastate.edu.compass.Models;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;

/**
 * This class is used for managing a BetterCompass object, we use this to do business logic before updating the UI
 */
public class BetterCompass implements SensorEventListener {
    private SensorManager mSensorManager;//used to store the SensorManager for use throughout the model class
    private Sensor mMagField;//used to get and start/register the Sensor
    private Sensor mAcc;//used to get and start/register the Sensor
    private SensorUpdateCallback mCallback;//used to keep track of the activity to callback to

    private float[] mAccelerometerReading = new float[3];
    private float[] mMagnetometerReading = new float[3];

    public BetterCompass(Context context, SensorUpdateCallback callback) {
        // Get the Sensor Service using the application context
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // Get a magnetic field sensor
        mMagField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // Get an accelerometer
        mAcc =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mCallback = callback;
    }

    /**
     * This method is called from the activity when the sensor listeners should be registered
     */
    public void start() {
        // Register the magnetic field and accelerometer listeners
        mSensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * This method is called from the activity when the sensor listeners should be unregistered
     */
    public void stop() {
        // Unregister the magnetic field and accelerometer listeners
        mSensorManager.unregisterListener(this, mMagField);
        mSensorManager.unregisterListener(this, mAcc);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] mRotationMatrix = new float[9];
        float[] mOrientationAngles = new float[3];


        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // Store magnetic field data in mMagnetometerReading
            mMagnetometerReading[0] = event.values[0];
            mMagnetometerReading[1] = event.values[1];
            mMagnetometerReading[2] = event.values[2];

        }
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Store accelerometer data in mAccelerometerReading
            mAccelerometerReading[0]  = event.values[0];
            mAccelerometerReading[1]  = event.values[1];
            mAccelerometerReading[2]  = event.values[2];
        }

        SensorManager.getRotationMatrix(mRotationMatrix, null,
                mAccelerometerReading, mMagnetometerReading);

        SensorManager.getOrientation(mRotationMatrix, mOrientationAngles);



        // Get orientation from magnetometer and accelerometer
        float orientation = (float) (-mOrientationAngles[0] * 180.0f / Math.PI);
        //orientation = 10*(Math.round(orientation/10));

        Log.e("Orientation: ", String.valueOf(orientation));
        mCallback.update(orientation);//use callback to call update() method in the activity
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing in our scenario
    }
}
