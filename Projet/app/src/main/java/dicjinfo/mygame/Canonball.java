package dicjinfo.mygame;

public class Canonball extends CollidableGameObject{

    int frameCount = 0;
    float velY = -50;

    public Canonball(float x, float y) {
        super(R.drawable.canonball, x, y, 0, 0);
        collider = new Collider(100, 100, true, this);
    }

    @Override
    public void action() {

        y += velY;
        if(frameCount < 5) {
            stretchX(40);
            stretchY(40);
        } else if(frameCount == 100) {
            gameObjectArray.remove(this);
        }
        frameCount++;
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {
        if(cgo.collider.isSolid()) {
            if(!(cgo instanceof Player) && !(cgo instanceof Popcorn)) {
                gameObjectArray.add(new Explosion(x, y));
                gameObjectArray.remove(this);
            }
        }
    }
}