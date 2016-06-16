package de.opendata.multisnake;

import android.graphics.Color;

/**
 * Created by bambus on 16.06.16.
 */
public enum DefaultColor {

    FRUIT_COLOR,
    SNAKE_COLOR,
    BACKGROUND,
    OBSTACLE_STONE,
    OBSTACLE_WOOD;

    private int color = android.R.color.white;

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
