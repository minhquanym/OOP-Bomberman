package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {
    private boolean picked;

    public Item(double x, double y, Image img) {
        super(x, y, img);
        picked = false;
    }

    public void setPicked(boolean picked) {
        picked = picked;
    }

    // player collision
    public boolean playerCollision(Entity player) {
        Rectangle playerRectangle = player.getBoundingBox();
        Rectangle itemRectangle = getBoundingBox();
        if (playerRectangle.intersects(itemRectangle.getLayoutBounds())) {
            return true;
        }
        return false;
    }
}
