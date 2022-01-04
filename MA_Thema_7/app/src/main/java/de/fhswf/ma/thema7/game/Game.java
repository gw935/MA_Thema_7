package de.fhswf.ma.thema7.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import de.fhswf.ma.thema7.game.scenes.Level;
import de.fhswf.ma.thema7.util.Constants;

public class Game extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread thread;

    private Level level1;

    public Game(Context context)
    {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        level1 = new Level(1);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder)
    {
        // new instance of GameThread
        thread = new GameThread(getHolder(), this);
        // used in gameloop
        Constants.INIT_TIME = System.currentTimeMillis();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder)
    {
        // thread is paused if it isnt in focus
        boolean retry = true;
        // while loop because it takes a few attempts
        while (retry)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    /**
     * Gamelogic is updated
     */
    public void update()
    {
        level1.update();
    }

    /**
     * Game is rendered
     *
     * @param canvas the canvas on which everything is drawn on
     */
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        level1.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        level1.recieveTouch(event);

        return true;
    }
}