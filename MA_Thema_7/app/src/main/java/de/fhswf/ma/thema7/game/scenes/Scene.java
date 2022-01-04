package de.fhswf.ma.thema7.game.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene
{
    public void update();
    public void draw(Canvas canvas);
    public void recieveTouch(MotionEvent event);
}
