package de.opendata.multisnake.tiles;

/**
 * Created by bambus on 16.06.16.
 */
public class ObjectTile {

    private int x;
    private int y;

    public ObjectTile(int x, int y) {

        this.x = x;
        this.y = y;

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectTile objectTile = (ObjectTile) o;

        if (x != objectTile.x) return false;
        return y == objectTile.y;

    }

    @Override
    public int hashCode() {

        int result = x;
        result = 31 * result + y;
        return result;

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
