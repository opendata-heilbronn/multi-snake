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

	public static Thread getInstance(String host, GameController gameController) {
		int port = 8887;
		WebSocketServer server = new GameServer(new InetSocketAddress(host, port), gameController);
		Thread thread = new Thread(server);
		thread.start();
		return thread;
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
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
	}
}
