package dicjinfo.mygame;

public class Octo extends GameObject implements IDynamic {

    int frameCount = 0;
    Boolean animeMode = false;

    public Octo(float x, float y) {
        super(R.drawable.octo, x, y, 100, 100, 140, 120, true);
    }

    @Override
    public void update() {
        emission();
        animate();
        frameCount++;
    }

    private void emission() {
        if(frameCount % 2 == 0)
            emitPopcorn();
    }

    private void animate() {
        if(animeMode) {
            rWidth++;
            rHeight--;
            if(rWidth >= 160)
                animeMode = false;
        } else {
            rWidth--;
            rHeight++;
            if(rHeight >= 140)
                animeMode = true;
        }
        updateRenderValues();
    }

    private void emitPopcorn() {
        GameObject player = gameObjectArray.get(1);
        Popcorn popcorn = new Popcorn(
                x + width / 2 - 25,
                y + height / 2 - 25,
                (player.x - x) / 30,
                (player.y - y) / 30
        );
        //gameObjectArray.add(popcorn);
        //dynamicArray.add(popcorn);
    }
}