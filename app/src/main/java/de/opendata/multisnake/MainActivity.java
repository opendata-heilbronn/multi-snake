package de.opendata.multisnake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private GameHandler gameHandler;
    private GameField gameFieldView;

    private Thread gameThread;
	private WebSocketServer server;

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

                gameThread = new Thread(new SnakeRunner(100L));
                gameThread.start();

            }

            @Override
            public void startThread(long frameSpeed) {

                gameThread = new Thread(new SnakeRunner(frameSpeed));
                gameThread.start();

            }

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
		server = GameServer.getInstance(Utils.getIPAddress(true), new GameController() {

			@Override
			public void onLeft() {
				Log.i("SNAKE", "left");
				turnLeft(null);
			}

			@Override
			public void onRight() {
				Log.i("SNAKE", "right");
				turnRight(null);
			}
		});


	}

    @Override
    protected void onPause() {

        super.onPause();
        gameThread.interrupt();
		try {
			server.stop();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    public void turnLeft(View view) {
        gameHandler.turnSnake(GameHandler.Control.LEFT);
    }

    public void turnRight(View view) {
        gameHandler.turnSnake(GameHandler.Control.RIGHT);
    }

    class SnakeRunner implements Runnable {

        private long frameSpeed;

        public SnakeRunner(long frameSpeed) {

            this.frameSpeed = frameSpeed;

        }

        @Override
        public void run() {

            try {

                while(!Thread.currentThread().isInterrupted()) {

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
