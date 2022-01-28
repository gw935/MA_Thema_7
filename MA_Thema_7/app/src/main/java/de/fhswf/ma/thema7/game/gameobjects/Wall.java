package de.fhswf.ma.thema7.game.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import de.fhswf.ma.thema7.util.Constants;

public class Wall implements GameObject
{
    private Bitmap wallImage;
    private RectF dst1;
    private RectF dst2;
    private int wallWidth = (int) (1080 * Constants.SCALE);
    private int wallHeight = (int) (128 * Constants.SCALE);

    /**
     * @param wallImage Image used for the walls
     * @param x         Right x coordinate of the left wall
     * @param y         Top y coordinate of the walls
     * @param gapSize   The gap size between the walls
     */
    public Wall(Bitmap wallImage, int x, int y, int gapSize)
    {
        this.wallImage = wallImage;
        this.dst1 = new RectF(
                x - wallWidth,
                y,
                x,
                y + wallHeight
        );
        this.dst2 = new RectF(
                x + gapSize,
                y,
                x + gapSize + wallWidth,
                y + wallHeight
        );
    }

    /**
     * Check if player collides with any wall
     *
     * @param player Is the player
     * @return returns true if collision is detected
     */
    public boolean playerCollide(Player player)
    {
        if (collideWithWall(dst1, player) || collideWithWall(dst2, player))
        {
            //System.out.println("KOLLIDIERT");
            return true;
        }
        return false;
    }

    /**
     * Checks if a given wall collides with the player
     *
     * @param wall   The wall
     * @param player The player
     * @return return true if collision is detected
     */
    private boolean collideWithWall(RectF wall, Player player)
    {
        // Distance between the center of the wall and position of the player
        float xDistance = Math.abs(wall.centerX() - player.getPosition().x);
        float yDistance = Math.abs(wall.centerY() - player.getPosition().y);

        // half of the width/height of the wall
        float halfWidth = wall.width() / 2;
        float halfHeight = wall.height() / 2;

        // left & right because of Math.abs
        // return false if xDistance is higher than the width to the center of the wall + the player radius
        if (xDistance > halfWidth + player.getRadius())
        {
            return false;
        }

        // top & bottom because of Math.abs
        // return false if yDistance is higher than the height to the center of the wall + the player radius
        if (yDistance > halfHeight + player.getRadius())
        {
            return false;
        }

        // left & right because of Math.abs
        // return true if the distance between wall center & player is lower than distance between wall center & wall l/r edge
        if (xDistance <= halfWidth)
        {
            return true;
        }

        // top & bottom because of Math.abs
        // return true if the distance between wall center & player is lower than distance between wall center & wall t/b edge
        if (yDistance <= halfHeight)
        {
            return true;
        }

        // distance to player - half the wall width/height
        float xCornerDistance = xDistance - halfWidth;
        float yCornerDistance = yDistance - halfHeight;

        // abberation of distance between two points
        double powCornerDistance = Math.pow(xCornerDistance, 2) + Math.pow(yCornerDistance, 2);

        // checks if above abberation is smaller or equal to the radius squared and returns true if that is the case
        return powCornerDistance <= Math.pow(player.getRadius(), 2);

    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        canvas.drawBitmap(wallImage, null, dst1, paint);
        canvas.drawBitmap(wallImage, null, dst2, paint);
    }

    @Override
    public void update()
    {

    }
}
