package dicjinfo.mygame;

public class Heart extends GameObject implements IDynamic {

    int frameCount = 0;
    Boolean animeMode = false;

    public Heart(float x, float y) {
        super(R.drawable.heart, x, y, 50, 50, 50, 50, false);
    }

    @Override
    public void update() {

        if(animeMode) {
            rHeight++;
            rWidth++;
            if(rWidth >= 60 )
                animeMode = false;
        } else {
            rHeight--;
            rWidth--;
            if(rWidth <= 40)
                animeMode = true;
        }
        updateRenderValues();
    }
}
