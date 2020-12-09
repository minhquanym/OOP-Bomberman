package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.items.Portal;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private static int idLevel;
    private static int numRow;
    private static int numCol;
    private static char[][] gameMap;
    private static Scanner reader;
    private static Entity[][] entityMap;

    Map() {

    }

    public static void placeBomb(Entity bomb) {
        Map.setEntityAtCell(bomb.getCellY(), bomb.getCellX(), bomb);
        Map.setValueAtCell(bomb.getCellY(), bomb.getCellX(), '~');
    }

    public static void removeBomb(Entity bomb) {
        Map.setEntityAtCell(bomb.getCellY(), bomb.getCellX(), new Grass(bomb.getX(), bomb.getY(), Sprite.grass.getFxImage()));
        Map.setValueAtCell(bomb.getCellY(), bomb.getCellX(), ' ');
    }

    public static int getNumRow() {
        return numRow;
    }

    public static int getNumCol() {
        return numCol;
    }

    public static char getValueAtCell(int i, int j) {
        return gameMap[i][j];
    }

    public static void setValueAtCell(int i, int j, char c) {
        gameMap[i][j] = c;
    }

    public static void setEntityAtCell(int i, int j, Entity entity) {
        entityMap[i][j] = entity;
    }

    public static Entity getEntityAtCell(int i, int j) {
        return entityMap[i][j];
    }

    public static Entity getEntityAtCoordinate(double x, double y) {
        int _x = (int) Math.floor((x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
        int _y = (int) Math.floor((y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
        return getEntityAtCell(_y, _x);
    }

    static void loadMap() {
        try {
            reader = new Scanner(new File("res/levels/Level1.txt"));
        } catch (FileNotFoundException err) {
            System.out.println("CCCCC\n");
            err.printStackTrace();
        }
        idLevel = reader.nextInt();
        numRow = reader.nextInt();
        numCol = reader.nextInt();
        reader.nextLine();

        System.out.println(idLevel + " " + numRow + " " + numCol);

        gameMap = new char[numRow][numCol];
        entityMap = new Entity[numRow][numCol];
        for (int i = 0; i < numRow; ++i) {
            String line = reader.nextLine();
            for (int j = 0; j < numCol; ++j) {
                char c = line.charAt(j);
                int x = j * Sprite.SCALED_SIZE;
                int y = i * Sprite.SCALED_SIZE;
                if (c == '#') {
                    entityMap[i][j] = new Wall(x, y, Sprite.wall.getFxImage());
                } else if (c == '*' || c == 'x' || c == 'b' || c == 's' || c == 'f') {
                    entityMap[i][j] = new Brick(x, y, Sprite.brick.getFxImage());
                } else {
                    entityMap[i][j] = new Grass(x, y, Sprite.grass.getFxImage());
                }
                gameMap[i][j] = c;
            }
        }

        for (int i = 0; i < numRow; ++i) {
            for (int j = 0; j < numCol; ++j) {
                System.out.print(gameMap[i][j]);
            }
            System.out.println("");
        }
    }

}
