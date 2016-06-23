package de.opendata.multisnake;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bambus on 16.06.16.
 */
public class GameHandler {

    public static final String TAG = "GameHandler";

    private GameField gameFieldView;
    private MainActivity.ThreadInterface threadInterface;

    private Snake snake;

    private List<ObjectTile> fruits = new ArrayList<ObjectTile>();
    private List<ObjectTile> craps = new ArrayList<>();

    public GameHandler(GameField view, MainActivity.ThreadInterface threadInterface) {

        this.gameFieldView = view;
        this.threadInterface = threadInterface;

    }

    public void createGame() {

        snake = new Snake();
        generateFruits();

    }

    public void generateFruits() {

        int fruitsToGenerate = 10;

        for (int i = 0; i < fruitsToGenerate; i++) {

            putRandomFruit();

        }

    }

    private void putRandomFruit() {

        int x = (int) (Math.random() * GameField.FIELD_WIDTH);
        int y = (int) (Math.random() * GameField.FIELD_HEIGHT);

        for (ObjectTile fruit : fruits) {

            if (fruit.getX() == x || fruit.getY() == y) {

                putRandomFruit(); //todo schwer zu lesen
                return;

            }

        }

        fruits.add(new ObjectTile(x, y));

    }

    public void putCrap() {

        int x = snake.getTail().getX();
        int y = snake.getTail().getY();

        craps.add(new ObjectTile(x, y));

    }

/*
    public void generateCrap() {

        int crapToGenerate = 10;

        for (int i = 0; i < crapToGenerate; i++) {

            putRandomCrap();

        }

    }

    private void putRandomCrap() {

        int x = (int) (Math.random() * GameField.FIELD_WIDTH);
        int y = (int) (Math.random() * GameField.FIELD_HEIGHT);

        for (ObjectTile crap : craps) {

            if (crap.getX() == x || crap.getY() == y) {
                putRandomCrap(); //todo schwer zu lesen
                return;
            }

        }

        craps.add(new ObjectTile(x, y));

    }
    */

    public List<ObjectTile> getFruits() {
        return fruits;
    }

    public List<ObjectTile> getCraps() {
        return craps;
    }

    public Snake getSnake() {
        return snake;
    }

    public void turnSnake(Control control) {

        if (control == Control.LEFT) snake.turnLeft();
        else snake.turnRight();

    }

    public void nextFrame() {

        snake.removeTail();

        ObjectTile virtual = getSnake().getVirtualBodyTile();

        //check for self-collision
        for (ObjectTile bodyTile : getSnake().getBody()) {

            if (virtual.equals(bodyTile)) {

                threadInterface.stopThread();
                Log.v(TAG, "Snake ran into it's own :(");
                break;

            }

        }

        //check for fruits
        for (int i = 0; i < getFruits().size(); i++) {

            ObjectTile fruit = getFruits().get(i);
            if (snake.getHead().equals(fruit)) {

                snake.addBodyTile();
                getFruits().remove(fruit);
                Log.v(TAG, "Tile added :)");
                break;

            }

        }

        snake.moveHead();

    }


    enum Control {

        LEFT,
        RIGHT;

    }

}
