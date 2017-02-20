package dicjinfo.mygame;

import android.content.Context;

public class Player extends DynamicGameObject{

    public Player(Context context) {
        super(context, R.drawable.terry, 400, 0, 200, 300);
    }

    @Override
    public void update(float gyro) {
        velX = gyro;
        //if(velY < 3)
        //    velY-= 0.2;

        if(y <= 0)
            velY = 5;
        if(y > 1000)
            velY = -5;

        x += velX;
        y += velY;
    }
}