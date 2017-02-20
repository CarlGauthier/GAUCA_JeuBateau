package dicjinfo.mygame;

import android.content.Context;

public abstract class CollidableGameObject extends GameObject {

    public CollidableGameObject(Context context, int id, float x, float y, float width, float height) {
        super(context, id, x, y, width, height);
    }

    public void onCollision() {

    }
}
