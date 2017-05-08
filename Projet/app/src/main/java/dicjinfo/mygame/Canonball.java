package dicjinfo.mygame;

public class Canonball extends CollidableGameObject{

    //Sound
    static int canonimpact;

    static {
        canonimpact = soundPool.load(GameActivity.getAppContext(), R.raw.canonimpact, 1);
    }

    private float velY = -50;
    private int frameCount;

    public Canonball(float x, float y) {
        super(R.drawable.canonball, x, y, 0, 0, 1);
        collider = new Collider(100, 100, true, this);
        collider.update();
    }

    @Override
    public void action() {

        y += velY;
        if(frameCount < 5) {
            stretchX(40);
            stretchY(40);
        } else if(frameCount == 100) {
            destroy(this);
        }
        frameCount++;
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {
        if(cgo.collider.isSolid()) {
            if(!(cgo instanceof Player) && !(cgo instanceof Popcorn)) {
                emit(new Explosion(x, y));
                soundPool.play(canonimpact, 1, 1, 0, 0, 0);
                destroy(this);
            }
        }
    }
}