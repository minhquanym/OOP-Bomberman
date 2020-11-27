package uet.oop.bomberman.entities.enemy.moveStrategy;

import java.util.Random;

public class RandomStrategy extends MoveStrategy {
    private Random generator;

    public RandomStrategy() {
        generator = new Random(19900828);
    }

    public int getDirection() {
        int direction = Math.abs(generator.nextInt()) % 4;
        return direction;
    }
}
