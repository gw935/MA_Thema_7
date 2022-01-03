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
                position.x - 128 / 2,
                position.y - 112 / 2,
                position.x + 128 / 2,
                position.y + 112 / 2);
        this.corners = new Point[3];
        // Positions of the 3 Corners of the Triangle.
        corners[0] = new Point(position.x - 64, position.y - 56);
        corners[1] = new Point(position.x + 64, position.y - 56);
        corners[2] = new Point(position.x, position.y + 56);
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
        int x = rand.nextInt(Constants.SCREEN_WIDTH - 128) + 64;
        int y = 56;
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
        for (int i = 0; i < corners.length; i++)
        {
            double distance = Math.sqrt(Math.pow(corners[i].x - player.getPosition().x, 2) + Math.pow(corners[i].y - player.getPosition().y, 2));
            if (distance <= player.getRadius())
            {
                return true;
            } else
            {
                return false;
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
