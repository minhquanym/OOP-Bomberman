package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

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

//    @Override
//    public void render(GraphicsContext gc) {
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//
//        ImageView iv = new ImageView(img);
//        Image base = iv.snapshot(params, null);
//
//        gc.drawImage(base, x * Sprite.SCALED_SIZE / 8, y * Sprite.SCALED_SIZE / 8);
//    }
}
