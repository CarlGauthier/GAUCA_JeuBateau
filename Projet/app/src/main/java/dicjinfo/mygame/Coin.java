package dicjinfo.mygame;

public class Coin extends GameObject implements IDynamic {

    int frameCount = 0;
    Boolean animeMode = false;

    public Coin(float x, float y) {
        super(R.drawable.coin, x, y, 50, 50, 50, 50, false);
    }

    @Override
    public void update() {

        if(animeMode) {
            rWidth+=3;
            if(rWidth >= 50 )
                animeMode = false;
        } else {
            rWidth-=3;
            if(rWidth <= 0)
                animeMode = true;
        }
        updateRenderValues();
    }
}
