package netid.iastate.edu.compass.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import netid.iastate.edu.compass.Interfaces.SensorUpdateCallback;
import netid.iastate.edu.compass.Models.FlatCompass;
import netid.iastate.edu.compass.R;

/**
 * This is the activity for the Flat Compass implementation
 */
public class FlatCompassActivity extends AppCompatActivity implements SensorUpdateCallback {
    private FlatCompass mCompass;//used to store the FlatCompass object
    private ImageView mArrow;//used for modifying the shown image view
    private float currentDegree =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        // Instantiate a FlatCompass object
        mCompass = new FlatCompass(this, this);

        mArrow = findViewById(R.id.image);
        mArrow.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.compass));

        Button leftButton = findViewById(R.id.btn1);
        leftButton.setText(R.string.tilt_text);


        Button rightButton = findViewById(R.id.btn2);
        rightButton.setText(R.string.better_compass_text);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCompass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCompass.stop();
    }

    private  int counter = 0;
    private  float avg = 0;
    private final  static  int NUM_ITEMS = 10;

    @Override
    public void update(float orientation) {

        //Wait for update to be called multiple times
        if(counter < NUM_ITEMS){
            avg = avg + orientation;
            counter++;
        }
        else{

            orientation = avg/NUM_ITEMS;
            avg =0;
            counter = 0;

            //mArrow.setRotation(orientation);

            // Rotate to orientation
            RotateAnimation rotateAnimation = new RotateAnimation(
                    currentDegree,
                    orientation,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            rotateAnimation.setDuration(1000);
            rotateAnimation.setFillAfter(true);

            // Start the animation
            mArrow.startAnimation(rotateAnimation);
            currentDegree = orientation;
        }

    }

    public void leftButtonClicked(View view){
        startActivity(new Intent(this, TiltActivity.class));
    }

    public void rightButtonClicked(View view){
        startActivity(new Intent(this, BetterCompassActivity.class));
    }


}