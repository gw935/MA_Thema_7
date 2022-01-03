package de.fhswf.ma.thema7.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import de.fhswf.ma.thema7.R;
import de.fhswf.ma.thema7.game.gameobjects.Goal;
import de.fhswf.ma.thema7.game.gameobjects.Player;
import de.fhswf.ma.thema7.util.Constants;
import de.fhswf.ma.thema7.util.OrientationData;

public class Game extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread thread;

    // TODO: auslagern in eigene Szene
    private Player player;
    private Point playerPosition;
    private boolean gameOver = false;
    private OrientationData orientationData;
    private long frameTime;

    private Goal goal;
    private Point goalPosition;

    public Game(Context context)
    {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        // TODO: auslagern in eigene Szene
        // Player is added
        BitmapFactory factory = new BitmapFactory();
        player = new Player(
                factory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player),
                new RectF(
                        Constants.SCREEN_WIDTH / 2 - 64,
                        Constants.SCREEN_HEIGHT / 2 - 64,
                        Constants.SCREEN_WIDTH / 2 + 64,
                        Constants.SCREEN_HEIGHT / 2 + 64
                )
        );
        playerPosition = new Point(Constants.SCREEN_WIDTH / 2 - 64, 3 * Constants.SCREEN_HEIGHT / 4 - 64);
        player.update(playerPosition);

        goal = new Goal(factory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.game_goal));

        // Sensors
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();

        System.out.println("Game startet");

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
        if (!gameOver)
        {
            // Constants.INIT_TIME is set in surfaceCreated and used for resuming the game
            if (frameTime < Constants.INIT_TIME)
            {
                frameTime = Constants.INIT_TIME;
            }
            // elapsedTime is used for correctly calculating the position regardless of framerate
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if (orientationData.getOutput() != null)
            {
                // pitch & roll are values between -1 and 1
                float pitch = orientationData.getOutput()[0];
                float roll = orientationData.getOutput()[1];

                // TODO: temp anpassen fuer geschwindigkeiten, Sensor infos anschauen fuer besseres verstaendnis
                // edit: fürs erste in ordnung
                int temp = 200;
                float xSpeed = roll * Constants.SCREEN_WIDTH / temp;
                float ySpeed = pitch * Constants.SCREEN_WIDTH / temp;

                playerPosition.x += xSpeed * elapsedTime;
                playerPosition.y += ySpeed * elapsedTime;
            }

            // TODO: Wenn Rand beruehrt gameover?
            if (playerPosition.x < 64)
            {
                playerPosition.x = 64;
            } else if (playerPosition.x > Constants.SCREEN_WIDTH - 64)
            {
                playerPosition.x = Constants.SCREEN_WIDTH - 64;
            }

            // später drauf achten bei älteren geräten bzw. < 2340x1080
            if (playerPosition.y < 64)
            {
                playerPosition.y = 64;
            } else if (playerPosition.y > Constants.SCREEN_HEIGHT - 64)
            {
                playerPosition.y = Constants.SCREEN_HEIGHT - 64;
            }

            player.update(playerPosition);
            if(goal.playerCollide(player))
            {
                System.out.println("You Won!!!!!!!!");
            }
        }
    }

    /**
     * Game is rendered
     *
     * @param canvas the canvas on which everything is drawn on
     */
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        goal.draw(canvas);
    }
}
