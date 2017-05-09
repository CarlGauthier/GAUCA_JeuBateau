package dicjinfo.mygame;

public class Ammunition extends CollidableGameObject {

    static int reload;

    static {
        reload = soundPool.load(GameActivity.getAppContext(), R.raw.reload, 1);
    }

    boolean active = true;

    public Ammunition() {
        drawableId = R.drawable.ammunition;
        width = 75;
        height = 75;
        collider = new Collider(75, 75, false, this);
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {

        if((cgo instanceof Player && active)) {
            ((Player)cgo).reload();
            destroy(this);
            soundPool.play(reload, 1, 1, 0, 0, 0);
            active = false;
        }
    }
}
