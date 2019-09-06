package edu.iastate.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random mRandom;
    private String[] mMessages = {"Munster", "Tigger", "Catillac", "Kitty Purry", "Crookshanks", "Sylvester", "Felix", "Cheshire"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRandom = new Random();
    }

    public void changeText(View view) {
        TextView textView = (TextView) findViewById(R.id.text);
        int randInt = mRandom.nextInt(mMessages.length);
        textView.setText(mMessages[randInt]);
    }
}
