package dicjinfo.mygame;

public class Octo extends CollidableGameObject{

    //Sound
    static int octoshoot;
    static int octodead;

    static {
        octoshoot = soundPool.load(GameActivity.getAppContext(), R.raw.octoshoot, 1);
        octodead = soundPool.load(GameActivity.getAppContext(), R.raw.octodead, 1);
    }

    protected int frameCount = 0;
    Boolean animeMode = false;
    Player player;

    public Octo() {
        drawableId = R.drawable.octo;
        width = 100;
        height = 100;
        collider = new Collider(100, 100, true, this);
        collider.update();
        for (int i = 0; i < getGameObjects().size(); i++) {
            if(getGameObjects().get(i) instanceof Player) {
                player = (Player)getGameObjects().get(i);
            }
        }
    }

    @Override
    public void action() {
        emission();
        animate();

    }

    private void emission() {
        if(frameCount % 75 == 0) {
            emitPopcorn();
            frameCount = 0;
        }
        frameCount++;
    }

    private void animate() {
        if(animeMode) {
            stretchX(2);
            stretchY(-2);
            if(width >= 160)
                animeMode = false;
        } else {
            stretchX(-2);
            stretchY(2);
            if(height >= 140)
                animeMode = true;
        }
    }

    private void emitPopcorn() {

        if(player.getY() > y + 400 && player.getY() < y + 2000)
        {
            Popcorn popcorn = new Popcorn(
                x + width / 3 - 25,
                y + height / 3 - 25,
                (player.x - x) / 50,
                (player.y - y) / 50
            );
            emit(popcorn);
            soundPool.play(octoshoot, 1, 1, 0, 0, 0);
        }
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {
        if(!(cgo instanceof Popcorn)) {
            emit(new Explosion(x, y));
            destroy(this);
            soundPool.play(octodead, 1, 1, 0, 0, 0);
        }
    }
}