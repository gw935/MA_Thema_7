package de.fhswf.ma.thema7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.fhswf.ma.thema7.util.Constants;

public class MainActivity extends AppCompatActivity
{
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        // getMetrics() Deprecated in API level 30
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        System.out.println("SCREEN_WIDTH " + Constants.SCREEN_WIDTH);
        System.out.println("SCREEN_HEIGHT " + Constants.SCREEN_HEIGHT);

        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.playButton);
    }

    // Change to Gameactivity
    public void playGame(View view)
    {
        System.out.println("Play Button pressed");

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}