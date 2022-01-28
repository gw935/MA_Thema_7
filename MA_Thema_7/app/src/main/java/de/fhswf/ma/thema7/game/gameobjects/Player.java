package de.fhswf.ma.thema7.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import de.fhswf.ma.thema7.util.Constants;

public class Player implements GameObject
{
    private Bitmap playerImage;
    private RectF dst;
    private Point position;
    private int radius = (int) (64 * Constants.SCALE);

    public Player(Bitmap playerImage, RectF dst)
    {
        this.playerImage = playerImage;
        this.dst = dst;

        System.out.println("Player was created.");
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(playerImage, null, dst, new Paint());
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
    }

    public RectF getDst()
    {
        return dst;
    }

    public void setDst(RectF dst)
    {
        this.dst = dst;
    }

    public Point getPosition()
    {
        return position;
    }

    public int getRadius()
    {
        return radius;
    }
}
