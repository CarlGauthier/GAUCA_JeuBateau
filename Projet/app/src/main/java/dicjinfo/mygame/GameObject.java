package dicjinfo.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class GameObject {

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public Bitmap getSprite() {
        return sprite;
    }

    protected float x, y;
    protected float width, height;
    private Bitmap sprite;

    public GameObject(Context context, int id, float x, float y, float width, float height) {
        sprite = BitmapFactory.decodeResource(context.getResources(), id);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}