package dicjinfo.mygame;

public class Octo extends CollidableGameObject{

    int frameCount = 0;
    Boolean animeMode = false;

    public Octo(float x, float y) {
        super(R.drawable.octo, x, y, 100, 100);
        collider = new Collider(100, 100, true, this);
    }

    @Override
    public void action() {
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
        GameObject player = gameObjectArray.get(1);
        if(player.getY() > y + 400 && player.getY() < y + 2000)
        {
            Popcorn popcorn = new Popcorn(
                    x + width / 2 - 25,
                    y + height / 2 - 25,
                    (player.x - x) / 50,
                    (player.y - y) / 50
            );
            gameObjectArray.add(popcorn);
        }
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {
        if(!(cgo instanceof Popcorn)) {
            gameObjectArray.add(new Explosion(x, y));
            gameObjectArray.remove(this);
        }
    }
}