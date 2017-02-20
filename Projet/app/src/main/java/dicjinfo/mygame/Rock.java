package dicjinfo.mygame;

import android.content.Context;

public class Rock extends CollidableGameObject{

    public Rock(Context context, float x, float y, float width, float height) {
        super(context, R.drawable.rock, x, y, width, height);
    }
}
