package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;


import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    public static final int TILES_SIZE = 16;
    
    private GraphicsContext gc;
    private Canvas canvas;

    private Bomber player = new Bomber(1, 1, Sprite.player_right.getFxImage());
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private boolean flag = false;

    private Map Board = new Map();

    public BombermanGame() {
        player = new Bomber(0, 0, Sprite.player_right.getFxImage(), 0, 1, false, 1);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        // set animation timer
//        long lastTime = 0;


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
//                gc.clearRect(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
            }
        };
        timer.start();

        createMap();

        // add key listener to scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
//                render();
                player.setKeyboardInput(event.getCode());
                player.update();
            }
        });
    }

    public void createMap() {
        Board.loadMap();

        for (int i = 0; i < Board.getNumCol(); i++) {
            for (int j = 0; j < Board.getNumRow(); j++) {
                if (Board.getValueAtCell(i, j) == '#') {
                    Entity object = new Wall(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.wall.getFxImage());
                    entities.add(object);
                } else {
                    Entity object = new Grass(i * Sprite.SCALED_SIZE, j * Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                    entities.add(object);
                }
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        if (!flag) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            entities.forEach(g -> g.render(gc));
            flag = true;

        } else {
            double x = player.getX();
            double y = player.getY();
            gc.clearRect(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
            System.out.println("Clear : " + x + " " + y);
        }
        player.render(gc);

    }
}
