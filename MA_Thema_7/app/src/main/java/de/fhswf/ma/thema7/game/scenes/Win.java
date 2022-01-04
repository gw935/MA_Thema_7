package de.fhswf.ma.thema7.game.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Win implements Scene
{
    private SceneManager sceneManager;

    public Win(SceneManager sceneManager)
    {
        this.sceneManager = sceneManager;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint(Color.BLACK);
        String text = "You Won";
        Rect r = new Rect();

        String text1 = "tap to go back to Main Menu";
        Rect r1 = new Rect();

        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

        paint.setTextSize(50);
        canvas.getClipBounds(r1);
        int cHeight1 = r1.height();
        int cWidth1 = r1.width();
        paint.getTextBounds(text1, 0, text1.length(), r1);
        float x1 = cWidth1 / 2f - r1.width() / 2f - r1.left;
        float y1 = cHeight1 / 2f + r1.height() / 2f - r1.bottom + 100;
        canvas.drawText(text1, x1, y1, paint);
    }

    @Override
    public void recieveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            // starts game if screen is touched
            case MotionEvent.ACTION_DOWN:
            {
                sceneManager.goToMainmenu();
                break;
            }
        }
    }
}
