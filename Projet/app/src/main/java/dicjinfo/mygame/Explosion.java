package dicjinfo.mygame;

public class Explosion extends GameObject {

    int frameCount = 0;

    public Explosion(float x, float y) {
        super(R.drawable.explosion, x - 50, y - 50, 200, 200, 1);
    }

    @Override
    public void action() {
        stretchX(6);
        stretchY(6);
        opacity -= 13;
        if(opacity < 0)
            destroy(this);
        frameCount++;
    }
}