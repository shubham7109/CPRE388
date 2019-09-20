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
    private Handler handler;
    private long TIME_START;
    private long CUR_TIME;
    private long ELAPSED_TIME;
    private boolean shouldThreadRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
        timerDisplay = findViewById(R.id.textView);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);
        startButton = findViewById(R.id.start_button);
    }

    public void onStartClicked(View view) {
        shouldThreadRun = true;
        startThread();
        setButtonVisibility(shouldThreadRun);

    }

    public void onResetClicked(View view) {
        shouldThreadRun = false;
        timerDisplay.setText(TIMER_DEFAULT_TEXT);
        TIME_START = 0;
        CUR_TIME = 0;
        ELAPSED_TIME = 0;

        setButtonVisibility(false);
    }

    public void onStopClicked(View view) {
        shouldThreadRun = false;
        CUR_TIME = ELAPSED_TIME;

        setButtonVisibility(false);
    }

    private void startThread(){
        handler = new Handler();

        TIME_START = System.currentTimeMillis();

        final Runnable r = new Runnable() {
            public void run() {

                if(shouldThreadRun){

                    ELAPSED_TIME = (System.currentTimeMillis() - TIME_START) + CUR_TIME;
                    timerDisplay.setText(convertToString(ELAPSED_TIME));

                    handler.postDelayed(this, 10);
                }

            }
        };

        handler.postDelayed(r, 10);
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


    private String convertToString(long millis){

        Date date = new Date(millis);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss.S");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));


        return formatter.format(date);


    }
}
