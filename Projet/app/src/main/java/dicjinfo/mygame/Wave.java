package dicjinfo.mygame;

public class Wave extends GameObject implements IDynamic {

    int frameCount = 0;

    public Wave(float x, float y) {
        super(R.drawable.wave, x - 50, y - 50, 100, 100, false);
    }

    @Override
    public void update() {
        rWidth += 6;
        rHeight += 6;

        if(frameCount > 25) {
            opacity -= 13;
        }
        if(opacity < 0) {
            gameObjectArray.remove(this);
            dynamicArray.remove(this);
        }
        updateRenderValues();

        frameCount++;
    }
}