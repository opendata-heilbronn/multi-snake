package de.opendata.multisnake.tiles;

import android.graphics.Color;

import de.opendata.multisnake.DefaultColor;

/**
 * Created by bambus on 16.06.16.
 */
public class Obstacle extends SolidObjectTile {

    public Obstacle(int x, int y) {

        super(x, y, DefaultColor.OBSTACLE_STONE.getColor());

    }

}
