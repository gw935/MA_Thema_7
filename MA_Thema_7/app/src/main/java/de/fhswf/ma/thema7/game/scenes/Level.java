package de.fhswf.ma.thema7.game.scenes;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;

import de.fhswf.ma.thema7.R;
import de.fhswf.ma.thema7.game.gameobjects.Goal;
import de.fhswf.ma.thema7.game.gameobjects.Player;
import de.fhswf.ma.thema7.game.gameobjects.WallManager;
import de.fhswf.ma.thema7.util.Constants;
import de.fhswf.ma.thema7.util.OrientationData;

public class Level implements Scene
{
    private SceneManager sceneManager;

    private Player player;
    private Point playerPosition;
    private boolean gameOver = false;
    private boolean started = false;
    private OrientationData orientationData;
    private long frameTime;

    private Goal goal;

    private int wallAmount;
    private WallManager wallManager;

    public Level(SceneManager sceneManager, int wallAmount)
    {
        this.sceneManager = sceneManager;

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
        playerPosition = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 128);
        player.update(playerPosition);

        goal = new Goal(factory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.game_goal));

        // Walls
        this.wallAmount = wallAmount;
        wallManager = new WallManager(wallAmount, 250, factory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.wall));

        // Sensors
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();

        System.out.println("Game startet");
    }

    /**
     *
     * is executed when the player collides with a wall or the bounds and resets player position
     *
     */
    private void restart()
    {
        gameOver = false;
        started = false;
        playerPosition = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 128);
        player.update(playerPosition);

    }

    /**
     * Checks if player collides with bounds.
     */
    private void collide()
    {
        // TODO: if lose change to extra scene and than to main menu
        // Screenbounds
        if (playerPosition.x < 64)
        {
            restart();
            return;
        } else if (playerPosition.x > Constants.SCREEN_WIDTH - 64)
        {
            restart();
            return;
        }

        if (playerPosition.y < 64)
        {
            restart();
            return;
        } else if (playerPosition.y > Constants.SCREEN_HEIGHT - 64)
        {
            restart();
            return;
        }

        // goal
        if (goal.playerCollide(player))
        {
            System.out.println("You Won!!!!!!!!");
            gameOver = true;
            sceneManager.nextScene();
            return;
        }

        // Walls
        if (wallManager.playerCollide(player))
        {
            System.out.println("The Player collided with a wall!!!");
            restart();
            return;
        }
    }

    /**
     * Gamelogic is updated
     */
    @Override
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


            if (started)
            {
                if (orientationData.getOutput() != null)
                {
                    // pitch & roll are values between -1 and 1
                    float pitch = orientationData.getOutput()[0];
                    float roll = orientationData.getOutput()[1];

                    // TODO: temp anpassen fuer geschwindigkeiten, Sensor infos anschauen fuer besseres verstaendnis
                    // edit: fuers erste in ordnung
                    int temp = 200;
                    float xSpeed = roll * Constants.SCREEN_WIDTH / temp;
                    float ySpeed = pitch * Constants.SCREEN_WIDTH / temp;

                    playerPosition.x += xSpeed * elapsedTime;
                    playerPosition.y += ySpeed * elapsedTime;
                }
                player.update(playerPosition);
                wallManager.update();
                collide();
            }
        }
        // show you lost | you won
    }

    @Override
    public void draw(Canvas canvas)
    {
        player.draw(canvas);
        goal.draw(canvas);
        wallManager.draw(canvas);
    }

    @Override
    public void recieveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            // starts game if screen is touched
            case MotionEvent.ACTION_DOWN:
            {
                if (!gameOver)
                {
                    started = true;
                }
                break;
            }
        }
    }
}
