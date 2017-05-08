package dicjinfo.mygame;

public class HUDAmmunition extends HUDElement{

    Player player;
    int indicator;

    public HUDAmmunition(int indicator, float x, float y, Player player) {
        super(R.drawable.ammunitionicon, x, y, 100, 100);
        this.player = player;
        this.indicator = indicator;
    }

    @Override
    public void action() {

        if(player.getAmmunition() >= indicator)
            drawableId = R.drawable.ammunitionicon;
        else
            drawableId = R.drawable.ammunitioniconempty;
    }
}
