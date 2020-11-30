package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected double x;
    protected double y;
    protected boolean removed = false;
    protected Image img;

    public Entity( double x, double y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getCellX() {
        return (int) Math.floor((x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
    }

    public int getCellY() {
        return (int) Math.floor((y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
    }
    public void setImg(Image img) {
        this.img = img;
    }
    public Image getImg() {
        return this.img;
    }

    public void render(GraphicsContext gc) {
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//
//        ImageView iv = new ImageView(img);
//        Image base = iv.snapshot(params, null);
//        gc.drawImage(base, x, y);

        gc.drawImage(img, x, y);
    }
    public abstract void update();
}
