package dicjinfo.mygame;

/**
 * Created by utilisateur on 2017-02-20.
 */
public class Wave extends GameObject implements IDynamic {

    public Wave(float x, float y) {
        super(R.drawable.wave, x - 32.5f, y - 32.5f, 75, 75, false);
    }

    @Override
    public void update() {
        width += 10;
        height += 10;
        x -= 5;
        y -= 5;
        opacity -= 13;
        if(opacity < 0)
            living = false;
    }
}