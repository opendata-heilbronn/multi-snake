package de.opendata.multisnake.tiles;

import android.graphics.Color;
import android.graphics.Paint;

import de.opendata.multisnake.DefaultColor;

/**
 * Created by bambus on 16.06.16.
 */
public class Obstacle extends SolidObjectTile {

    private Paint paint;

    public Obstacle(int x, int y) {

        super(x, y, DefaultColor.OBSTACLE_STONE.getColor());

        paint = new Paint();
        paint.setColor(DefaultColor.OBSTACLE_STONE.getColor());
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    public Paint getPaint() {

        return paint;

    }

}
