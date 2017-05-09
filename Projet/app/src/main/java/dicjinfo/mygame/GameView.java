package dicjinfo.mygame;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
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

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.InputStream;
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

    volatile boolean playing = true;
    private Thread gameThread = null;

    private HashMap<Integer, Bitmap> spriteMap;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private float gyroMovement = 0;
    private float gyroBase;

    private ArrayList<HUDElement> hudElements;
    private Environment environment;
    private Camera camera;

    public GameView(Context context, int screenWidth, int screenHeight) {

        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

        environment = new Environment();
        camera = new Camera(screenWidth, screenHeight, environment.getPlayer());

        initSpriteMap(context);
        initHUD();
    }

    private void initSpriteMap(Context context) {
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
        spriteMap.put(R.drawable.ammunition, BitmapFactory.decodeResource(context.getResources(), R.drawable.ammunition));
        spriteMap.put(R.drawable.explosion, BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion));
        spriteMap.put(R.drawable.fullheart, BitmapFactory.decodeResource(context.getResources(), R.drawable.fullheart));
        spriteMap.put(R.drawable.emptyheart, BitmapFactory.decodeResource(context.getResources(), R.drawable.emptyheart));
        spriteMap.put(R.drawable.ammunitionicon, BitmapFactory.decodeResource(context.getResources(), R.drawable.ammunitionicon));
        spriteMap.put(R.drawable.ammunitioniconempty, BitmapFactory.decodeResource(context.getResources(), R.drawable.ammunitioniconempty));
    }

    private void initHUD() {
        hudElements = new ArrayList<HUDElement>();
        HUDHeart h1 = new HUDHeart(1, 10, 10, environment.getPlayer());
        hudElements.add(h1);
        HUDHeart h2 = new HUDHeart(2, 120, 10, environment.getPlayer());
        hudElements.add(h2);
        HUDHeart h3 = new HUDHeart(3, 230, 10, environment.getPlayer());
        hudElements.add(h3);
        HUDAmmunition a1 = new HUDAmmunition(1, 970, 10, environment.getPlayer());
        hudElements.add(a1);
        HUDAmmunition a2 = new HUDAmmunition(2, 860, 10, environment.getPlayer());
        hudElements.add(a2);
        HUDAmmunition a3 = new HUDAmmunition(3, 750, 10, environment.getPlayer());
        hudElements.add(a3);
        HUDScore s = new HUDScore(environment.getPlayer());
        hudElements.add(s);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            environment.getPlayer().setScreenTouch(true);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        final long frameMillis = 1000 / 60;
        while (playing) {
            long startMillis, delayMillis;
            startMillis = System.currentTimeMillis();
            update();
            render();
            delayMillis = frameMillis - ( System.currentTimeMillis() - startMillis);
            if(delayMillis < 0)
                continue;
            try {
                gameThread.sleep(delayMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {


        Player player = environment.getPlayer();
        environment.update(GameObject.getEmitQueue(), GameObject.getDestroyQueue());
        player.setGyroMovement(gyroMovement);
        if(!player.isAlive())
            end();
        for(GameObject go : environment.getGameObjects()) {
            go.update(environment.getGameObjects());
        }
        camera.update();
        for(HUDElement he : hudElements) {
            he.update();
        }
    }

    private void render() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(69,160,204)); //Clear
            for(GameObject go : environment.getGameObjects())
                draw(go);
            for(HUDElement he : hudElements) {
                if(he instanceof HUDText) {
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(72);
                    canvas.drawText(((HUDText) he).text, he.getX(), he.getY(), paint);
                } else drawFixed(he);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void draw(GameObject go) {

        Bitmap sprite = spriteMap.get(go.getDrawableId());
        Paint paint = new Paint();
        paint.setAlpha(go.getOpacity());
        canvas.drawBitmap(
            sprite,
            new Rect(0, 0, sprite.getWidth(), sprite.getHeight()),
            new Rect(
                (int)(go.getX() * camera.getZoomX()),
                (int)((go.getY() - camera.getY()) * camera.getZoomY()),
                (int)((go.getWidth() + go.getX()) * camera.getZoomX()),
                (int)((go.getHeight() + go.getY() - camera.getY()) * camera.getZoomY())
            ),
            paint
        );
        /* //DEBUG : Show all colliders
        paint.setARGB(255,255,255,255);
        if(go instanceof CollidableGameObject) {
            CollidableGameObject collidableGameObject = (CollidableGameObject)go;
            canvas.drawRect(
                (int)(collidableGameObject.collider.getX() * camera.getZoomX()),
                (int)((collidableGameObject.collider.getY() - camera.getY()) * camera.getZoomY()),
                (int)((collidableGameObject.collider.getX() + collidableGameObject.collider.getWidth()) * camera.getZoomX()),
                (int)((collidableGameObject.collider.getY() + collidableGameObject.collider.getHeight() - camera.getY())) * camera.getZoomY(),
                paint
            );
        }
        */
    }

    private void drawFixed(HUDElement he) {

        Bitmap sprite = spriteMap.get(he.getDrawableId());
        Paint paint = new Paint();
        canvas.drawBitmap(
            sprite,
            new Rect(0, 0, sprite.getWidth(), sprite.getHeight()),
            new Rect(
                (int)(he.getX()),
                (int)(he.getY()),
                (int)(he.getX() + he.getWidth()),
                (int)(he.getY() + he.getHeight())
            ),
            paint
        );
    }

    private void end() {
        ((Activity) getContext()).finish();
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