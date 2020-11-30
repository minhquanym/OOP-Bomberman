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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.enemyObject.Balloom;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.GameSound;

import java.io.File;
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
    private List<Entity> flames = new ArrayList<>();
    private List<Entity> staticFinalObjects = new ArrayList<>();
    private List<Entity> staticObjects = new ArrayList<>();
    private boolean flag = false;

    public BombermanGame() {
        player = new Bomber(Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.player_right.getFxImage(), 0, 1, false, 4);
        enemy = new Balloom(Sprite.SCALED_SIZE*2, Sprite.SCALED_SIZE, Sprite.balloom_left1.getFxImage(), 0);
        enemy.setSpeed(7);
    }

    public static void main(String[] args) {
        new GameSound("sounds/beat.wav");
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // create map
        createMap();
        entities.add(player);

        // create Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Map.getNumCol(), Sprite.SCALED_SIZE * Map.getNumRow());
        gc = canvas.getGraphicsContext2D();

        // create root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // create scene
        Scene scene = new Scene(root);

        // add scene to stage
        stage.setHeight(HEIGHT * Sprite.SCALED_SIZE);
        stage.setWidth(WIDTH * Sprite.SCALED_SIZE);
        stage.setScene(scene);
        stage.show();

        // set up camera
        camera = new GameCamera();

        // initialize animation timer
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

//        System.out.println(entities.size());

        // start
        render();
        timer.start();

        // add key listener to scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {

                } else {
                    player.setKeyboardInput(event.getCode());
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    Entity bomb = player.placeBomb();
                    entities.add(bomb);
                    flames.addAll(((Bomb) bomb).getFlames());
                } else {
                    player.setKeyboardInput(null);
                }
            }
        });
    }

    public void createMap() {
        Map.loadMap();

        for (int i = 0; i < Map.getNumRow(); i++) {
            for (int j = 0; j < Map.getNumCol(); j++) {
                if (Map.getValueAtCell(i, j) == '#') {
                    // Wall
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
                } else if (Map.getValueAtCell(i, j) == ' ') {
                    // Grass
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
                } else if (Map.getValueAtCell(i, j) == '*') {
                    // Brick
                    staticObjects.add(Map.getEntityAtCell(i, j));
                } else if (Map.getValueAtCell(i, j) == 'x') {
                    // Portal
                    staticObjects.add(Map.getEntityAtCell(i, j));
                } else {
                    // something else
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
                }
            }
        }
    }

    public void update() {
        // update camera
        camera.update(player);
        canvas.setTranslateX(-camera.getxOffset());
        canvas.setTranslateY(-camera.getyOffset());
        entities.forEach(Entity::update);
        staticObjects.forEach(Entity::update);
        staticFinalObjects.forEach(Entity::update);
        flames.forEach(Entity::update);
    }

    public void render() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        staticObjects.forEach(g -> g.render(gc));
        staticFinalObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
    }
}
