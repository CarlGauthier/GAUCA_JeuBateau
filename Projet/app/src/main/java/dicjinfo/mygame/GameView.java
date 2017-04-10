package dicjinfo.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

public class GameView extends SurfaceView implements Runnable {

    public void setBaseGyro(float baseGyro) {
        this.gyroBase = baseGyro;
    }

    public void updateGyroDifference(float gyroPosition) {

        this.gyroMovement = gyroBase - gyroPosition;
    }

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    private Player player;
    volatile boolean playing = true;
    private Thread gameThread = null;

    private LevelLoader levelLoader;

    //Display values
    private HashMap<Integer, Bitmap> spriteMap;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private float scrollY = 0;

    //Sensors values
    private float gyroMovement = 0;
    private float gyroBase;

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;

        spriteMap = new HashMap<Integer, Bitmap>();
        spriteMap.put(R.drawable.terry, BitmapFactory.decodeResource(context.getResources(), R.drawable.terry));
        spriteMap.put(R.drawable.rock, BitmapFactory.decodeResource(context.getResources(), R.drawable.rock));
        spriteMap.put(R.drawable.destroyablerock, BitmapFactory.decodeResource(context.getResources(), R.drawable.destroyablerock));
        spriteMap.put(R.drawable.wave, BitmapFactory.decodeResource(context.getResources(), R.drawable.wave));
        spriteMap.put(R.drawable.popcorn, BitmapFactory.decodeResource(context.getResources(), R.drawable.popcorn));
        spriteMap.put(R.drawable.octo, BitmapFactory.decodeResource(context.getResources(), R.drawable.octo));
        spriteMap.put(R.drawable.canonball, BitmapFactory.decodeResource(context.getResources(), R.drawable.canonball));
        spriteMap.put(R.drawable.coin, BitmapFactory.decodeResource(context.getResources(), R.drawable.coin));
        spriteMap.put(R.drawable.heart, BitmapFactory.decodeResource(context.getResources(), R.drawable.heart));
        spriteMap.put(R.drawable.explosion, BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion));

        //levelLoader = new LevelLoader(this.getContext(), gameObjectArray, collidableArray, dynamicArray);

        Random random = new Random();

        for(int i = 0; i < 10; i++) {
            Rock rock = new Rock(random.nextInt(1000),-200 * i + 100, 100, 100);
            GameObject.getGameObjectArray().add(rock);
        }
        for(int i = 0; i < 10; i++) {
            Heart heart = new Heart(random.nextInt(1000),-200 * i + 100);
            GameObject.getGameObjectArray().add(heart);
        }
        for(int i = 0; i < 10; i++) {
            DestroyableRock destroyableRock = new DestroyableRock(random.nextInt(1000),-200 * i - 2100, 100, 100);
            GameObject.getGameObjectArray().add(destroyableRock);
        }
        for(int i = 0; i < 10; i++) {
            Octo octo = new Octo(random.nextInt(1000),-200 * i - 4100);
            GameObject.getGameObjectArray().add(octo);
        }

        player = new Player();
        GameObject.getGameObjectArray().add(1,player);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            player.setScreenTouch(true);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        final long frameMs = 1000 / 60;
        while (playing) {
            long startMs, endMs, delayMs;
            startMs = System.currentTimeMillis();
            update();
            draw();
            endMs = System.currentTimeMillis();
            delayMs = frameMs - ( endMs - startMs);
            if(delayMs < 0)
                delayMs = 0;
            try {
                gameThread.sleep(delayMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {

        player.setGyroMovement(gyroMovement);

        //Update all IDynamics
        for(int i = 0; i < GameObject.getGameObjectArray().size(); i++) {
            GameObject go = GameObject.getGameObjectArray().get(i);
            go.update();
        }
        scrollY = player.getY() - SCREEN_HEIGHT + 500;
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(69,160,204));

            for(int i = 0; i < GameObject.getGameObjectArray().size(); i++) {
                GameObject gameObject = GameObject.getGameObjectArray().get(i);
                Bitmap sprite = spriteMap.get(gameObject.getDrawableId());
                Paint paint = new Paint();
                paint.setAlpha(gameObject.getOpacity());
                canvas.drawBitmap(
                    sprite,
                    new Rect(0,0,sprite.getWidth(),sprite.getHeight()),
                    new Rect(
                        (int)gameObject.getX(),
                        (int)(gameObject.getY() - scrollY),
                        (int)(gameObject.getWidth() + gameObject.getX()),
                        (int)(gameObject.getHeight() + gameObject.getY() - scrollY)
                    ),
                    paint
                );
                /*
                paint.setARGB(255,255,255,255);
                if(gameObject instanceof CollidableGameObject) {
                    CollidableGameObject collidableGameObject = (CollidableGameObject)gameObject;
                    canvas.drawRect(
                            (int)collidableGameObject.collider.getX(),
                            (int)collidableGameObject.collider.getY() - scrollY,
                            (int)collidableGameObject.collider.getX() + collidableGameObject.collider.getWidth(),
                            (int)collidableGameObject.collider.getY() + collidableGameObject.collider.getHeight() - scrollY,
                            paint
                    );
                }
                */
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}