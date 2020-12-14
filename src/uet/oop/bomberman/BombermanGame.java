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
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.enemyObject.*;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.items.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.GameSound;

import javax.sound.sampled.Clip;
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

    public static boolean levelUp = false;
    public static int level = 1;
    public static Bomber player;
    public static List<Entity> enemies = new ArrayList<>();
    public static List<Entity> flames = new ArrayList<>();
    public static List<Entity> staticFinalObjects = new ArrayList<>();
    public static List<Entity> bricks = new ArrayList<>();
    public static List<Entity> bombs = new ArrayList<>();
    public static List<Entity> items = new ArrayList<>();
    public static boolean flag = false;

    public BombermanGame() {
    }

    public static void main(String[] args) {
        new GameSound("sounds/beat.wav", Clip.LOOP_CONTINUOUSLY);
        Application.launch(BombermanGame.class);
    }

    Scene buildNewSceneLevel() {
        // reset
        enemies = new ArrayList<>();
        flames = new ArrayList<>();
        staticFinalObjects = new ArrayList<>();
        bricks = new ArrayList<>();
        bombs = new ArrayList<>();
        items = new ArrayList<>();
        flag = false;

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

        // add key listener to scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    Entity bomb = player.placeBomb();
                    if (bomb == null) {
                        return;
                    }
                    bombs.add(bomb);
                    flames.addAll(((Bomb)bomb).getFlames());
                } else {
                    player.setKeyboardInput(event.getCode());
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                } else {
                    player.setKeyboardInput(null);
                }
            }
        });

        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = buildNewSceneLevel();

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
                    if (levelUp) {
                        levelUp = false;
                        createMap();
                        Scene scene = buildNewSceneLevel();
                        stage.setScene(scene);
                    }

                    update();
                    render();
                    lastTime = now;
                }
            }
        };

        // start
        render();
        timer.start();


    }

    public void createMap() {
        Map.loadMap(level);

        for (int i = 0; i < Map.getNumRow(); i++) {
            for (int j = 0; j < Map.getNumCol(); j++) {
                if (Map.getValueAtCell(i, j) == 'p') {
                    // player
                    player = new Bomber(j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE, Sprite.player_right.getFxImage(), 0, 1, false, 4);
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
                } else if (Map.getValueAtCell(i, j) == '#') {
                    // Wall
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
                } else if (Map.getValueAtCell(i, j) == ' ') {
                    // Grass
                    staticFinalObjects.add(Map.getEntityAtCell(i, j));
                } else if (Map.getValueAtCell(i, j) == '*' || Map.getValueAtCell(i, j) == 'f' || Map.getValueAtCell(i, j) == 'x' || Map.getValueAtCell(i, j) == 'b' || Map.getValueAtCell(i, j) == 's') {
                    // Brick
                    bricks.add(Map.getEntityAtCell(i, j));
                } else if ('1' <= Map.getValueAtCell(i, j) && Map.getValueAtCell(i, j) <= '5') {
                    // enemy
                    if (Map.getValueAtCell(i, j) == '1') {
                        // balloom
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy balloom = new Balloom(x, y, Sprite.balloom_left1.getFxImage(), 0);
                        balloom.setSpeed(2);
                        enemies.add((Entity) balloom);
                    } else if (Map.getValueAtCell(i, j) == '2') {
                        // doll
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy doll = new Doll(x, y, Sprite.doll_left1.getFxImage(), 0);
                        doll.setSpeed(2);
                        enemies.add((Entity) doll);
                    } else if (Map.getValueAtCell(i, j) == '3') {
                        // kondoria
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy kondoria = new Kondoria(x, y, Sprite.kondoria_left1.getFxImage(), 0);
                        kondoria.setSpeed(1);
                        enemies.add((Entity) kondoria);
                    } else if (Map.getValueAtCell(i, j) == '4') {
                        // minvo
                        int x = j * Sprite.SCALED_SIZE;
                        int y = i * Sprite.SCALED_SIZE;
                        Enemy minvo = new Minvo(x, y, Sprite.minvo_left1.getFxImage(), 0);
                        minvo.setSpeed(1);
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

    private void collisionUpdate() {
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

        // brick collides flames2
        for (int idBrick = 0; idBrick < bricks.size(); ++idBrick) {
            Brick brick = (Brick) bricks.get(idBrick);
            if (!brick.isDestroyed() && brick.flameCollision(flames)) {
                brick.setDestroyed(true);
            }
            if (brick.isDestroyed() && brick.getTimeDestroyingCountDown() <= 0) {
                int cellX = brick.getCellY();
                int cellY = brick.getCellX();
                double x = brick.getX();
                double y = brick.getY();

                switch (Map.getValueAtCell(cellX, cellY)) {
                    case '*':
                        Map.setValueAtCell(cellX, cellY, ' ');
                        break;
                    case 'x':
                        items.add(new Portal(x, y, Sprite.portal.getFxImage()));
                        break;
                    case 'f':
                        items.add(new FlameItem(x, y, Sprite.powerup_flames.getFxImage()));
                        break;
                    case 'b':
                        items.add(new BombItem(x, y, Sprite.powerup_bombs.getFxImage()));
                        break;
                    case 's':
                        items.add(new SpeedItem(x, y, Sprite.powerup_speed.getFxImage()));
                        break;
                }

                bricks.remove(idBrick);
                staticFinalObjects.add(new Grass(x, y, Sprite.grass.getFxImage()));
                Map.setEntityAtCell(cellX, cellY, new Grass(x, y, Sprite.grass.getFxImage()));
            }
        }

        // item collides player
        for (int idItem = 0; idItem < items.size(); ++idItem) {
            Entity item = items.get(idItem);
            if (((Item) item).playerCollision(player)) {
                if (item instanceof Portal) {
                    level += 1;
                    levelUp = true;
                    return;
                } else {
                    if (item instanceof SpeedItem) {
                        player.setSpeed(player.getSpeed() + player.baseSpeed);
                    } else if (item instanceof BombItem) {
                        player.setBombLimit(player.getBombLimit() + 1);
                    } else if (item instanceof FlameItem) {
                        player.setBombRange(player.getBombRange() + 1);
                    }
                    // remove item
                    int cellX = item.getCellX();
                    int cellY = item.getCellY();
                    Map.setValueAtCell(cellX, cellY, ' ');
                    items.remove(idItem);
                }
            }
        }

    }

    public void update() {
        // update camera
        camera.update(player);
        canvas.setTranslateX(-camera.getxOffset());
        canvas.setTranslateY(-camera.getyOffset());

        // update collision
        collisionUpdate();

        // update entities
        player.update();
        enemies.forEach(Entity::update);
        bricks.forEach(Entity::update);
        staticFinalObjects.forEach(Entity::update);
        bombs.forEach(Entity::update);
        flames.forEach(Entity::update);
        items.forEach(Entity::update);

        // check first time go through bomb after place.
        for (Entity bomb : bombs) {
            if (((Bomb) bomb).isDone()) {
                Map.removeBomb(bomb);
            }
            else if (Math.max(Math.abs(bomb.getX() - player.getX()),
                    Math.abs(bomb.getY() - player.getY())) >= Sprite.SCALED_SIZE) {
                Map.placeBomb(bomb);
            }

        }
        bombs.removeIf(bomb -> ((Bomb) bomb).isDone());
        flames.removeIf(flame -> ((Flame) flame).isDone());
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
        items.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));

        player.render(gc);
        enemies.forEach(g -> g.render(gc));

        bombs.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
    }
}
