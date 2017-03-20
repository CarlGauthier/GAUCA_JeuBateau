package dicjinfo.mygame;

import android.content.Context;

public class Rock extends GameObject{

    public Rock(float x, float y, float width, float height) {
        super(R.drawable.rock, x, y, width, height, (int)(width * 1.44), (int)(height * 1.66), true);
    }
}