package de.opendata.multisnake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GameHandler gameHandler;
    private GameField gameFieldView;

    private SnakeThread gameThread;

    private boolean keepActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize colors
        DefaultColor.BACKGROUND.setColor(getResources().getColor(android.R.color.black));
        DefaultColor.FRUIT_COLOR.setColor(getResources().getColor(android.R.color.holo_green_light));
        DefaultColor.SNAKE_COLOR.setColor(getResources().getColor(android.R.color.holo_red_light));
        DefaultColor.OBSTACLE_STONE.setColor(getResources().getColor(android.R.color.darker_gray));
        DefaultColor.OBSTACLE_WOOD.setColor(getResources().getColor(android.R.color.holo_orange_light));

        gameFieldView = (GameField) this.findViewById(R.id.game_field);
        this.gameHandler = new GameHandler(gameFieldView, new ThreadInterface() {

            @Override
            public void startThread() {

                gameThread = new SnakeThread(1000L);
                gameThread.start();

            }

            @Override
            public void startThread(long frameSpeed) {

                gameThread = new SnakeThread(frameSpeed);
                gameThread.start();

            }

            @Override
            public void stopThread() {

                gameThread.stopGameThread();

            }

        });

        this.gameHandler.createGame();
        this.gameFieldView.setGameHandler(gameHandler);

    }

    @Override
    protected void onStart() {

        super.onStart();

        if(gameThread == null || gameThread.isGameThreadStopped()) {

            gameThread = new SnakeThread(1000L);
            gameThread.start();

        }

    }

    @Override
    protected void onPause() {

        super.onPause();
        gameThread.stopGameThread();

    }

    public void turnLeft(View view) {
        gameHandler.turnSnake(GameHandler.Control.LEFT);
    }

    public void turnRight(View view) {
        gameHandler.turnSnake(GameHandler.Control.RIGHT);
    }

    class SnakeThread extends Thread {

        private long frameSpeed;
        private boolean keepActive = true;

        public SnakeThread(long frameSpeed) {

            this.frameSpeed = frameSpeed;

        }

        public void stopGameThread() {

            this.keepActive = false;

        }

        public boolean isGameThreadStopped() {

            return !this.keepActive;

        }

        @Override
        public void run() {

            try {

                while(keepActive) {

                    Thread.sleep(frameSpeed);
                    gameHandler.nextFrame();

                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            gameFieldView.invalidate();
                        }

                    });

                }

            }

            catch (InterruptedException e) {

            }

        }

    }

    interface ThreadInterface {

        void startThread();

        void startThread(long frameSpeed);

        void stopThread();

    }

}
