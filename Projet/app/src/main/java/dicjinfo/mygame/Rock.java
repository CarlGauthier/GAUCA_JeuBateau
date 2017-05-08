package dicjinfo.mygame;

import android.content.Context;

import org.simpleframework.xml.Element;

@Element
public class Rock extends CollidableGameObject {

    public Rock() {
        drawableId = R.drawable.rock;
        width = 100;
        height = 120;
        collider = new Collider(80, 70, true, this);
    }
}