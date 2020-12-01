package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public abstract class Entity {
    public static final double widthEps = 0.3;
    public static final double heightEps = 0.3;

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

    public double getImgWidth() {
        if (img == null) {
            return 0;
        }
        return img.getWidth();
    }
    public double getImgHeight() {
        if (img == null) {
            return 0;
        }
        return img.getHeight();
    }

    Rectangle getBoundingBox() {
        return new Rectangle(getX()+widthEps, getY()+heightEps, getImgWidth()-widthEps, getImgHeight()-heightEps);
    }

    public void render(GraphicsContext gc) {
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//
//        ImageView iv = new ImageView(img);
//        Image base = iv.snapshot(params, null);
//        gc.drawImage(base, x, y);

        if (img == null) return;
        gc.drawImage(img, x, y);
    }
    public abstract void update();

    public boolean flameCollision(List<Entity> flames) {
        if (this.getImg() == null) {
            return false;
        }
        Rectangle entityRectangle = getBoundingBox();
        for (Entity flame : flames) {
            if (!((Flame) flame).isExploded() || flame.getImg() == null) {
                continue;
            }
            Rectangle flameRectangle = flame.getBoundingBox();
            if (entityRectangle.intersects(flameRectangle.getLayoutBounds())) {
                return true;
            }
        }
        return false;
    }
}
