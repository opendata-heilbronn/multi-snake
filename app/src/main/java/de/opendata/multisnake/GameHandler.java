package de.opendata.multisnake;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.opendata.multisnake.tiles.Fruit;
import de.opendata.multisnake.tiles.ObjectTile;
import de.opendata.multisnake.tiles.Obstacle;

/**
 * Created by bambus on 16.06.16.
 */
public class GameHandler implements LifecycleListener {

    public static final String TAG = "GameHandler";

    private GameField gameFieldView;
    private MainActivity.ThreadInterface threadInterface;

    private Snake snake;
    private List<Fruit> fruits;

    private Level level;

    public GameHandler(GameField view, MainActivity.ThreadInterface threadInterface) {

        this.gameFieldView = view;
        this.threadInterface = threadInterface;

    }

    public void createGame() {

        startLevel(Level.CLASSIC_01);

    }

    @Override
    public void notifyStart() {

    }

    @Override
    public void notifyPause() {

    }

    public void startLevel(Level level) {

        this.level = level;
        snake = new Snake();

        generateFruits();

        long frameSpeed = getLevel().getFrameSpeed();

        threadInterface.startThread(frameSpeed);

    }

    public void generateFruits() {

        this.fruits = new ArrayList<Fruit>();
        int fruitsToGenerate = this.level.getFruitCount();

        for(int i = 0; i < fruitsToGenerate; i++) {

            generateRandomFruit();

        }

    }

    public Level getLevel() {

        return level;

    }

    private void generateRandomFruit() {

        int x = (int) (Math.random() * GameField.FIELD_WIDTH);
        int y = (int) (Math.random() * GameField.FIELD_HEIGHT);

        for(ObjectTile fruit : fruits) {

            if(fruit.getX() == x || fruit.getY() == y) {

                generateRandomFruit(); //achtung: rekursiv
                return;

            }

        }

        fruits.add(new Fruit(x, y));

    }

    public List<Fruit> getFruits() {
        return fruits;
    }

    public Snake getSnake() {
        return snake;
    }

    public void turnSnake(Control control) {

        if(control == Control.LEFT) snake.turnLeft();
        else snake.turnRight();

    }

    //called BEFORE the frame is about to be drawn to the UI
    public synchronized void beforeNextFrame() {

        snake.removeTail();

        ObjectTile virtual = getSnake().getVirtualBodyTile(); //the new head tile, already on it's new position

        //check for edge collision
        if(virtual.getX() <= -1 || virtual.getY() <= -1 || virtual.getX() >= GameField.FIELD_WIDTH || virtual.getY() >= GameField.FIELD_HEIGHT) {

            this.endLevel(Result.FAIL);
            Log.v(TAG, "Snake ran into edge :(");

        }

        //check for obstacle-collision
        for(Obstacle obstacle : getLevel().getObstacles()) {

            if(virtual.equals(obstacle)) {

                this.endLevel(Result.FAIL);
                Log.v(TAG, "Snake ran into obstacle :(");
                break;

            }

        }

        //check for self-collision
        for(ObjectTile bodyTile : getSnake().getBody()) {

            if(virtual.equals(bodyTile)) {

                this.endLevel(Result.FAIL);
                Log.v(TAG, "Snake ran into it's own :(");
                break;

            }

        }

        //check for fruits
        for(int i = 0; i < getFruits().size(); i++) {

            ObjectTile fruit = getFruits().get(i);
            if(snake.getHead().equals(fruit)) {

                snake.addBodyTile();
                getFruits().remove(fruit);
                Log.v(TAG, "Fruit eaten :)");

                break;

            }

        }

        snake.moveHead();

    }

    //called AFTER the frame was drawn
    public synchronized void afterNextFrame() {

        if(getFruits().size() <= 0) endLevel(Result.SUCCESS);

    }

    private void endLevel(Result result) {

        threadInterface.stopThread();

        if(result == Result.SUCCESS) {

            switch (this.getLevel()) {

                case CLASSIC_01:
                    this.startLevel(Level.DESERT_OF_DOOM_02);
                    break;

                case DESERT_OF_DOOM_02:
                    this.startLevel(Level.BEACH_OF_EXHAUSTING_03);
                    break;

                default:
                    this.startLevel(Level.CLASSIC_01);
                    break;

            }
        }


        else {

        }

    }

    enum Control {

        LEFT,
        RIGHT;

    }

    enum Result {

        SUCCESS,
        FAIL;

    }

}
