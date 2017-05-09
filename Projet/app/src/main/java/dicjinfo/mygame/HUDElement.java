package dicjinfo.mygame;

public class HUDElement {

    protected int drawableId;
    protected float x, y;
    protected float width, height;

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public int getDrawableId() { return drawableId; }

    public HUDElement(int drawableId, float x, float y, float width, float height) {
        this.drawableId = drawableId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {

    }
}