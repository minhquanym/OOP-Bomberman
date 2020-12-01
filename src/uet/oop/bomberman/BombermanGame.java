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
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.enemyObject.*;
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
    private List<Entity> enemies = new ArrayList<>();
    private List<Entity> flames = new ArrayList<>();
    private List<Entity> staticFinalObjects = new ArrayList<>();
    private List<Entity> bricks = new ArrayList<>();
    private List<Entity> bombs = new ArrayList<>();
    private boolean flag = false;

    public BombermanGame() {
        player = new Bomber(Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, Sprite.player_right.getFxImage(), 0, 1, false, 4);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
        new GameSound("sounds/beat.wav");
    }

    @Override
    public void start(Stage stage) throws Exception {
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
                    bombs.add(bomb);
                    flames.addAll(((Bomb)bomb).getFlames());

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
                    bricks.add(Map.getEntityAtCell(i, j));
                } else if (Map.getValueAtCell(i, j) == 'x') {
                    // Portal
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
                } else if ('1' <= Map.getValueAtCell(i, j) && Map.getValueAtCell(i, j) <= '5') {
                    // enemy
                    if (Map.getValueAtCell(i, j) == '1') {
                        // balloom
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy balloom = new Balloom(x, y, Sprite.balloom_left1.getFxImage(), 0);
                        balloom.setSpeed(4);
                        enemies.add((Entity) balloom);
                    } else if (Map.getValueAtCell(i, j) == '2') {
                        // doll
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy doll = new Doll(x, y, Sprite.doll_left1.getFxImage(), 0);
                        doll.setSpeed(4);
                        enemies.add((Entity) doll);
                    } else if (Map.getValueAtCell(i, j) == '3') {
                        // kondoria
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy kondoria = new Kondoria(x, y, Sprite.kondoria_left1.getFxImage(), 0);
                        kondoria.setSpeed(4);
                        enemies.add((Entity) kondoria);
                    } else if (Map.getValueAtCell(i, j) == '4') {
                        // minvo
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy minvo = new Minvo(x, y, Sprite.minvo_left1.getFxImage(), 0);
                        minvo.setSpeed(4);
                        enemies.add((Entity) minvo);
                    } else if (Map.getValueAtCell(i, j) == '5') {
                        // oneal
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy oneal = new Oneal(x, y, Sprite.oneal_left1.getFxImage(), 0);
                        oneal.setSpeed(4);
                        enemies.add((Entity) oneal);
                    }
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
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

        // player collides flames
        if (player.flameCollision(flames)){
            player.setAlive(false);
        }

        // player collides enemy
        if (player.enemyCollision(enemies)) {
            player.setAlive(false);
        }

        // enemy collides flames
        for (int idEnemy = 0; idEnemy < enemies.size(); ++idEnemy) {
            Enemy enemy = (Enemy) enemies.get(idEnemy);
            if (enemy.isAlive() && enemy.flameCollision(flames)) {
                enemy.setAlive(false);
            }
            if (!enemy.isAlive() && enemy.getTimeLiveLeft() <= 0) {
                enemies.remove(idEnemy);
            }
        }

        // brick collides flames
        for (int idBrick = 0; idBrick < bricks.size(); ++idBrick) {
            Brick brick = (Brick) bricks.get(idBrick);
            if (!brick.isDestroyed() && brick.flameCollision(flames)) {
                brick.setDestroyed(true);
            }
            if (brick.isDestroyed() && brick.getTimeDestroyingCountDown() <= 0) {
                bricks.remove(idBrick);
            }
        }

        // update entities
        player.update();
        enemies.forEach(Entity::update);
        bricks.forEach(Entity::update);
        staticFinalObjects.forEach(Entity::update);
        bombs.forEach(Entity::update);
        flames.forEach(Entity::update);

        flames.removeIf(flame -> ((Flame) flame).isDone());
        bombs.removeIf(bomb -> ((Bomb) bomb).isDone());
    }

    public void render() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // render entities
        staticFinalObjects.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));

        player.render(gc);
        enemies.forEach(g -> g.render(gc));

        bombs.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
    }
}
