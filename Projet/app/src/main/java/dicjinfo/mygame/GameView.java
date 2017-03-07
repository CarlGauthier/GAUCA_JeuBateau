package dicjinfo.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
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

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    private HashMap<Integer, Bitmap> spriteMap;

    private Player player;

    volatile boolean playing = true;
    private Thread gameThread = null;

    private LevelLoader levelLoader;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private float scrollY = 0;

    private float gyroMovement = 0;
    private float gyroBase;
    public void setBaseGyro(float baseGyro) {
        this.gyroBase = baseGyro;
    }

    public void updateGyroDifference(float gyroPosition) {

        this.gyroMovement = gyroBase - gyroPosition;
    }

    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;

        spriteMap = new HashMap<Integer, Bitmap>();
        spriteMap.put(R.drawable.terry, BitmapFactory.decodeResource(context.getResources(), R.drawable.terry));
        spriteMap.put(R.drawable.rock, BitmapFactory.decodeResource(context.getResources(), R.drawable.rock));
        spriteMap.put(R.drawable.wave, BitmapFactory.decodeResource(context.getResources(), R.drawable.wave));

        //levelLoader = new LevelLoader(this.getContext(), gameObjectArray, collidableArray, dynamicArray);

        Random random = new Random();

        for(int i = 0; i < 100; i++) {
            int w = (int)((random.nextFloat() % 4 + 0.5) * 100);
            Rock rock = new Rock(random.nextInt(1000),-200 * i,w,w);
            GameObject.getGameObjectArray().add(rock);
        }

        player = new Player();
        GameObject.getGameObjectArray().add(player);
        GameObject.getDynamicArray().add(player);
    }

    @Override
    public void run() {

        while (playing) {
            update();
            draw();
            try {
                gameThread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {

        player.setGyroMovement(gyroMovement);

        for(int i = 0; i < GameObject.getDynamicArray().size(); i++) {
            IDynamic dgo = GameObject.getDynamicArray().get(i);
            dgo.update();
        }
        for(int i = 0; i < GameObject.getCollidableArray().size(); i++)
        {
            CollidableGameObject cgo = GameObject.getCollidableArray().get(i);
            for (int j = 0; j < GameObject.getGameObjectArray().size(); j++)
            {
                GameObject gameObject = GameObject.getGameObjectArray().get(j);
                    if(cgo == gameObject)
                        continue;
                    cgo.checkCollision(GameObject.getGameObjectArray().get(j));

            }
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