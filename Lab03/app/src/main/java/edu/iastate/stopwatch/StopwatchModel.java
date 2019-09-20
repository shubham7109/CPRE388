package edu.iastate.stopwatch;

import android.annotation.SuppressLint;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StopwatchModel {

    private Handler handler;
    private long TIME_START;
    private long CUR_TIME;
    private long ELAPSED_TIME;
    public boolean shouldThreadRun;
    private MainActivity mainActivity;


    public StopwatchModel(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void startThread(){
        handler = new Handler();
        shouldThreadRun = true;
        TIME_START = System.currentTimeMillis();

        final Runnable r = new Runnable() {
            public void run() {

                if(shouldThreadRun){

                    ELAPSED_TIME = (System.currentTimeMillis() - TIME_START) + CUR_TIME;
                    mainActivity.setDisplay(convertToString(ELAPSED_TIME));

                    handler.postDelayed(this, 10);
                }

            }
        };

        handler.postDelayed(r, 10);
    }

    public void resetVals(){
        CUR_TIME = 0;
        ELAPSED_TIME = 0;
        TIME_START = 0;
        shouldThreadRun = false;
    }

    private String convertToString(long millis){

        Date date = new Date(millis);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss.S");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        return formatter.format(date);


    }

    public void handleStop() {
        shouldThreadRun = false;
        CUR_TIME = ELAPSED_TIME;
    }
}
