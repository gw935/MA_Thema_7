package de.fhswf.ma.thema7.game.scenes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.ArrayList;

import de.fhswf.ma.thema7.GameActivity;
import de.fhswf.ma.thema7.MainActivity;
import de.fhswf.ma.thema7.game.Game;
import de.fhswf.ma.thema7.util.Constants;

public class SceneManager implements Scene
{
    private ArrayList<Scene> scenes;
    private int currentScene;

    public SceneManager()
    {
        scenes = new ArrayList<Scene>();
        scenes.add(new Level(this, 1));
        scenes.add(new Level(this, 2));
        scenes.add(new Level(this, 3));
        scenes.add(new Win(this));
        currentScene = 0;
    }

    public int getCurrentScene()
    {
        return currentScene;
    }

    public void nextScene()
    {
        if (currentScene < scenes.size())
        {
            currentScene++;
        }
    }

    public void goToMainmenu()
    {
        Intent intent = new Intent(Constants.CURRENT_CONTEXT, MainActivity.class);
        Constants.CURRENT_CONTEXT.startActivity(intent);
    }

    @Override
    public void update()
    {
        scenes.get(currentScene).update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        scenes.get(currentScene).draw(canvas);
    }

    @Override
    public void recieveTouch(MotionEvent event)
    {
        scenes.get(currentScene).recieveTouch(event);
    }
}
