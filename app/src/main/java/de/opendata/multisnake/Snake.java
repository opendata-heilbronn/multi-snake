package de.opendata.multisnake;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import de.opendata.multisnake.tiles.ObjectTile;
import de.opendata.multisnake.tiles.SnakeTile;

/**
 * Created by bambus on 16.06.16.
 */
public class Snake {

    public static final String TAG = Snake.class.getSimpleName();

    private Direction direction = Direction.EAST;
    private LinkedList<SnakeTile> body;

    private SnakeTile virtualBodyTile; //the removed but yet not inserted body part

    public Snake() {

        body = new LinkedList<SnakeTile>();

        body.add(new SnakeTile(0, 0));
        body.add(new SnakeTile(1, 0));
        body.add(new SnakeTile(2, 0));
        //addBodyTile(); //mit diesen Methoden wächst die Schlange aus sich heraus!
        //addBodyTile();

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<SnakeTile> getBody() {

        return body;

    }

    public void addBodyTile() {

        body.add(new SnakeTile(getHead().getX(), getHead().getY()));
        Log.v(TAG, "Snake length +1 (now " + body.size() + ")");

    }

    public ObjectTile getHead() {

        return body.peekLast();

    }

    public ObjectTile getTail() {

        return body.peekFirst();

    }

    public void removeTail() {

        virtualBodyTile = body.pollFirst();

        int x = 0;
        int y = 0;

        ObjectTile head = body.peekLast();

        switch (direction) {

            case NORTH:
                x = 0;
                y = -1;
                break;
            case SOUTH:
                x = 0;
                y = 1;
                break;
            case EAST:
                x = 1;
                y = 0;
                break;
            case WEST:
                x = -1;
                y = 0;
                break;
        }

        virtualBodyTile.setX(head.getX() + x);
        virtualBodyTile.setY(head.getY() + y);

    }

    public void moveHead() {
        
        this.body.add(virtualBodyTile);

    }

    public void turnRight() {

        switch (direction) {

            case NORTH:
                this.setDirection(Direction.EAST);
                break;
            case SOUTH:
                this.setDirection(Direction.WEST);
                break;
            case EAST:
                this.setDirection(Direction.SOUTH);
                break;
            case WEST:
                this.setDirection(Direction.NORTH);
                break;
        }

    }

    public void turnLeft() {

        switch (direction) {

            case NORTH:
                this.setDirection(Direction.WEST);
                break;
            case SOUTH:
                this.setDirection(Direction.EAST);
                break;
            case EAST:
                this.setDirection(Direction.NORTH);
                break;
            case WEST:
                this.setDirection(Direction.SOUTH);
                break;
        }

    }

    public ObjectTile getVirtualBodyTile() {
        return virtualBodyTile;
    }

    private enum Direction {

        NORTH,
        SOUTH,
        EAST,
        WEST;

    }

}
