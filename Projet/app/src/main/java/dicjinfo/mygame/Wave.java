package dicjinfo.mygame;

public class Wave extends GameObject implements IDynamic {

    public Wave(float x, float y) {
        super(R.drawable.wave, x - 32.5f, y - 32.5f, 75, 75, false);
    }

    @Override
    public void update() {
        width += 8;
        height += 8;
        x -= 4;
        y -= 4;
        opacity -= 13;
        if(opacity < 0)
            living = false;
    }
}