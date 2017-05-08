package dicjinfo.mygame;

public class Camera {

    final float gameWidth = 1080;
    final float gameHeight = 1920;

    public float getY() {
        return y;
    }

    public float getZoomX() {
        return zoomX;
    }

    public float getZoomY() {
        return zoomY;
    }

    float y;
    float zoomX, zoomY;
    Player player;

    public Camera(int screenWidth, int screenHeight, Player player) {
        zoomX = screenWidth / gameWidth;
        zoomY = screenHeight / gameHeight;
        this.player = player;
    }

    public void update() {
        y = player.getY() - gameHeight + 500;
    }
}