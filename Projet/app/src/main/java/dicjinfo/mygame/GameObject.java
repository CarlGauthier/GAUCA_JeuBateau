package dicjinfo.mygame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.Queue;

import org.simpleframework.xml.Element;

@Element
public abstract class GameObject {

    static private ArrayList<GameObject> emitQueue;
    static private ArrayList<GameObject> destroyQueue;
    static protected SoundPool soundPool;
    static {
        emitQueue = new ArrayList<GameObject>();
        destroyQueue = new ArrayList<GameObject>();
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }
    protected int drawableId;
    protected int opacity;
    @Element
    protected float x, y;
    protected float width, height;
    protected int zIndex;

    public static ArrayList<GameObject> getDestroyQueue() {
        return destroyQueue;
    }

    public static SoundPool getSoundPool() {
        return soundPool;
    }

    public static ArrayList<GameObject> getEmitQueue() {
        return emitQueue;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public int getDrawableId() { return drawableId; }

    public int getzIndex() {
        return zIndex;
    }

    public int getOpacity() {
        return opacity;
    }

    public GameObject(){
        opacity = 255;
    }

    public GameObject(int drawableId, float x, float y, float width, float height, int zIndex) {

        this.drawableId = drawableId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.zIndex = zIndex;
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

    public void setY(float y) {
        this.y = y;
    }

    protected void emit(GameObject go) {
        emitQueue.add(go);
    }

    protected void destroy(GameObject go) {
        destroyQueue.add(go);
    }

    public void update(ArrayList<GameObject> gameObjects) {
        action();
    }

    public void action() {

    }
}