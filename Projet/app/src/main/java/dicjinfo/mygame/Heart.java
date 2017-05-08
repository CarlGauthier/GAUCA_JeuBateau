package dicjinfo.mygame;

public class Heart extends CollidableGameObject {

    int frameCount = 0;
    Boolean animeMode = false;

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

        if((cgo instanceof Player)) {
            ((Player)cgo).increaseHealth();
            destroy(this);
            //soundPool.play(rockbreak, 1, 1, 0, 0, 0);
        }
    }
}