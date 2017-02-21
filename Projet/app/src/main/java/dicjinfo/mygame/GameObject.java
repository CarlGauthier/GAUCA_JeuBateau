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

    public int getDrawableId() { return drawableId; }

    public int getOpacity() {
        return opacity;
    }

    public boolean isLiving() {
        return living;
    }

    public boolean isSolid() { return solid; }

    private int drawableId;

    protected int opacity;
    protected float x, y;
    protected float width, height;
    protected boolean solid;

    protected boolean living;

    public GameObject(int drawableId, float x, float y, float width, float height, boolean solid) {

        this.drawableId = drawableId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        living = true;
        opacity = 255;
    }
}