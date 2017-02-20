package dicjinfo.mygame;

import android.content.Context;

public abstract class DynamicGameObject extends CollidableGameObject{

    protected float velX, velY;

    public DynamicGameObject(Context context, int id, float x, float y, float width, float height) {
        super(context, id, x, y, width, height);
    }

    public abstract void update(float gyro);

    public boolean checkCollision(CollidableGameObject cgo) {
        int w = (int)(0.5 * (width + cgo.getWidth()));
        int h = (int)(0.5 * (height + cgo.getHeight()));

        int dx = (int)((width / 2 + x) - (cgo.getWidth() / 2 + cgo.getX()));
        int dy = (int)((height / 2 + y) - (cgo.getHeight() / 2 + cgo.getY()));

        if (Math.abs(dx) < w && Math.abs(dy) < h)
        {
            float wy = w * dy;
            float hx = h * dx;

            if (wy > hx)
                if (wy > -hx)
                    y = cgo.getY() + cgo.getHeight();
                else
                    x = cgo.getX() - width;
            else
            if (wy > -hx)
                x = cgo.getX() + cgo.getWidth();
            else
                y = cgo.getY() - height;
            return true;
        }
        return false;
    }
}