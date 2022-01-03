package de.fhswf.ma.thema7.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Goal implements GameObject
{
    private Bitmap goalImage;
    private RectF dst;
    private Point[] corners;
    private Point position;

    public Goal(Bitmap goalImage, RectF dst)
    {
        this.goalImage = goalImage;
        this.dst = dst;
        this.corners = new Point[3];
        this.position = new Point();
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

    public void update(Point position)
    {
        // updates position of the destination Rectangle
        dst.set(
                // l,t,r,b
                position.x - dst.width() / 2,
                position.y - dst.height() / 2,
                position.x + dst.width() / 2,
                position.y + dst.height() / 2
        );
        this.position = position;
        // Positions of the 3 Corners of the Triangle.
        corners[0] = new Point(position.x - 64, position.y - 56);
        corners[1] = new Point(position.x + 64, position.y - 56);
        corners[2] = new Point(position.x, position.y + 56);
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

