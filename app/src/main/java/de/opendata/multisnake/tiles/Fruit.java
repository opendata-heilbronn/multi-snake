package de.opendata.multisnake.tiles;

import android.graphics.Paint;

import de.opendata.multisnake.DefaultColor;

/**
 * Created by Cedric on 17.06.2016.
 */
public class Fruit extends SolidObjectTile {

    private Paint paint;

    public Fruit(int x, int y) {

        super(x, y, DefaultColor.FRUIT_COLOR.getColor());

        paint = new Paint();
        paint.setColor(DefaultColor.FRUIT_COLOR.getColor());
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    public Paint getPaint() {

        return paint;

    }

}