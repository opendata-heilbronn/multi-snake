package de.opendata.multisnake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GameHandler gameHandler;
    private GameField gameFieldView;

    private Thread gameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameFieldView = (GameField) this.findViewById(R.id.game_field);
        this.gameHandler = new GameHandler(gameFieldView, new ThreadInterface() {

            @Override
            public void stopThread() {

                gameThread.interrupt();

            }

        });

        this.gameHandler.createGame();

        this.gameFieldView.setGameHandler(gameHandler);

    }

    @Override
    protected void onStart() {

        super.onStart();

        gameThread = new Thread(new SnakeRunner());
        gameThread.start();

    }

    @Override
    protected void onPause() {

        super.onPause();
        gameThread.interrupt();

    }



    public void turnLeft(View view) {
        gameHandler.turnSnake(GameHandler.Control.LEFT);
    }

    public void turnRight(View view) {
        gameHandler.turnSnake(GameHandler.Control.RIGHT);
    }

    class SnakeRunner implements Runnable {

        @Override
        public void run() {

            try {

                while(true) {

                    Thread.sleep(100L);
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

        void stopThread();

    }

}
