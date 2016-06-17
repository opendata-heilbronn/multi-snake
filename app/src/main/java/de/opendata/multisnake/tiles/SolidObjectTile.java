package de.opendata.multisnake.tiles;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by bambus on 16.06.16.
 */
public abstract class SolidObjectTile extends ObjectTile {

    private int color;

    public SolidObjectTile(int x, int y, int color) {

        super(x, y);
        this.color = color;

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public abstract Paint getPaint();

}
