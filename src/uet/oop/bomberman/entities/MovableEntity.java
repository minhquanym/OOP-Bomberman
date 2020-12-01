package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.SnapshotParameters;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class MovableEntity extends Entity {
    protected int direction;
    protected boolean isMoving;
    protected int speed;
    protected boolean alive = true;
    protected int timeLiveLeft;
    protected int animationStep = 0;
    public static int MAX_ANIMATION_STEP = 9400;

    public static final int UpDirection = 0;
    public static final int RightDirection = 1;
    public static final int DownDirection = 2;
    public static final int LeftDirection = 3;

    public MovableEntity(int x, int y, Image img, int animationStep) {
        super(x, y, img);
        this.animationStep = animationStep;
    }

    public int getTimeLiveLeft() {
        return timeLiveLeft;
    }

    public void setTimeLiveLeft(int timeLiveLeft) {
        this.timeLiveLeft = timeLiveLeft;
    }

    public void setAlive(boolean alive) {
        if (!alive) {
            this.timeLiveLeft = 18;
            isMoving = false;
        }
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Set<Integer> getCanDirection() {
        Set<Integer> canDirection = new HashSet<Integer>();
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        for (int dir = 0; dir < 4; dir++) {
            double newX = this.getX() + dx[dir]*speed;
            double newY = this.getY() + dy[dir]*speed;

            if (Map.isGrass(newX, newY)) {
                canDirection.add(dir);
            }
        }
        return canDirection;
    }

    protected void updatePosition() {
        if (!isMoving) return;

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

    protected abstract void updateImage();
    
    public void animate() {
        if (animationStep+1 <= MAX_ANIMATION_STEP) {
            animationStep++;
        } else {
            // reset it
            animationStep = 0;
        }
    }
}
