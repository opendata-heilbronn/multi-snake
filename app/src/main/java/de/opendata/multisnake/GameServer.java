package de.opendata.multisnake;

import android.util.Log;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class GameServer extends WebSocketServer {

	private GameController gameController;

	public GameServer(InetSocketAddress address, GameController gameController) {
		super(address);
		this.gameController = gameController;
	}

	public static WebSocketServer getInstance(String host, GameController gameController) {
		int port = 8887;
		WebSocketServer server = new GameServer(new InetSocketAddress(host, port), gameController);
		server.start();
		Log.i("SNAKE", "WebSocket server started");
		return server;
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		Log.i("SNAKE", "opened");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		Log.i("SNAKE", "closed");
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		Log.i("SNAKE", "Message: " + message);
		int posColon = message.indexOf(":");
		if (posColon != -1) {
			String command = message.substring(posColon + 1);
			Log.i("SNAKE", "Command: " + command);
			if ("left".equals(command)) {
				gameController.onLeft();
			} else if ("right".equals(command)) {
				gameController.onRight();
			}
		}
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
	}
}
