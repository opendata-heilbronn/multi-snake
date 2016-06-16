package de.opendata.multisnake;

import java.util.LinkedList;
import java.util.List;

import de.opendata.multisnake.tiles.ObjectTile;

/**
 * Created by bambus on 16.06.16.
 */
public class Snake {

    private Direction direction = Direction.EAST;
    private LinkedList<ObjectTile> body = new LinkedList<ObjectTile>();

    private ObjectTile virtualBodyTile; //the removed but yet not inserted body part

    public Snake() {

        body.add(new ObjectTile(0, 0));
        addBodyTile();
        addBodyTile();

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<ObjectTile> getBody() {

        return body;

    }

    public void addBodyTile() {

        body.add(new ObjectTile(getHead().getX(), getHead().getY()));

    }

    public ObjectTile getHead() {

        return body.peekLast();

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
