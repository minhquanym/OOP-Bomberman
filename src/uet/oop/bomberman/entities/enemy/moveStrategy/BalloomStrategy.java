package uet.oop.bomberman.entities.enemy.moveStrategy;

import java.util.Random;

public class BalloomStrategy extends MoveStrategy {
    private Random generator;
    private static BalloomStrategy instance;

    private BalloomStrategy() {
        long currentTime = System.currentTimeMillis();
        generator = new Random(currentTime);
    }

    public static BalloomStrategy getInstance() {
        if (instance == null) {
            instance = new BalloomStrategy();
        }
        return instance;
    }

    public int getDirection() {
        int direction = Math.abs(generator.nextInt()) % 4;
        return direction;
    }
}
