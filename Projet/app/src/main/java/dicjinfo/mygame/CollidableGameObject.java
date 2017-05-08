package dicjinfo.mygame;

import android.content.Context;

public abstract class CollidableGameObject extends GameObject{

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    Collider collider;

    public CollidableGameObject() {

    }

    public CollidableGameObject(int drawableId, float x, float y, float width, float height, int zIndex) {
        super(drawableId, x, y, width, height, zIndex);
    }

    @Override
    public void update() {
        action();
        collider.update();
        checkCollision();
    }

    private void checkCollision() {
        for(int i = 0; i < getGameObjects().size(); i++)
        {
            GameObject go = getGameObjects().get(i);
            if(go instanceof CollidableGameObject && go != this) {
                CollidableGameObject cgo = (CollidableGameObject)go;
                Collider otherCollider = cgo.getCollider();
                if(collider.isColliding(otherCollider)) {
                    onCollision(cgo);
                    cgo.onCollision(this);
                }
            }
        }
    }

    protected void onCollision(CollidableGameObject cgo) {

    }
}