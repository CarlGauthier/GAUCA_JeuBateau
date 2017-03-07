package dicjinfo.mygame;

import android.content.Context;

public class Player extends CollidableGameObject {

    public void setGyroMovement(float gyroMovement) {
        this.gyroMovement = gyroMovement;
    }

    float velX, velY;
    float gyroMovement;
    int animationFrame = 0;
    private int waveFrameCount = 0;
    boolean invincible = false;

    public Player() {
        super(R.drawable.terry, 400, 0, 125, 260, true);
    }

    @Override
    public void update() {

        move();
        checkBound();

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
        if(velY > -15)
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
        if(waveFrameCount > 2) {
            Wave wave = new Wave(x + width / 2, y + height / 2);
            gameObjectArray.add(1, wave);
            dynamicArray.add(wave);
            waveFrameCount = 0;
        }
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
        }
    }
}