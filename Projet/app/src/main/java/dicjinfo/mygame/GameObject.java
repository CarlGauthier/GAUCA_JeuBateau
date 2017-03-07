package dicjinfo.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

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

    public boolean isSolid() { return solid; }

    public static ArrayList<GameObject> getGameObjectArray() {
        return gameObjectArray;
    }

    public static ArrayList<IDynamic> getDynamicArray() {
        return dynamicArray;
    }

    public static ArrayList<CollidableGameObject> getCollidableArray() {
        return collidableArray;
    }

    static protected ArrayList<GameObject> gameObjectArray;
    static protected ArrayList<CollidableGameObject> collidableArray;
    static protected ArrayList<IDynamic> dynamicArray;

    static {
        gameObjectArray = new ArrayList<GameObject>();
        collidableArray = new ArrayList<CollidableGameObject>();
        dynamicArray = new ArrayList<IDynamic>();
    }

    private int drawableId;

    protected int opacity;
    protected float x, y;
    protected float width, height;
    protected boolean solid;

    public GameObject(int drawableId, float x, float y, float width, float height, boolean solid) {

        this.drawableId = drawableId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        opacity = 255;
    }
}