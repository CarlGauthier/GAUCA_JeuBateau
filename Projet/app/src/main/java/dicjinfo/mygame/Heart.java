package dicjinfo.mygame;

public class Heart extends CollidableGameObject {

    int frameCount = 0;
    Boolean animeMode = false;

    public Heart(float x, float y) {
        super(R.drawable.heart, x, y, 50, 50);
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
}
