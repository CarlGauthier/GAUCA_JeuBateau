package dicjinfo.mygame;

public class Heart extends CollidableGameObject {

    static int heartpickup;

    static {
        heartpickup = soundPool.load(GameActivity.getAppContext(), R.raw.heartpickup, 1);
    }

    int frameCount = 0;
    Boolean animeMode = false;
    boolean active = true;

    public Heart() {
        drawableId = R.drawable.heart;
        width = 50;
        height = 50;
        collider = new Collider(50, 50, false, this);
    }

    @Override
    public void action() {

        if(animeMode) {
            stretchX(1);
            stretchY(1);
            if(width >= 60 )
                animeMode = false;
        } else {
            stretchX(-1);
            stretchY(-1);
            if(width <= 40)
                animeMode = true;
        }
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {

        if((cgo instanceof Player && active)) {
            ((Player)cgo).increaseHealth();
            destroy(this);
            soundPool.play(heartpickup, 1, 1, 0, 0, 0);
            active = false;
        }
    }
}