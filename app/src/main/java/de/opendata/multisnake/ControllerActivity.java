package de.opendata.multisnake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

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
		/*server = GameServer.getInstance(Utils.getIPAddress(true), new GameController() {

			@Override
			public void onLeft() {
				Log.i("SNAKE", "left");
			}

			@Override
			public void onRight() {
				Log.i("SNAKE", "right");
			}
		});*/
	}

	@Override
	protected void onStop() {
		super.onStop();
		//server.interrupt();
		webSocketClient.close();
	}

	public void connectToServer(View view) {
		try {
			webSocketClient = new WebSocketClient(new URI("ws://" + remoteAddress.getText().toString() + ":8887")) {

				@Override
				public void onOpen(ServerHandshake serverHandshake) {
					Log.i("Websocket", "Opened");
					//Toast.makeText(ControllerActivity.this, "Connected", Toast.LENGTH_SHORT).show();

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
					//Toast.makeText(ControllerActivity.this, "Closed", Toast.LENGTH_SHORT).show();

				}

				@Override
				public void onError(Exception e) {
				//	Toast.makeText(ControllerActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

					Log.i("Websocket", "Error " + e.getMessage());
				}
			};
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		webSocketClient.connect();
		Toast.makeText(ControllerActivity.this, "Connecting...", Toast.LENGTH_SHORT).show();

	}

	public void turnLeft(View view) {
		if (webSocketClient != null && webSocketClient.getConnection() != null && webSocketClient.getConnection()
				.isOpen()) {
			webSocketClient.send("command:left");
		} else {
			Toast.makeText(ControllerActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
		}
	}

	public void turnRight(View view) {
		if (webSocketClient != null && webSocketClient.getConnection() != null && webSocketClient.getConnection()
				.isOpen()) {
			webSocketClient.send("command:right");
		} else {
			Toast.makeText(ControllerActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
		}
	}
}
