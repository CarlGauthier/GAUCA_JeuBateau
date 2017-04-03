package dicjinfo.mygame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Player extends CollidableGameObject {

    public void setGyroMovement(float gyroMovement) {
        this.gyroMovement = gyroMovement;
    }

    public void setScreenTouch(boolean screenTouch) {
        this.screenTouch = screenTouch;
    }

    //Sensor values
    float gyroMovement;
    boolean screenTouch;

    float velX, velY;
    int animationFrame = 0;
    private int waveFrameCount = 0;
    boolean invincible = false;

    SoundPool soundPool;
    int canonblast;

    int health = 0;

    public Player() {
        super(R.drawable.terry, 400, 0, 125, 222, 125, 260, true);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        canonblast = soundPool.load(GameActivity.getAppContext(), R.raw.weber, 1);
    }

    @Override
    public void update() {

        move();
        checkBound();
        if(screenTouch) {
            shoot();
            screenTouch = false;
        }
        if(invincible)
            invincibility();
        else
            waveEmission();
    }

    private void invincibility() {

        animationFrame++;
        if(animationFrame % 5 == 0)
        {
            if(animationFrame % 10 == 0)
                opacity = 255;
            else
                opacity = 0;
        }
        if(animationFrame > 100)
        {
            invincible = false;
            opacity = 255;
            animationFrame = 0;
        }
    }

    private void move() {
        if(velY > -10)
            velY-= 1;
        velX *= 0.95;
        velY *= 0.95;
        checkVelLimits();
        x += velX + gyroMovement;
        y += velY;
    }

    private void checkVelLimits() {

        if(velX > 20)
            velX = 20;
        else if(velX < -20)
            velX = -20;
        if(velY > 20)
            velY = 20;
        else if(velY < -20)
            velY = -20;
    }

    private void checkBound() {
        if(x > 1080 - width)
            x = 1080 - width;
        if(x < 0)
            x = 0;
    }

    private void waveEmission() {
        waveFrameCount++;
        if(waveFrameCount > 6) {
            Wave wave = new Wave(x + rWidth / 2, y + rHeight / 2);
            gameObjectArray.add(gameObjectArray.indexOf(this), wave);
            dynamicArray.add(wave);
            waveFrameCount = 0;
        }
    }

    private void shoot() {
        soundPool.play(canonblast,1,1,0,0,0);
        Canonball canonball = new Canonball(x + 12.5f, y);
        gameObjectArray.add(canonball);
        dynamicArray.add(canonball);
    }

    @Override
    public void onCollision(GameObject gameObject)
    {
        if(gameObject.isSolid())
        {
            int dx = (int)((width / 2 + x) - (gameObject.getWidth() / 2 + gameObject.getX()));
            int dy = (int)((height / 2 + y) - (gameObject.getHeight() / 2 + gameObject.getY()));
            velX += dx / 2;
            velY += dy / 2;
            invincible = true;
            opacity = 0;
            health--;

            gameObjectArray.remove(gameObject);
            if(gameObject instanceof IDynamic)
                dynamicArray.remove(gameObject);
        }
    }
}