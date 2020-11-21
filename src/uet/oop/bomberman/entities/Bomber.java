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
    private int direction;
    private boolean isMoving;
    private int speed;

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
    }

    public void setKeyboardInput(KeyCode keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isGrass(double newX, double newY) {
        for (int c = 0; c < 4; c++) {
            int _x = (int)((newX + Sprite.CHECK_SIZE * (c % 2) + (Sprite.SCALED_SIZE - Sprite.CHECK_SIZE) * (1 - c % 2)) / Sprite.SCALED_SIZE);
            int _y = (int)((newY + Sprite.CHECK_SIZE * (c / 2) + (Sprite.SCALED_SIZE - Sprite.CHECK_SIZE) * (1 - c / 2)) / Sprite.SCALED_SIZE);
            if (!(Map.getEntityAtCell(_y, _x) instanceof Grass)) {
                return false;
            }
        }
        return true;

    }
    public Set<Integer> getCanDirection() {
        Set<Integer> canDirection = new HashSet<Integer>();
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        for (int dir = 0; dir < 4; dir++) {
            double newX = this.getX() + dx[dir]*speed;
            double newY = this.getY() + dy[dir]*speed;

            if (isGrass(newX, newY)) {
                canDirection.add(dir);
            }
        }
        return canDirection;
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
    }
    void updatePosition() {
        Set<Integer> canDirection = getCanDirection();
        if (!canDirection.contains(direction)) {
            return;
        }
        switch (direction) {
            case 0:
                y -= speed;
                break;
            case 1:
                x += speed;
                break;
            case 2:
                y += speed;
                break;
            case 3:
                x -= speed;
                break;
        }
    }

    // update image which suits for new direction and new animationStep
    void updateImage() {
        switch (direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animationStep, 3).getFxImage();
                }
                break;
            case 1:
                img = Sprite.player_right.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animationStep, 3).getFxImage();
                }
                break;
            case 2:
                img = Sprite.player_down.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animationStep, 3).getFxImage();
                }
                break;
            case 3:
                img = Sprite.player_left.getFxImage();
                if (isMoving) {
                    img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animationStep, 3).getFxImage();
                }
                break;
        }
    }
}
