package de.opendata.multisnake.tiles;

import android.graphics.Color;

/**
 * Created by bambus on 16.06.16.
 */
public class SolidObjectTile extends ObjectTile {

    private Color color;

    public SolidObjectTile(int x, int y, int color) {

        super(x, y);

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
