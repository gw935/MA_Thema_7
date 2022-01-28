package de.fhswf.ma.thema7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import de.fhswf.ma.thema7.game.Game;
import de.fhswf.ma.thema7.util.Constants;

public class GameActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        // getMetrics() Deprecated in API level 30
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        // 1080 Standard Size
        Constants.SCALE = Constants.SCREEN_WIDTH / 1080.0;
        System.out.println("SCALE: " + Constants.SCALE);
        System.out.println("SCREEN_WIDTH " + Constants.SCREEN_WIDTH);
        System.out.println("SCREEN_HEIGHT " + Constants.SCREEN_HEIGHT);

        setContentView(new Game(this));
    }
}