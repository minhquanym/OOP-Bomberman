package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.enemyObject.Balloom;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    public static final int TILES_SIZE = 16;
    
    private GraphicsContext gc;
    private Canvas canvas;

    private GameCamera camera;

    private Bomber player;
    private Enemy enemy;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private boolean flag = false;

    public BombermanGame() {
        player = new Bomber(Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.player_right.getFxImage(), 0, 1, false, 4);
        enemy = new Balloom(Sprite.SCALED_SIZE*2, Sprite.SCALED_SIZE, Sprite.balloom_left1.getFxImage(), 0);
        enemy.setSpeed(7);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // create map
        createMap();

        // create Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Map.getNumCol(), Sprite.SCALED_SIZE * Map.getNumRow());
        gc = canvas.getGraphicsContext2D();

        // create root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // create scene
        Scene scene = new Scene(root);
        scene.setFill(Color.rgb(80, 160, 0));

        // add scene to stage
        stage.setHeight(HEIGHT * Sprite.SCALED_SIZE);
        stage.setWidth(WIDTH * Sprite.SCALED_SIZE);
        stage.setScene(scene);
        stage.show();

        // set up camera
        camera = new GameCamera();

        // initialize
        AnimationTimer timer = new AnimationTimer() {
            long lastTime = 0;
            final long timeRender = 100000000/3;

            @Override
            public void handle(long now) {
                if (now - lastTime >= timeRender) {
                    update();
                    render();
                    lastTime = now;
                }
            }
        };

        // render first map
        entities.forEach(g -> g.render(gc));
        player.render(gc);
        enemy.render(gc);

        // start
        timer.start();

        // add key listener to scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                player.setKeyboardInput(event.getCode());
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                player.setKeyboardInput(null);
            }
        });
    }

    public void createMap() {
        Map.loadMap();

        for (int i = 0; i < Map.getNumRow(); i++) {
            for (int j = 0; j < Map.getNumCol(); j++) {
                entities.add(Map.getEntityAtCell(i, j));
            }
        }
    }

    public void update() {
        // update camera
        camera.update(player);
        canvas.setTranslateX(-camera.getxOffset());
        canvas.setTranslateY(-camera.getyOffset());

        // update entities
//        entities.forEach(Entity::update);
        player.update();
        enemy.update();
    }

    public void render() {
//        entities.forEach(g -> g.render(gc));
        enemy.render(gc);
        player.render(gc);
    }
}
