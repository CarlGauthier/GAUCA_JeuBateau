package dicjinfo.mygame;

import android.content.Context;

public class Player extends CollidableGameObject{

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
        velX = gyroMovement;
        if(velY > -15)
            velY-= 0.1;
        x += velX;
        y += velY;
    }
}