package dicjinfo.mygame;

public class Coin extends CollidableGameObject {

    int frameCount = 0;
    Boolean animeMode = false;

    public Coin(float x, float y) {
        super(R.drawable.coin, x, y, 50, 50);
        collider = new Collider(50, 50, false, this);
    }

    @Override
    public void update() {

        if(animeMode) {
            stretchX(3);
            if(width >= 50 )
                animeMode = false;
        } else {
            stretchX(-3);
            if(width <= 0)
                animeMode = true;
        }
    }
}
