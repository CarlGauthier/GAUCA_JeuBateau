package dicjinfo.mygame;

import android.content.Context;

public class Player extends DynamicGameObject{

    public Player(Context context) {
        super(context, R.drawable.terry, 400, 0, 125, 260);
    }

    @Override
    public void update(float gyro) {
        int a;
        velX = gyro;
        if(velY > -15)
            velY-= 0.1;

        x += velX;
        y += velY;
    }
}