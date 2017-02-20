package dicjinfo.mygame;

import android.content.Context;
import android.graphics.Bitmap;
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
import java.util.Random;
import java.util.logging.Level;

public class GameView extends SurfaceView implements Runnable {

    int framePassed = 0;

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    private ArrayList<GameObject> gameObjectArray;
    private ArrayList<CollidableGameObject> collidableArray;
    private ArrayList<DynamicGameObject> dynamicArray;

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

        levelLoader = new LevelLoader(this.getContext(), gameObjectArray, collidableArray, dynamicArray);

        gameObjectArray = new ArrayList<GameObject>();
        collidableArray = new ArrayList<CollidableGameObject>();
        dynamicArray = new ArrayList<DynamicGameObject>();

        player = new Player(context);
        dynamicArray.add(player);
        gameObjectArray.add(player);

        Random random = new Random();

        for(int i = 0; i < 100; i++)
        {
            int w = (int)((random.nextFloat() % 2 + 0.5) * 100);
            addRock(new Rock(context,random.nextInt(700),-200 * i,w,w));
        }
    }

    private void addRock(Rock rock)
    {
        collidableArray.add(rock);
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

        for(int i = 0; i < dynamicArray.size(); i++) {
            DynamicGameObject dgo = dynamicArray.get(i);
            dgo.update(gyroMovement);
            for (int j = 0; j < collidableArray.size(); j++)
            {
                CollidableGameObject cgo = collidableArray.get(j);
                dgo.checkCollision(cgo);
            }
        }
        if(gyroChanged) {
            gyroChanged = false;
        }

        scrollY = player.getY() - SCREEN_HEIGHT + 500;
        framePassed++;
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.rgb(69,160,204));

            for(int i = 0; i < gameObjectArray.size(); i++) {
                GameObject gameObject = gameObjectArray.get(i);
                Bitmap sprite = gameObject.getSprite();
                canvas.drawBitmap(
                    sprite,
                    new Rect(0,0,sprite.getWidth(),sprite.getHeight()),
                    new Rect(
                        (int)gameObject.getX(),
                        (int)(gameObject.getY() - scrollY),
                        (int)(gameObject.getWidth() + gameObject.getX()),
                        (int)(gameObject.getHeight() + gameObject.getY() - scrollY)
                    ),
                    new Paint()
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