package dicjinfo.mygame;

public class Canonball extends CollidableGameObject{

    int frameCount = 0;
    float velY = -50;

    public Canonball(float x, float y) {
        super(R.drawable.canonball, x, y, 100, 100, 0, 0, false);
    }

    @Override
    public void update() {

        y += velY;
        if(frameCount < 5) {
            rWidth += 40;
            rHeight += 40;
            updateRenderValues();
        } else if(frameCount == 100) {
            gameObjectArray.remove(this);
            dynamicArray.remove(this);
        }
        frameCount++;
    }

    @Override
    public void onCollision(GameObject gameObject) {

        if(gameObject.isSolid() && !(gameObject instanceof Player))
        {
            if(gameObject instanceof Octo || gameObject instanceof DestroyableRock) {
                if(gameObject instanceof Octo)
                    dynamicArray.remove(gameObject);
                gameObjectArray.remove(gameObject);
            } else if(gameObject instanceof Popcorn) {
                gameObjectArray.remove(gameObject);
                dynamicArray.remove(gameObject);
            }
            gameObjectArray.remove(this);
            dynamicArray.remove(this);
        }
    }
}