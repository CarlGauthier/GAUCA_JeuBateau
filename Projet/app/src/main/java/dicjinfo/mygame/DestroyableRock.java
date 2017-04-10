package dicjinfo.mygame;

import android.content.Context;

public class DestroyableRock extends CollidableGameObject {

    public DestroyableRock(float x, float y, float width, float height) {
        super(R.drawable.destroyablerock, x, y, width, height);
        collider = new Collider(width * 0.8f, height * 0.6f, true, this);
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {
        if(!(cgo instanceof Popcorn)) {
            gameObjectArray.add(new Explosion(x, y));
            gameObjectArray.remove(this);
        }
    }
}