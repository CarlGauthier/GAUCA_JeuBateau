package dicjinfo.mygame;

public class HUDHeart extends HUDElement {

    Player player;
    int indicator;

    public HUDHeart(int indicator, float x, float y, Player player) {
        super(R.drawable.fullheart, x, y, 100, 100);
        this.player = player;
        this.indicator = indicator;
    }

    @Override
    public void action() {

        if(player.getHealth() >= indicator)
            drawableId = R.drawable.fullheart;
        else
            drawableId = R.drawable.emptyheart;
    }
}