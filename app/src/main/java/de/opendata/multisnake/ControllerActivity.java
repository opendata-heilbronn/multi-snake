package de.opendata.multisnake;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.URI;
import java.net.URISyntaxException;

public class ControllerActivity extends AppCompatActivity {

	private WebSocketClient webSocketClient;
	private Thread server;
	private EditText remoteAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controller_activity);
		remoteAddress = (EditText)findViewById(R.id.remoteAddress);
	}

	@Override
	protected void onStart() {
		super.onStart();
		server = GameServer.getInstance(Utils.getIPAddress(true), new GameController() {

			@Override
			public void onLeft() {
				Log.i("SNAKE", "left");
			}

			@Override
			public void onRight() {
				Log.i("SNAKE", "right");
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		server.interrupt();
	}

	public void connectToServer(View view) {
		try {
			webSocketClient = new WebSocketClient(new URI("ws://" + remoteAddress.getText().toString() + ":8887")) {

				@Override
				public void onOpen(ServerHandshake serverHandshake) {
					Log.i("Websocket", "Opened");
					//webSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
				}

				@Override
				public void onMessage(String s) {
					final String message = s;
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							TextView textView = (TextView)findViewById(R.id.ipAddress);
							textView.setText(textView.getText() + "\n" + message);
						}
					});
				}

				@Override
				public void onClose(int i, String s, boolean b) {
					Log.i("Websocket", "Closed " + s);
				}

				@Override
				public void onError(Exception e) {
					Log.i("Websocket", "Error " + e.getMessage());
				}
			};
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		webSocketClient.connect();
	}

	public void turnLeft(View view) {
		if (webSocketClient != null && webSocketClient.getConnection() != null) {
			webSocketClient.send("command:left");
		}
	}

	public void turnRight(View view) {
		if (webSocketClient != null && webSocketClient.getConnection() != null) {
			webSocketClient.send("command:right");
		}
	}
}
