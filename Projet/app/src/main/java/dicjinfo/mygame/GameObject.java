package dicjinfo.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

public abstract class GameObject {

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

    public static ArrayList<GameObject> getGameObjectArray() {
        return gameObjectArray;
    }

    static protected ArrayList<GameObject> gameObjectArray;
    static protected SoundPool soundPool;

    static {
        gameObjectArray = new ArrayList<GameObject>();
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }

    protected int drawableId;
    protected int opacity;
    protected float x, y;
    protected float width, height;

    public GameObject(int drawableId, float x, float y, float width, float height) {

        this.drawableId = drawableId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        opacity = 255;
    }

    protected void stretchX(float value) {
        width += value;
        x -= value / 2;
    }

    protected void stretchY(float value) {
        height += value;
        y -= value / 2;
    }

    public void update() {
        action();
    }

    public void action() {

    }
}