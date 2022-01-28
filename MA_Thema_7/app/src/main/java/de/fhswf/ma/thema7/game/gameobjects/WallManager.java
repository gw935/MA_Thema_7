package de.fhswf.ma.thema7.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

import de.fhswf.ma.thema7.util.Constants;

public class WallManager implements GameObject
{
    private Wall[] walls;
    private int gap;
    private Bitmap wallImage;

    private int height = (int) (128 * Constants.SCALE);

    public WallManager(int wallAmount, int gap, Bitmap wallImage)
    {
        int amount = wallAmount;
        if (amount <= 0)
        {
            amount = 1;
        }
        if (amount > 3)
        {
            amount = 3;
        }
        this.walls = new Wall[amount];
        this.gap = gap;
        this.wallImage = wallImage;

        System.out.println("Wallamount: " + wallAmount);
        System.out.println("ArrayLength: " + walls.length);

        generateWalls();
    }

    private void generateWalls()
    {
        int temp = Constants.SCREEN_HEIGHT / (walls.length + 1);
        int currentWallPosition = (int) (temp - height);

        Random rand = new Random(System.currentTimeMillis());
        System.out.println("ScreenHeight = " + Constants.SCREEN_HEIGHT);

        for (int i = 0; i < walls.length; i++)
        {
            System.out.println("Wall generated with y = " + currentWallPosition);
            walls[i] = new Wall(wallImage, rand.nextInt(Constants.SCREEN_WIDTH - gap), currentWallPosition, gap);

            currentWallPosition += temp;
        }
    }

    public boolean playerCollide(Player player)
    {
        for (int i = 0; i < walls.length; i++)
        {
            if(walls[i].playerCollide(player))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)
    {
        for (int i = 0; i < walls.length; i++)
        {
            walls[i].draw(canvas);
        }
    }

    @Override
    public void update()
    {
        for (int i = 0; i < walls.length; i++)
        {
            walls[i].update();
        }
    }
}
