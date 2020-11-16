package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class MovableEntity extends Entity {
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

    public void animate() {
        if (animationStep+1 <= MAX_ANIMATION_STEP) {
            animationStep++;
        } else {
            // reset it
            animationStep = 0;
        }
    }
}
