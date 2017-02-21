package dicjinfo.mygame;

import android.content.Context;

public abstract class CollidableGameObject extends GameObject implements IDynamic{

    public CollidableGameObject(int drawingId, float x, float y, float width, float height, boolean solid) {
        super(drawingId, x, y, width, height, solid);
    }

    public void checkCollision(GameObject gameObject) {
        int w = (int)(0.5 * (width + gameObject.getWidth()));
        int h = (int)(0.5 * (height + gameObject.getHeight()));

        int dx = (int)((width / 2 + x) - (gameObject.getWidth() / 2 + gameObject.getX()));
        int dy = (int)((height / 2 + y) - (gameObject.getHeight() / 2 + gameObject.getY()));

        if (Math.abs(dx) < w && Math.abs(dy) < h)
        {
            if(gameObject.isSolid())
            {
                float wy = w * dy;
                float hx = h * dx;

                if (wy > hx)
                    if (wy > -hx)
                        y = gameObject.getY() + gameObject.getHeight();
                    else
                        x = gameObject.getX() - width;
                else
                if (wy > -hx)
                    x = gameObject.getX() + gameObject.getWidth();
                else
                    y = gameObject.getY() - height;
            }
            onCollision(gameObject);
        }
    }

    public abstract void onCollision(GameObject gameObject);
}
