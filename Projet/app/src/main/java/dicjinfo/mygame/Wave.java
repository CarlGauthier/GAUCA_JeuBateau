package dicjinfo.mygame;

public class Wave extends GameObject {

    int frameCount = 0;

    public Wave(float x, float y) {
        super(R.drawable.wave, x - 50, y - 50, 100, 100);
    }

    @Override
    public void action() {
        stretchX(6);
        stretchY(6);

        if(frameCount > 25) {
            opacity -= 13;
        }
        if(opacity < 0)
            gameObjectArray.remove(this);
        frameCount++;
    }
}