package dicjinfo.mygame;

import android.content.Entity;

public class Collider {

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getCenterX() {
        return x + (width / 2);
    }

    public float getCenterY() {
        return y + (height / 2);
    }

    public boolean isSolid() {
        return solid;
    }

    protected float offsetX, offsetY;
    protected float x;
    protected float y;
    protected float width, height;
    protected boolean solid;
    private CollidableGameObject master;

    public Collider(float width, float height, boolean solid, CollidableGameObject master) {
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.master = master;
    }

    protected void update() {
        offsetX = (master.getWidth() - width) / 2;
        offsetY = (master.getHeight() - height) / 2;
        x = offsetX + master.getX();
        y = offsetY + master.getY();
    }

    public boolean isColliding(Collider c) {
        update();
        c.update();
        float w = 0.5f * (width + c.getWidth());
        float h = 0.5f * (height + c.getHeight());
        float dx = getCenterX() - c.getCenterX();
        float dy = getCenterY() - c.getCenterY();
        if (Math.abs(dx) < w && Math.abs(dy) < h) {
            return true;
        }
        return false;
    }
}