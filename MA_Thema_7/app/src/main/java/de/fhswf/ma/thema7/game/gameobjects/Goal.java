package de.fhswf.ma.thema7.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.Random;

import de.fhswf.ma.thema7.util.Constants;

public class Goal implements GameObject
{
    private Bitmap goalImage;
    private RectF dst;
    private Point[] corners;
    private Point position;

    public Goal(Bitmap goalImage)
    {
        this.goalImage = goalImage;
        this.position = randPosition();
        this.dst = new RectF(
                position.x - 128 / 2 * Constants.SCALE,
                position.y - 112 / 2 * Constants.SCALE,
                position.x + 128 / 2 * Constants.SCALE,
                position.y + 112 / 2 * Constants.SCALE);
        this.corners = new Point[3];
        // Positions of the 3 Corners of the Triangle.
        corners[0] = new Point(position.x - (64 * Constants.SCALE), position.y + (56 * Constants.SCALE));
        corners[1] = new Point(position.x + (64 * Constants.SCALE), position.y + (56 * Constants.SCALE));
        corners[2] = new Point(position.x, position.y - (56 * Constants.SCALE));
        /*
        System.out.println("Positon = " + position.x + " , " + position.y);
        System.out.println("Corner 0 = " + corners[0].x + " , " + corners[0].y);
        System.out.println("Corner 1 = " + corners[1].x + " , " + corners[1].y);
        System.out.println("Corner 2 = " + corners[2].x + " , " + corners[2].y);
        */
        System.out.println("Goal was created.");
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(goalImage, null, dst, new Paint());
    }

    @Override
    public void update()
    {

    }

    /**
     * Calculates a random position.
     *
     * @return A point with x = between 64 and (Constants.SCREEN_WIDTH - 64), y = 56.
     */
    private Point randPosition()
    {
        Random rand = new Random(System.currentTimeMillis());
        int x = rand.nextInt(Constants.SCREEN_WIDTH - (128 * Constants.SCALE)) + (64 * Constants.SCALE);
        int y = 56 * Constants.SCALE;
        return new Point(x, y);
    }

    /**
     * Checks if the player collides with the goal.
     *
     * @param player the player.
     * @return true if player collides.
     */
    public boolean playerCollide(Player player)
    {
        if(player.getPosition().x > corners[0].x && player.getPosition().x < corners[1].x && player.getPosition().y - player.getRadius() <= corners[0].y)
        {
            return true;
        }
        for (int i = 0; i < corners.length; i++)
        {
            double distance = Math.sqrt(Math.pow((corners[i].x - player.getPosition().x), 2) + Math.pow((corners[i].y - player.getPosition().y), 2));
            //System.out.println("Distance = " + distance);
            if (distance <= player.getRadius())
            {
                return true;
            }
        }
        return false;
    }

    public RectF getDst()
    {
        return dst;
    }

    public void setDst(RectF dst)
    {
        this.dst = dst;
    }
}

