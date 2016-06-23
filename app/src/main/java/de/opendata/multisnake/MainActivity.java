package de.opendata.multisnake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private List<LifecycleListener> lifecycleListeners = new ArrayList<LifecycleListener>();

    private GameHandler gameHandler;
    private GameField gameFieldView;

    private GameThread gameThread;
    private ThreadInterface threadInterface;

    private volatile boolean keepActive;
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

        this.gameFieldView = (GameField) this.findViewById(R.id.game_field);
        this.threadInterface = new ThreadInterface() {

            //an interface for the game handler to communicate with the UI thread
            @Override
            public void startThread(long frameSpeed) {

                if(gameThread == null || gameThread.isSetToStop()) {

                    gameThread = new GameThread(frameSpeed);
                    gameThread.start();

                    Log.v(TAG, "Started game thread");

                }

                else Log.v(TAG, "Did not start game thread - already running");

            }

            @Override
            public boolean isThreadStopped() {

                return gameThread.isSetToStop();

            }

            @Override
            public void stopThread() {

                if(gameThread == null || gameThread.isSetToStop()) Log.v(TAG, "Did not stop game thread - already stopped");

                else {

                    gameThread.stopGameThread();
                    Log.v(TAG, "Stopped game thread");

                }

            }

        };

        this.gameHandler = new GameHandler(gameFieldView, this.threadInterface);

        this.gameFieldView.setGameHandler(gameHandler);
        this.gameHandler.createGame();

    }

    @Override
    protected void onStart() {

        super.onStart();

        this.threadInterface.startThread(this.gameHandler.getLevel().getFrameSpeed());
		this.gameHandler.notifyStart(); //todo use list

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

        this.threadInterface.stopThread();
        this.gameHandler.notifyPause(); //todo use list

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

    public void onResetButtonClick(View view) {

        gameHandler.endLevel(GameHandler.Result.ABORT);
        gameHandler.createGame();

        Log.d(TAG, "Resetted game");

    }

    class GameThread extends Thread {

        private long frameSpeed;
        private boolean keepActive = true;

        public GameThread(long frameSpeed) {

            this.frameSpeed = frameSpeed;

        }

        public void stopGameThread() {

            this.keepActive = false;

        }

        public boolean isSetToStop() {

            return !this.keepActive;

        }

        @Override
        public void run() {

            while(keepActive) {

                //Log.v(TAG, "I'm still alive: " + Thread.currentThread().getName());

                gameHandler.beforeNextFrame();

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        gameFieldView.invalidate(); //obwohl es nicht mehr alive ist, tut er es noch ein mal invalidaten
                    }

                });

                try {

                    Thread.sleep(frameSpeed);

                }
                catch (InterruptedException e) {
                    this.stopGameThread();
                }

                gameHandler.afterNextFrame();

            }

        }

    }

    interface ThreadInterface {

        void startThread(long frameSpeed);

        boolean isThreadStopped();

        void stopThread();

    }

}
