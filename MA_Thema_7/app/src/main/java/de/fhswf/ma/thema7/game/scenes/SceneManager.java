package de.fhswf.ma.thema7.game.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

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
        currentScene = 0;
    }

    public int getCurrentScene()
    {
        return currentScene;
    }

    public void nextScene()
    {
        if(currentScene < scenes.size())
        {
            currentScene++;
        }
    }

    @Override
    public void update()
    {
        scenes.get(currentScene).update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        scenes.get(currentScene).draw(canvas);
    }

    @Override
    public void recieveTouch(MotionEvent event)
    {
        scenes.get(currentScene).recieveTouch(event);
    }
}
