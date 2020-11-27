package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.HashSet;
import java.util.Set;

public class Bomber extends MovableEntity {
    /*
             0
             ^
             |
     3 <-   (-1)   -> 1
             |
             v
             2
    */
    private KeyCode keyboardInput;

    public Bomber(int x, int y, Image img) {
        super(x, y, img, 0);
    }

    public Bomber(int x, int y, Image img, int animationStep) {
        super(x, y, img, animationStep);
    }

    public Bomber(int x, int y, Image img, int animationStep, int direction, boolean isMoving, int speed) {
        super(x, y, img, animationStep);
        this.direction = direction;
        this.isMoving = isMoving;
        this.speed = speed;
        this.alive = true;
    }

    public void setKeyboardInput(KeyCode keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    @Override
    public void update() {
        isMoving = true;
        if (keyboardInput == KeyCode.LEFT) {
            direction = LeftDirection;
        } else if (keyboardInput == KeyCode.RIGHT) {
            direction = RightDirection;
        } else if (keyboardInput == KeyCode.UP) {
            direction = UpDirection;
        } else if (keyboardInput == KeyCode.DOWN) {
            direction = DownDirection;
        } else {
            isMoving = false;
        }

        animate();
        updatePosition();
        updateImage();

        keyboardInput = null;
    }

    // update image which suits for new direction and new animationStep
    protected void updateImage() {
        switch (direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animationStep, 20).getFxImage();
                }
                break;
            case 1:
                img = Sprite.player_right.getFxImage();
//                img = Sprite.player_up.
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animationStep, 20).getFxImage();
                }
                break;
            case 2:
                img = Sprite.player_down.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animationStep, 20).getFxImage();
                }
                break;
            case 3:
                img = Sprite.player_left.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animationStep, 20).getFxImage();
                }
                break;
        }
    }

    public double getImgWidth() {
        return img.getWidth();
    }

    public double getImgHeight() {
        return img.getHeight();
    }
}
