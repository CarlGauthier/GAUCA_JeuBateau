package dicjinfo.mygame;

public class HUDText extends HUDElement {

    String text;

    public String getText() {
        return text;
    }

    public HUDText(float x, float y) {
        super(0, x, y, 0, 0);
    }
}