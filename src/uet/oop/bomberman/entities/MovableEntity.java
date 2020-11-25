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

//	public boolean canMove(double x, double y) {
//		for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
//			double xt = ((_x + x) + c % 2 * 11) / Game.TILES_SIZE; //divide with tiles size to pass to tile coordinate
//			double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE; //these values are the best from multiple tests
//
//			Entity a = Map.getEntityAtCell(xt, yt, this);
//
//			if(!a.collide(this))
//				return false;
//		}
//
//		return true;
//	}

    public void animate() {
        if (animationStep+1 <= MAX_ANIMATION_STEP) {
            animationStep++;
        } else {
            // reset it
            animationStep = 0;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView iv = new ImageView(img);
        Image base = iv.snapshot(params, null);

        int _x = (int)((x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
        int _y = (int)((y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);

        // remove 9 tiles
        for (int i = Math.max(0, _x - 1); i < Math.min(Map.getNumCol(), _x + 2); i++) {
            for (int j = Math.max(0, _y - 1); j < Math.min(Map.getNumRow(), _y + 2); j++) {
                Entity tile = Map.getEntityAtCell(j, i);
                tile.render(gc);
            }
        }
        gc.drawImage(base, x, y);
    }
}
