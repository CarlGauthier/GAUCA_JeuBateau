package dicjinfo.mygame;

import android.content.Context;

import java.util.ArrayList;

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
    public void update(ArrayList<GameObject> gameObjects) {
        action();
        collider.update();
        checkCollision(gameObjects);
    }

    private void checkCollision(ArrayList<GameObject> gameObjects) {
        for(int i = 0; i < gameObjects.size(); i++)
        {
            GameObject go = gameObjects.get(i);
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