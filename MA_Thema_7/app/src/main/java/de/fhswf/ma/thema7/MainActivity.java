package de.fhswf.ma.thema7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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