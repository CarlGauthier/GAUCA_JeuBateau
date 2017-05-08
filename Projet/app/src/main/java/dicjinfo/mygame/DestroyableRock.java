package dicjinfo.mygame;

import android.content.Context;

import org.simpleframework.xml.Element;

@Element
public class DestroyableRock extends CollidableGameObject {

    //Sound
    static int rockbreak;

    static {
        rockbreak = soundPool.load(GameActivity.getAppContext(), R.raw.rockbreak, 1);
    }

    public DestroyableRock() {
        drawableId = R.drawable.destroyablerock;
        width = 100;
        height = 120;
        collider = new Collider(80, 70, true, this);
        collider.update();
    }

    @Override
    protected void onCollision(CollidableGameObject cgo) {
        if(!(cgo instanceof Popcorn)) {
            emit(new Explosion(x, y));
            destroy(this);
            soundPool.play(rockbreak, 1, 1, 0, 0, 0);
        }
    }
}