package uet.oop.bomberman;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class GameCamera {
    private double xOffset;
    private double yOffset;

    private double lastYOffset;
    private double lastXOffset;

    GameCamera() {
        xOffset = 0;
        yOffset = 0;

        lastXOffset = 0;
        lastYOffset = 0;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public double getLastYOffset() {
        return lastYOffset;
    }

    public double getLastXOffset() {
        return lastXOffset;
    }

    public void update(Bomber bomber) {
        if (!bomber.isAlive()) {
            return;
        }
        double size = Sprite.SCALED_SIZE;
        xOffset = bomber.getX() + Math.round(bomber.getImgWidth() / 2) - Math.round(BombermanGame.WIDTH * size / 2.0) ;
        yOffset = bomber.getY() + Math.round(bomber.getImgHeight() / 2) - Math.round(BombermanGame.HEIGHT * size / 2.0);
        if (xOffset < 0) { xOffset = 0; }
        if (yOffset < 0) { yOffset = 0; }
        if (xOffset + BombermanGame.WIDTH * size > Math.round(Map.getNumCol() * size)){
            xOffset = Map.getNumCol() * size - BombermanGame.WIDTH * size;
        }
        if (yOffset + BombermanGame.HEIGHT * size > Math.round(Map.getNumRow() * size)){
            yOffset = Map.getNumRow() * size - BombermanGame.HEIGHT * size;
        }
        lastXOffset = xOffset;
        lastYOffset = yOffset;
    }
}
