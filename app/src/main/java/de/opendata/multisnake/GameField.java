package de.opendata.multisnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import de.opendata.multisnake.tiles.ObjectTile;
import de.opendata.multisnake.tiles.Obstacle;

/**
 * Created by bambus on 16.06.16.
 */
public class GameField extends View {

    public static final int FIELD_WIDTH = 480/8;
    public static final int FIELD_HEIGHT = 270/8;

    private GameHandler gameHandler;

    public GameField(Context context) {
        super(context);
    }

    public GameField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setGameHandler(GameHandler gameHandler) {

        this.gameHandler = gameHandler;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Background draw
        Paint paint = new Paint();
        paint.setColor(gameHandler.getLevel().getBackgroundColor());
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        //Obstacles draw
        List<Obstacle> obstacles = gameHandler.getLevel().getObstacles();
        if(obstacles.size() > 0) {

            for (Obstacle obstacle : obstacles) {

                drawObstacleTile(obstacle, canvas);

            }

        }

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
        snakePaint.setColor(DefaultColor.SNAKE_COLOR.getColor());
        snakePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(x * dx, y * dy, (x+1) * dx, (y+1) * dy, snakePaint);

    }

    private void drawFruitTile(ObjectTile tile, Canvas canvas) {

        int x = tile.getX();
        int y = tile.getY();

        float dx = ((float) getWidth()) / FIELD_WIDTH;
        float dy = ((float) getHeight()) / FIELD_HEIGHT;

        Paint fruitPaint = new Paint();
        fruitPaint.setColor(DefaultColor.FRUIT_COLOR.getColor());
        fruitPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(x * dx, y * dy, (x+1) * dx, (y+1) * dy, fruitPaint);

    }

    private void drawObstacleTile(ObjectTile tile, Canvas canvas) {

        int x = tile.getX();
        int y = tile.getY();

        float dx = ((float) getWidth()) / FIELD_WIDTH;
        float dy = ((float) getHeight()) / FIELD_HEIGHT;

        Paint fruitPaint = new Paint();
        fruitPaint.setColor(DefaultColor.OBSTACLE_STONE.getColor());
        fruitPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(x * dx, y * dy, (x+1) * dx, (y+1) * dy, fruitPaint);

    }

}
