package de.fhswf.ma.thema7.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
    public static final int MAX_FPS = 60;
    private double averageFPS;

    private SurfaceHolder surfaceHolder;
    private Game game;

    public static Canvas canvas;

    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, Game game)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    @Override
    public void run()
    {
        // Gameloop with fixed timesteps

        long previous;
        // number of Frames
        long elapsedMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running)
        {
            //
            previous = System.nanoTime();
            canvas = null;

            // currentTime - previous in milliseconds
            elapsedMillis = (System.nanoTime() - previous) / 1000000;
            // how long the game has to wait for next update
            waitTime = targetTime - elapsedMillis; // in millis

            try
            {
                if (waitTime > 0)
                {
                    // the CPU waits the appropriate time so that MAX_FPS are achieved
                    this.sleep(waitTime);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            // Debug Framecount
            totalTime += System.nanoTime() - previous;
            frameCount++;
            if (frameCount >= MAX_FPS)
            {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }

            try
            {
                // move to Constructor?
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    // the Game is updated including the canvas
                    this.game.update();
                    this.game.draw(canvas);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                if (canvas != null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }
}
