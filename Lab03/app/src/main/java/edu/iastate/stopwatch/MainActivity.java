package edu.iastate.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TIMER_DEFAULT_TEXT = "00:00:00.0";
    private TextView timerDisplay;
    private Button stopButton;
    private Button resetButton;
    private Button startButton;
    private StopwatchModel stopwatchModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        stopwatchModel = new StopwatchModel(this);

    }

    private void initViews() {
        timerDisplay = findViewById(R.id.textView);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);
        startButton = findViewById(R.id.start_button);
    }

    public void onStartClicked(View view) {
        stopwatchModel.startThread();
        setButtonVisibility(true);

    }

    public void onResetClicked(View view) {
        stopwatchModel.resetVals();
        timerDisplay.setText(TIMER_DEFAULT_TEXT);

        setButtonVisibility(false);
    }

    public void onStopClicked(View view) {
        stopwatchModel.handleStop();
        setButtonVisibility(false);
    }



    private void setButtonVisibility(boolean isRunning){
        if(isRunning){
            stopButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.GONE);
            startButton.setVisibility(View.GONE);
        } else{
            stopButton.setVisibility(View.GONE);
            resetButton.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
        }
    }

    public void setDisplay(String displayText){
        timerDisplay.setText(displayText);
    }

}
