package de.opendata.multisnake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GameStartActivity extends AppCompatActivity {

	private TextView ipAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_start_activity);
		ipAddress = (TextView)findViewById(R.id.ipAddress);
		ipAddress.setText(Utils.getIPAddress(true));
	}

	public void startGame(View view) {
		startActivity(new Intent(this, MainActivity.class));
	}

	public void connectToGame(View view) {
		startActivity(new Intent(this, ControllerActivity.class));
	}
}
