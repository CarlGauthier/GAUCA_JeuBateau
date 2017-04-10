package dicjinfo.mygame;

public class Popcorn extends CollidableGameObject {

    int frameCount = 0;
    float velX, velY;

    public Popcorn(float x, float y, float velX, float velY) {
        super(R.drawable.popcorn, x, y, 50, 50);
        collider = new Collider(40, 40, true, this);
        this.velX = velX;
        this.velY = velY;
    }

    @Override
    public void action() {
        x += velX;
        y += velY;
        if(frameCount == 500)
            gameObjectArray.remove(this);
        frameCount++;
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {
        if(cgo instanceof Player || cgo instanceof Canonball) {
            gameObjectArray.remove(this);
        }
    }
}