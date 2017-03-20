package dicjinfo.mygame;

import android.content.Context;

public class DestroyableRock extends GameObject {

    public DestroyableRock(float x, float y, float width, float height) {
        super(R.drawable.destroyablerock, x, y, width, height, (int)(width * 1.44), (int)(height * 1.66), true);
    }
}