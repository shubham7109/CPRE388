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
import netid.iastate.edu.compass.Models.BetterCompass;
import netid.iastate.edu.compass.R;

/**
 * This is the activity for the Better Compass implementation
 */
public class BetterCompassActivity extends AppCompatActivity implements SensorUpdateCallback {
    private BetterCompass mCompass;//used to store the BetterCompass object
    private ImageView mArrow;//used for modifying the shown image view
    private float currentDegree = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        // Instantiate a BetterCompass object
        mCompass = new BetterCompass(this, this);

        mArrow = findViewById(R.id.image);
        mArrow.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.compass));

        Button leftButton = findViewById(R.id.btn1);
        leftButton.setText(R.string.flat_compass_text);

        Button rightButton = findViewById(R.id.btn2);
        rightButton.setText(R.string.tilt_text);
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

    @Override
    public void update(float orientation) {
        // Rotate to orientation
        RotateAnimation rotateAnimation = new RotateAnimation(
                currentDegree,
                orientation,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        rotateAnimation.setDuration(100);
        rotateAnimation.setFillAfter(true);

        // Start the animation
        mArrow.startAnimation(rotateAnimation);
        currentDegree = orientation;

    }

    public void leftButtonClicked(View view){
        startActivity(new Intent(this, FlatCompassActivity.class));
    }

    public void rightButtonClicked(View view){
        startActivity(new Intent(this, TiltActivity.class));
    }

}

