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

    private ArrayList<GameObject> gameObjectArray;
    private ArrayList<CollidableGameObject> collidableArray;
    private ArrayList<IDynamic> dynamicArray;

    private int waveFrameCount = 0;

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
    public void setBaseGyro(float baseGyro) {
        this.baseGyro = baseGyro;
    }
    private float baseGyro;
    boolean gyroChanged;

    public void updateGyroMovement(float gyroMovement) {
        this.gyroMovement = baseGyro - gyroMovement;
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

        gameObjectArray = new ArrayList<GameObject>();
        collidableArray = new ArrayList<CollidableGameObject>();
        dynamicArray = new ArrayList<IDynamic>();

        Random random = new Random();

        for(int i = 0; i < 100; i++)
        {
            int w = (int)((random.nextFloat() % 4 + 0.5) * 100);
            addRock(new Rock(random.nextInt(700),-200 * i,w,w));
        }

        player = new Player();
        dynamicArray.add(player);
        collidableArray.add(player);
        gameObjectArray.add(player);
    }

    private void addRock(Rock rock)
    {
        gameObjectArray.add(rock);
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
        waveFrameCount++;
        if(waveFrameCount > 3)
        {
            Wave wave = new Wave(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
            gameObjectArray.add(2, wave);
            dynamicArray.add(wave);
            waveFrameCount = 0;
        }

        for(int i = 0; i < dynamicArray.size(); i++) {
            IDynamic dgo = dynamicArray.get(i);
            dgo.update();
        }
        for(int i = 0; i < collidableArray.size(); i++)
        {
            CollidableGameObject cgo = collidableArray.get(i);
            for (int j = 0; j < gameObjectArray.size(); j++)
            {
                GameObject gameObject = gameObjectArray.get(j);
                if(gameObject.isSolid())
                {
                    if(cgo == gameObject)
                        continue;
                    cgo.checkCollision(gameObjectArray.get(j));
                }
            }
        }
        if(gyroChanged) {
            gyroChanged = false;
        }
        scrollY = player.getY() - SCREEN_HEIGHT + 500;
        clean();
    }

    private void clean() {
        for (int i = 0; i < gameObjectArray.size(); i++)
        {
            GameObject gameObject = gameObjectArray.get(i);
            if(!gameObject.isLiving())
            {
                gameObjectArray.remove(i);
                if(collidableArray.contains(gameObject))
                    collidableArray.remove(gameObject);
                if(dynamicArray.contains(gameObject))
                    dynamicArray.remove(gameObject);
            }
        }
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(69,160,204));

            for(int i = 0; i < gameObjectArray.size(); i++) {
                GameObject gameObject = gameObjectArray.get(i);
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