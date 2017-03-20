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
        if(frameCount == 75)
            frameCount = 0;
        frameCount++;
    }

    private void emission() {
        if(frameCount % 75 == 0)
            emitPopcorn();
    }

    private void animate() {
        if(animeMode) {
            rWidth+=2;
            rHeight-=2;
            if(rWidth >= 160)
                animeMode = false;
        } else {
            rWidth-=2;
            rHeight+=2;
            if(rHeight >= 140)
                animeMode = true;
        }
        updateRenderValues();
    }

    private void emitPopcorn() {
        GameObject player = gameObjectArray.get(1);
        if(player.getY() > y + 400 && player.getY() < y + 2000)
        {
            Popcorn popcorn = new Popcorn(
                    x + width / 2 - 25,
                    y + height / 2 - 25,
                    (player.x - x) / 30,
                    (player.y - y) / 30
            );
            gameObjectArray.add(popcorn);
            dynamicArray.add(popcorn);
        }
    }
}