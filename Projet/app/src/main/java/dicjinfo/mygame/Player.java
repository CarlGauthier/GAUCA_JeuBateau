package dicjinfo.mygame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Player extends CollidableGameObject {

    static int canonblast;
    static int boathit;

    static {
        canonblast = soundPool.load(GameActivity.getAppContext(), R.raw.canonblast, 1);
        boathit = soundPool.load(GameActivity.getAppContext(), R.raw.boathit, 1);
    }

    private float gyroMovement;
    private boolean screenTouch;
    private float velX, velY;
    private int animationFrame = 0;
    protected int frameCount = 0;
    private boolean invincible = false;
    private boolean alive = true;
    private int health;
    private int ammunition;
    private int score = 0;

    public void setGyroMovement(float gyroMovement) {
        this.gyroMovement = gyroMovement;
    }

    public void setScreenTouch(boolean screenTouch) {
        this.screenTouch = screenTouch;
    }

    public int getHealth() {
        return health;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getScore() {
        return score;
    }

    public void increaseHealth() {

        if(health != 3)
            health++;
        else {
            score += 100;
        }
    }

    public Player() {
        super(R.drawable.terry, 400, 0, 125, 260, 3);
        collider = new Collider(125, 222, true, this);
        health = 3;
        ammunition = 3;
    }

    @Override
    public void action() {

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
        if(health == 0) {
            die();
        }
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
        if(velY > -7)
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

    private void die() {
        velX = 0;
        velY = 0;
        opacity = 0;
        alive = false;
    }

    private void waveEmission() {
        if(frameCount > 10) {
            Wave wave = new Wave(x + width / 2, y + height / 2);
            getGameObjects().add(getGameObjects().indexOf(this), wave);
            frameCount = 0;
        }
        frameCount++;
    }

    private void shoot() {
        if(ammunition > 0) {
            ammunition--;
            Canonball canonball = new Canonball(x + 62.5f, y + 50);
            Explosion explosion = new Explosion(x + 11, y - 20);
            emit(canonball);
            emit(explosion);
            soundPool.play(canonblast, 1, 1, 0, 0, 0);
        }
    }

    @Override
    protected void onCollision(CollidableGameObject cgo)
    {
        if(cgo.collider.isSolid() && !(cgo instanceof Canonball))
        {
            int dx = (int)((width / 2 + x) - (cgo.getWidth() / 2 + cgo.getX()));
            int dy = (int)((height / 2 + y) - (cgo.getHeight() / 2 + cgo.getY()));
            velX += dx / 2;
            velY += dy / 2;
            if(!invincible) {
                soundPool.play(boathit, 1, 1, 0, 0, 0);
                invincible = true;
                opacity = 255;
                animationFrame = 0;
                health--;
            }
        }
    }
}