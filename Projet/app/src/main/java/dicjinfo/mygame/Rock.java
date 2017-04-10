package dicjinfo.mygame;

import android.content.Context;

public class Rock extends CollidableGameObject {

    public Rock(float x, float y, float width, float height) {
        super(R.drawable.rock, x, y, width, height);
        collider = new Collider(width * 0.8f, height * 0.6f, true, this);
    }
}