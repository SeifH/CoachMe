package com.example.coachme;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

	private Button formationsButton, directoryButton, statisticsButton;
	private ImageButton helpButton, settingsButton;
	
	
	private final String BUTTON_TAG = "tag";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		formationsButton = (Button) findViewById(R.id.formations);
		formationsButton.setTag(BUTTON_TAG);
		formationsButton.setOnClickListener(this);

		statisticsButton = (Button) findViewById(R.id.game_statistics);
		statisticsButton.setTag(BUTTON_TAG);

		statisticsButton.setOnClickListener(this);

		directoryButton = (Button) findViewById(R.id.player_directory);
		directoryButton.setTag(BUTTON_TAG);
		directoryButton.setOnClickListener(this);

		helpButton = (ImageButton) findViewById(R.id.help);
		helpButton.setOnClickListener(this);

		settingsButton = (ImageButton) findViewById(R.id.settings);
		settingsButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		if (v.getTag().equals("image")) {

			ImageButton b = (ImageButton) v;

			if (b.getId() == R.id.settings) {

				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				builder.setTitle("Credits");
				builder.setMessage("Created by: Olivia Perryman and Seif Hassan");

				builder.setPositiveButton("Close",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

				AlertDialog dialog = builder.create();

				dialog.show();
			}else if(b.getId() == R.id.help)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				builder.setTitle("Help");
				builder.setMessage("Formations\nThe formations option allows you to create"
						+ " different formations and game strategies by dragging and dropping"
						+ " players. You have a marker to draw on the board, an eraser to fix "
						+ "mistakes and the ability to save formations. The new formation butto"
						+ "n will clear the field.\n\nPlayer Directory\nThe player directory al"
						+ "lows you to keep track of your players contact information and the a"
						+ "bility to email your players. Add Players using the “+” button. Sele"
						+ "ct the players desired and click “Send Email” to write the subject "
						+ "and message of the email to send.\n\nGame Statistics\nThis feature al"
						+ "lows you to keep track of stats during games. Maintain a record of yo"
						+ "ur team’s goals, shots, penalties and possession for each game they pl"
						+ "ay. Press Start to begin the timer which you can then pause and resume"
						+ ". The plus increases values and the minus decreases them. Click on the "
						+ "red or yellow cards to keep track of penalties.");

				builder.setPositiveButton("Close",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

				AlertDialog dialog = builder.create();

				dialog.show();
			}
			
		} else {
			Button b = (Button) v;

			if (b.getId() == R.id.formations) {

				Intent intent = new Intent(this, FormationsActivity.class);
				startActivity(intent);
			} else if (b.getId() == R.id.game_statistics) {

				Intent intent = new Intent(this, StatisticsActivity.class);
				startActivity(intent);

			} else if (b.getId() == R.id.player_directory) {

				Intent intent = new Intent(this, DirectoryActivity.class);
				startActivity(intent);
			}

		}

	}

}
