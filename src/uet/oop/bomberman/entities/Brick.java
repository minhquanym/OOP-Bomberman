package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean isDestroyed;
    private int timeDestroyingCountDown;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        isDestroyed = false;
    }

    public void setDestroyed(boolean destroyed) {
        if (destroyed) {
            setTimeDestroyingCountDown(18);
        }
        isDestroyed = destroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setTimeDestroyingCountDown(int timeDestroyingCountDown) {
        this.timeDestroyingCountDown = timeDestroyingCountDown;
    }

    public int getTimeDestroyingCountDown() {
        return timeDestroyingCountDown;
    }

    @Override
    public void update() {
        if (isDestroyed()) {
            updateImage();
            if (timeDestroyingCountDown == 0) {
                img = null;
            }
        }
        updateImage();
    }

    void updateImage() {
        if (img == null) {
            return;
        }

        if (isDestroyed()) {
            img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1,
                    Sprite.brick_exploded2, timeDestroyingCountDown, 20).getFxImage();
            timeDestroyingCountDown--;
        }
    }
}
