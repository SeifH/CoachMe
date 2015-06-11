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

/**
 * This is the main activity and home screen of the application
 * 
 * @author Seif Hassan and Olivia Perryman
 * @since March 28, 2015
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	// set up buttons and tags
	private Button formationsButton, directoryButton, statisticsButton;
	private ImageButton helpButton, settingsButton;

	private final String BUTTON_TAG = "tag";

	/**
	 * onCreate sets up Activity and its components
	 * 
	 * @param savedInstanceState
	 *            - Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// setup user interface layout for this Activity
		setContentView(R.layout.activity_main);

		// formations button
		formationsButton = (Button) findViewById(R.id.formations);
		formationsButton.setTag(BUTTON_TAG);
		formationsButton.setOnClickListener(this);

		// statistics button
		statisticsButton = (Button) findViewById(R.id.game_statistics);
		statisticsButton.setTag(BUTTON_TAG);
		statisticsButton.setOnClickListener(this);

		// directory button
		directoryButton = (Button) findViewById(R.id.player_directory);
		directoryButton.setTag(BUTTON_TAG);
		directoryButton.setOnClickListener(this);

		// help button
		helpButton = (ImageButton) findViewById(R.id.help);
		helpButton.setOnClickListener(this);

		// settings button
		settingsButton = (ImageButton) findViewById(R.id.settings);
		settingsButton.setOnClickListener(this);

	}

	/**
	 * Primary collection of menu items
	 * 
	 * @param Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Handle action bar item clicks here.
	 * 
	 * @param item
	 * @return boolean
	 */
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

	/**
	 * Defines click listener for buttons
	 * 
	 * @param view
	 *            that is clicked
	 */
	@Override
	public void onClick(View v) {

		// check if button is an image
		if (v.getTag().equals("image")) {

			// cast the view as an imagebutton
			ImageButton b = (ImageButton) v;

			// settings button
			if (b.getId() == R.id.settings) {

				// Create a pop up
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				builder.setTitle("Credits");
				builder.setMessage("Created by: Olivia Perryman and Seif Hassan");

				// allow user to close popup
				builder.setPositiveButton("Close",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// do nothing, just close popup

							}
						});

				AlertDialog dialog = builder.create();

				dialog.show();
			}
			// help button
			else if (b.getId() == R.id.help) {
				// create pop up
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				builder.setTitle("Help");
				builder.setMessage("Formations\nThe formations option allows you to create"
						+ " different formations and game strategies by dragging and dropping"
						+ " players. You have a marker to draw on the board, an eraser to fix "
						+ "mistakes and the ability to save formations. The garbage butto"
						+ "n will clear the field.\n\nPlayer Directory\nThe player directory al"
						+ "lows you to keep track of your players contact information and the a"
						+ "bility to email your players. Add Players using the '+' button. Sele"
						+ "ct the players desired and click 'Send Email' to write the subject "
						+ "and message of the email to send. Clicking 'Delete' will remove the se"
						+ "lected players from the list.\n\nGame Statistics\nThis feature al"
						+ "lows you to keep track of stats during games. Maintain a record of yo"
						+ "ur team’s goals, shots, penalties and possession for each game they pl"
						+ "ay. Press play to begin the timer which you can then pause and resume"
						+ ". The plus increases values and the minus decreases them. Click on the "
						+ "red or yellow cards to keep track of penalties. Pressing 'Save' will "
						+ "allow you to name the game and open it later and 'New Game' will clear "
						+ "the data.");

				// allow user to close popup
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

		}
		// the button is not an image
		else {
			// cast view as button
			Button b = (Button) v;

			if (b.getId() == R.id.formations) {
				// open formations screen
				Intent intent = new Intent(this, FormationsActivity.class);
				//starts FormationsActivity
				startActivity(intent);
			} else if (b.getId() == R.id.game_statistics) {
				// open statistics screen
				Intent intent = new Intent(this, StatisticsActivity.class);
				//starts StatisticsActivity
				startActivity(intent);

			} else if (b.getId() == R.id.player_directory) {
				// open player directory screen
				Intent intent = new Intent(this, DirectoryActivity.class);
				//starts DirecotryActitvity
				startActivity(intent);
			}

		}

	}

}
