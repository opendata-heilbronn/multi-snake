package de.opendata.multisnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by bambus on 16.06.16.
 */
public class GameField extends View {

    public static final int FIELD_WIDTH = 480/8;
    public static final int FIELD_HEIGHT = 270/8;

    private GameHandler gameHandler;

    private int backgroundColor;
    private int fruitColor;
    private int snakeColor;

    public GameField(Context context) {
        super(context);
        init();
    }

    public GameField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()  {

        backgroundColor = getContext().getResources().getColor(android.R.color.black);
        fruitColor = getContext().getResources().getColor(android.R.color.holo_green_light);
        snakeColor = getContext().getResources().getColor(android.R.color.holo_red_light);

    }

    public void setGameHandler(GameHandler gameHandler) {

        this.gameHandler = gameHandler;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Background draw
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        //Fruits draw
        List<ObjectTile> fruits = gameHandler.getFruits();
        if(fruits.size() > 0) {

            for (ObjectTile fruit : fruits) {

                drawFruitTile(fruit, canvas);

            }

        }

        //Snake draw
        Snake snake = gameHandler.getSnake();
        if(snake != null) {

            for (ObjectTile tile : snake.getBody()) {

                drawSnakeTile(tile, canvas);

            }

        }

    }

    private void drawSnakeTile(ObjectTile tile, Canvas canvas) {

        int x = tile.getX();
        int y = tile.getY();

        float dx = ((float) getWidth()) / FIELD_WIDTH;
        float dy = ((float) getHeight()) / FIELD_HEIGHT;

        Paint snakePaint = new Paint();
        snakePaint.setColor(snakeColor);
        snakePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(x * dx, y * dy, (x+1) * dx, (y+1) * dy, snakePaint);

    }

    private void drawFruitTile(ObjectTile tile, Canvas canvas) {

        int x = tile.getX();
        int y = tile.getY();

        float dx = ((float) getWidth()) / FIELD_WIDTH;
        float dy = ((float) getHeight()) / FIELD_HEIGHT;

        Paint fruitPaint = new Paint();
        fruitPaint.setColor(fruitColor);
        fruitPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(x * dx, y * dy, (x+1) * dx, (y+1) * dy, fruitPaint);

    }

}
