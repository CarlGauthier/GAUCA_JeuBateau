package dicjinfo.mygame;

public class HUDScore extends HUDText{

    Player player;

    public HUDScore(Player player) {
        super(0, 200);
        this.player = player;
    }

    @Override
    public void update() {
        text = "Score: " + player.getScore();
    }
}