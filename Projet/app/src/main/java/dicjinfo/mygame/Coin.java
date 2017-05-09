package dicjinfo.mygame;

public class Coin extends CollidableGameObject {

    static int coinpickup;

    static {
        coinpickup = soundPool.load(GameActivity.getAppContext(), R.raw.coinpickup, 1);
    }

    int frameCount = 0;
    Boolean animeMode = false;
    boolean active = true;

    public Coin() {
        drawableId = R.drawable.coin;
        width = 50;
        height = 50;
        collider = new Collider(50, 50, false, this);
    }

    @Override
    public void action() {

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

    @Override
    protected void onCollision(CollidableGameObject cgo) {

        if((cgo instanceof Player && active)) {
            ((Player)cgo).coinPickup();
            destroy(this);
            soundPool.play(coinpickup, 1, 1, 0, 0, 0);
            active = false;
        }
    }
}
