package dicjinfo.mygame;

public class Popcorn extends GameObject implements IDynamic{

    int frameCount = 0;
    float velX, velY;

    public Popcorn(float x, float y, float velX, float velY) {
        super(R.drawable.popcorn, x, y, 50, 50, true);
        this.velX = velX;
        this.velY = velY;
    }

    @Override
    public void update() {
        x += velX;
        y += velY;
        if(frameCount == 500) {
            gameObjectArray.remove(this);
            dynamicArray.remove(this);
        }
        frameCount++;
    }
}