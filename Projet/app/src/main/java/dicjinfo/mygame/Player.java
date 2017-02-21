package dicjinfo.mygame;

import android.content.Context;

public class Player extends CollidableGameObject {

    public void setGyroMovement(float gyroMovement) {
        this.gyroMovement = gyroMovement;
    }

    float velX, velY;
    float gyroMovement;

    public Player() {
        super(R.drawable.terry, 400, 0, 125, 260, true);
    }

    @Override
    public void update() {

        int a;
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

    @Override
    public void onCollision(GameObject gameObject)
    {
        if(gameObject.isSolid())
        {
            int dx = (int)((width / 2 + x) - (gameObject.getWidth() / 2 + gameObject.getX()));
            int dy = (int)((height / 2 + y) - (gameObject.getHeight() / 2 + gameObject.getY()));
            velX += dx / 2;
            velY += dy / 2;
        }
    }
}