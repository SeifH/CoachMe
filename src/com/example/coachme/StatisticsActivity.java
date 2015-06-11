package com.example.coachme;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
/**
 *  This class extends the Activity for the Statistics and controls the features
 * in the Statistics Screen
 * 
 * @author Seif Hassan and Olivia Perryman
 * @since Saturday, March 28 2015
 *
 */
public class StatisticsActivity extends Activity implements OnClickListener,
		OnChronometerTickListener, OnItemClickListener {

	//set up buttons
	private Button newGameButton, homePosession, awayPosession, homeRed,
			awayShotsMinus, homeGoalsMinus, awayGoalsMinus, homeYellow,
			awayRed, awayYellow, awayGoalsPlus, homeShotsPlus, homeGoalsPlus,
			homeShotsMinus, awayShotsPlus;
	private ImageButton save, pause, awayRefresh, homeRefresh, play, drawerBtn;
	//set up timer
	private Chronometer timer;
	//set up counters
	private TextView homeShots, awayShots, homeGoals, awayGoals, homePercent,
			awayPercent, listHeader;
	
	//keep track of time passed for each possession
	private long lastPause;
	private int lastPosession; // seconds passed at last possession
	private int homeSec, awaySec; // total number of seconds with possession
	private int posessionSide;

	//keep track of which side is clicked
	private final int HOME_SIDE = 0;
	private final int AWAY_SIDE = 1;

	// setup saved drawer
	private ArrayList<String> statsNamesMenu;
	private DrawerLayout savedDrawer;
	private ListView savedList;
	private ArrayAdapter<String> adapter;

	private Games currentGame;

	// private final int HIGHLIGHTED_COLOR = 0x70FFFFFF;
	// private final int NON_HIGHLIGHTED_COLOR = 0xB7B7B7;

	/**
	 * onCreate sets up Activity and its components
	 * 
	 * @param savedInstanceState
	 *            - Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// setup user interface layout for this Activity
		setContentView(R.layout.acitivity_statistics);

		//keep track of time passed
		lastPosession = 0;
		homeSec = 0;
		awaySec = 0;
		posessionSide = -1;

		//keep track of saved games
		currentGame = new Games();
		UserStatistics.load();

		//set up button to clear vlaues
		newGameButton = (Button) findViewById(R.id.newGame);
		newGameButton.setOnClickListener(this);

		//set up save button
		save = (ImageButton) findViewById(R.id.saveStats);
		save.setOnClickListener(this);

		//set up drawer
		drawerBtn = (ImageButton) findViewById(R.id.statsDrawerBtn);
		drawerBtn.setOnClickListener(this);

		//set up all value changing buttons
		homePosession = (Button) findViewById(R.id.home);
		homePosession.setSelected(false);
		homePosession.setEnabled(false);
		homePosession.setOnClickListener(this);

		awayPosession = (Button) findViewById(R.id.away);
		awayPosession.setSelected(false);
		awayPosession.setEnabled(false);
		awayPosession.setOnClickListener(this);

		homeRed = (Button) findViewById(R.id.home_redcard);
		homeRed.setOnClickListener(this);

		homeYellow = (Button) findViewById(R.id.home_yellowcard);
		homeYellow.setOnClickListener(this);

		awayRed = (Button) findViewById(R.id.away_redcard);
		awayRed.setOnClickListener(this);

		awayYellow = (Button) findViewById(R.id.away_yellowcard);
		awayYellow.setOnClickListener(this);

		homeShotsPlus = (Button) findViewById(R.id.homeShotsPlus);
		homeShotsPlus.setOnClickListener(this);

		homeShotsMinus = (Button) findViewById(R.id.homeShotsMinus);
		homeShotsMinus.setOnClickListener(this);

		awayShotsPlus = (Button) findViewById(R.id.awayShotsPlus);
		awayShotsPlus.setOnClickListener(this);

		awayShotsMinus = (Button) findViewById(R.id.awayShotsMinus);
		awayShotsMinus.setOnClickListener(this);

		homeGoalsPlus = (Button) findViewById(R.id.homeGoalsPlus);
		homeGoalsPlus.setOnClickListener(this);

		homeGoalsMinus = (Button) findViewById(R.id.homeGoalsMinus);
		homeGoalsMinus.setOnClickListener(this);

		awayGoalsPlus = (Button) findViewById(R.id.awayGoalsPlus);
		awayGoalsPlus.setOnClickListener(this);

		awayGoalsMinus = (Button) findViewById(R.id.awayGoalsMinus);
		awayGoalsMinus.setOnClickListener(this);

		awayRefresh = (ImageButton) findViewById(R.id.away_refresh);
		awayRefresh.setOnClickListener(this);

		homeRefresh = (ImageButton) findViewById(R.id.home_refresh);
		homeRefresh.setOnClickListener(this);

		//set up all views to show values
		homePercent = (TextView) findViewById(R.id.home_percent);
		awayPercent = (TextView) findViewById(R.id.away_percent);

		homeShots = (TextView) findViewById(R.id.numHomeShots);
		awayShots = (TextView) findViewById(R.id.numAwayShots);
		homeGoals = (TextView) findViewById(R.id.numHomeGoals);
		awayGoals = (TextView) findViewById(R.id.numAwayGoals);

		//set up timer
		timer = (Chronometer) findViewById(R.id.timer);
		timer.setOnChronometerTickListener(this);
		//make timer start at zero
		timer.setBase(SystemClock.elapsedRealtime());
		//keep track of pausing
		lastPause = SystemClock.elapsedRealtime();

		//set up buttons to control timer
		pause = (ImageButton) findViewById(R.id.pause);
		pause.setOnClickListener(this);

		play = (ImageButton) findViewById(R.id.play);
		play.setOnClickListener(this);

		//set up drawer 
		listHeader = new TextView(this);
		listHeader.setText("Saved Games");
		listHeader.setGravity(Gravity.CENTER);
		listHeader.setBackgroundColor(Color.rgb(222, 222, 222));
		listHeader.setTextSize(25);
		listHeader.setIncludeFontPadding(false);
		listHeader.setPadding(0, 5, 0, 5);

		setGameNames();

		savedDrawer = (DrawerLayout) findViewById(R.id.stats_drawer_layout);
		savedList = (ListView) findViewById(R.id.stats_right_drawer);
		savedList.addHeaderView(listHeader, null, false);

		//fill the list in the drawer last use of app
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, statsNamesMenu);

		savedList.setAdapter(adapter);
		savedList.setSelector(android.R.color.holo_blue_dark);
		savedDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		savedList.setOnItemClickListener(this);

	}

	/**
	 * Handle action bar item clicks here
	 * 
	 * @param item
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
	 * Called whenever a button is clicked
	 * 
	 * @param the view that is clicked
	 */
	@Override
	public void onClick(View v) {

		// System.out.println("View tag: " + v.getTag());

		if (v.getTag() == null)// its a Button
		{
			Button b = (Button) v;

			//respond to clicks by either increasing or decreasing the view that keeps track of the value
			if (b.getId() == R.id.home_redcard) {

				increaseCardNum(b);
			} else if (b.getId() == R.id.home_yellowcard) {

				increaseCardNum(b);
			} else if (b.getId() == R.id.away_yellowcard) {

				increaseCardNum(b);
			} else if (b.getId() == R.id.away_redcard) {

				increaseCardNum(b);
			} else if (b.getId() == R.id.homeShotsPlus) {

				increaseNum(homeShots);
			} else if (b.getId() == R.id.homeShotsMinus) {

				decreaseNum(homeShots);
			} else if (b.getId() == R.id.homeGoalsPlus) {

				increaseNum(homeGoals);
			} else if (b.getId() == R.id.homeGoalsMinus) {

				decreaseNum(homeGoals);
			} else if (b.getId() == R.id.awayShotsPlus) {

				increaseNum(awayShots);
			} else if (b.getId() == R.id.awayShotsMinus) {

				decreaseNum(awayShots);
			} else if (b.getId() == R.id.awayGoalsPlus) {

				increaseNum(awayGoals);
			} else if (b.getId() == R.id.awayGoalsMinus) {

				decreaseNum(awayGoals);
			} else if (b.getId() == R.id.newGame) {
				//reset all values
				
				// timer.setBase(SystemClock.elapsedRealtime());
				// timer.start();
				// System.out.println("timer started");
				
				//stop the timer and rest to zero
				timer.stop();
				timer.setBase(SystemClock.elapsedRealtime());
				lastPause = SystemClock.elapsedRealtime();
				//show the play button and hide the pause.
				pause.setVisibility(View.INVISIBLE);
				play.setVisibility(View.VISIBLE);

				//reset values
				homeShots.setText("0");
				awayShots.setText("0");
				homeGoals.setText("0");
				awayGoals.setText("0");
				homePercent.setText("0%");
				awayPercent.setText("0%");
				homeRed.setText("0");
				homeYellow.setText("0");
				awayRed.setText("0");
				awayYellow.setText("0");

				homePosession.setSelected(false);
				awayPosession.setSelected(false);

				homePosession.setEnabled(false);
				awayPosession.setEnabled(false);

				posessionSide = -1;
				lastPosession = 0;
				homeSec = 0;
				awaySec = 0;
				// updateSelected();
				resetValues();

			} else if (b.getId() == R.id.home) {
				//change the colour of button to indicate which side has possession
				posessionSide = HOME_SIDE;
				homePosession.setSelected(true);
				awayPosession.setSelected(false);
				// updateSelected();

			} else if (b.getId() == R.id.away) {
				//change the colour of button to indicate which side has possession
				posessionSide = AWAY_SIDE;
				awayPosession.setSelected(true);
				homePosession.setSelected(false);
				// updateSelected();

			}

		} else if (v.getTag().equals("imageButton")) {
			// System.out.println("clicked image button");

			ImageButton b = (ImageButton) v;

			if (b.getId() == R.id.away_refresh) {

				//clear values for cards for away
				
				resetCardNum(awayRed);
				resetCardNum(awayYellow);

				currentGame.setAwayRed(0);
				currentGame.setAwayYellow(0);

			} else if (b.getId() == R.id.home_refresh) {

				//clear values for cards for home
				
				resetCardNum(homeRed);
				resetCardNum(homeYellow);

				currentGame.setHomeRed(0);
				currentGame.setHomeYellow(0);

			} else if (b.getId() == R.id.pause) {

				//record the time when paused
				lastPause = SystemClock.elapsedRealtime();
				//stop the timer counting
				timer.stop();
				//change visibility of play and pause
				pause.setVisibility(View.INVISIBLE);
				play.setVisibility(View.VISIBLE);

			} else if (b.getId() == R.id.play) {

				//start the timer where the last one left off
				timer.setBase(timer.getBase() + SystemClock.elapsedRealtime()
						- lastPause);
				//start the timer
				timer.start();
				//change visibility of play and pause
				play.setVisibility(View.INVISIBLE);
				pause.setVisibility(View.VISIBLE);

				//allow possession to continue
				homePosession.setEnabled(true);
				awayPosession.setEnabled(true);
			} else if (b.getId() == R.id.statsDrawerBtn) {
				// UserDrawings.loadFileNames();
				// statsNamesMenu = UserDrawings.getFileNames();

				
				//show the list of saved games
				setGameNames();

				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, statsNamesMenu);
				savedList.setAdapter(adapter);

				savedDrawer.openDrawer(Gravity.RIGHT);
			} else if (b.getId() == R.id.saveStats) {
				//save the game stats
				saveStats();
			}

		}

	}

	/**
	 * increases the number of card when button is clicked
	 * @param b button pressed
	 */
	private void increaseCardNum(Button b) {
		int n = Integer.parseInt(b.getText().toString());

		String num = String.valueOf(n + 1);

		b.setText(num);
	}

	/**
	 * changes number on card to 0
	 * @param b card
	 */
	private void resetCardNum(Button b) {
		//change the text to 0
		b.setText("0");
	}

	/**
	 * increases the value of textview by one
	 * @param t textview holding value
	 */
	private void increaseNum(TextView t) {
		//change the text to an integer
		int n = (Integer.parseInt(t.getText().toString()));

		//change the view to represent the number plus one 
		String num = String.valueOf(n + 1);

		t.setText(num);
	}

	/**
	 * decreases the value of textview by one
	 * @param t textview holding value
	 */
	private void decreaseNum(TextView t) {
		//change the text to an integer

		int n = (Integer.parseInt(t.getText().toString()));

		//check if the number is zero because can't be negative
		if (n > 0) {
			//if not negative, show the number minus one
			String num = String.valueOf(n - 1);
			t.setText(num);

		}

	}

	/**
	 * Save the values from the game to be retrieved later
	 */
	private void setValues() {

		//change format of values
		currentGame.setHomePossession(Integer.valueOf(homePercent.getText()
				.toString()
				.substring(0, homePercent.getText().toString().indexOf("%"))));

		currentGame.setAwayPossession(Integer.valueOf(awayPercent.getText()
				.toString()
				.substring(0, awayPercent.getText().toString().indexOf("%"))));

		currentGame.setHomeShots(Integer
				.valueOf(homeShots.getText().toString()));
		currentGame.setAwayShots(Integer
				.valueOf(awayShots.getText().toString()));
		currentGame.setHomeGoals(Integer
				.valueOf(homeGoals.getText().toString()));
		currentGame.setAwayGoals(Integer
				.valueOf(awayGoals.getText().toString()));

		currentGame.setHomeRed(Integer.valueOf(homeRed.getText().toString()));
		currentGame.setHomeYellow(Integer.valueOf(homeYellow.getText()
				.toString()));
		currentGame.setAwayRed(Integer.valueOf(awayRed.getText().toString()));
		currentGame.setAwayYellow(Integer.valueOf(awayYellow.getText()
				.toString()));

	}

	/**
	 * clear all values for current game
	 */
	private void resetValues() {
		currentGame.setHomeShots(0);
		currentGame.setAwayShots(0);
		currentGame.setHomeGoals(0);
		currentGame.setAwayGoals(0);
		currentGame.setHomePossession(0);
		currentGame.setAwayPossession(0);
		currentGame.setHomeRed(0);
		currentGame.setAwayRed(0);
		currentGame.setHomeYellow(0);
		currentGame.setAwayYellow(0);
	}

	/**
	 * determine how many seconds have passed in the game to calculate percentages
	 * @return
	 */
	private int secondsPassed() {

		//get text from timer
		String time = (String) timer.getText();
		int secPassed = 0;
		int hrs, mins, secs;

		//check if time has just minutes and seconds, or hours too
		//format is either MM:SS or HH:MM:SS
		if (time.length() == 5) {
			
			//no hours have passed, store minutes and seconds
			hrs = 0;
			mins = Integer.parseInt(time.substring(0, 2));
			secs = Integer.parseInt(time.substring(3, 5));
		} else // an hour has passed
		{
			//store hours, minutes and seconds.
			hrs = Integer.parseInt(time.substring(0, 2));
			mins = Integer.parseInt(time.substring(3, 5));
			secs = Integer.parseInt(time.substring(6, 8));
		}

		//convert to seconds
		secPassed = secs + (60 * mins) + (3600 * hrs);
		// System.out.println(secPassed);

		return secPassed;

	}

	/**
	 * change the text views of possession to represent real time changes.
	 */
	private void updatePosession() {
		// total time passed in seconds
		int totalTime = secondsPassed();
		// System.out.println("Total Time:" + totalTime);

		if (totalTime != 0) {
			int timeWithPossession = totalTime - lastPosession;
			// System.out.println("Time with posession:"+ timeWithPossession);

			//find total time with posession for each side
			if (getPossesionSide() == HOME_SIDE)
				homeSec += timeWithPossession;
			else if (getPossesionSide() == AWAY_SIDE)
				awaySec += timeWithPossession;
			else
				return;

			//calculate percentages and multiply by 100
			int homePercent = (int) Math
					.round(((double) homeSec / (double) totalTime) * 100);
			// System.out.println("homepercent:" + homePercent);
			int awayPercent = (int) Math
					.round(((double) awaySec / (double) totalTime) * 100);
			// System.out.println("awaypercent:" + awayPercent);

			//store how much time has passed
			lastPosession = totalTime;

			//update text
			this.homePercent.setText(homePercent + "%");
			this.awayPercent.setText(awayPercent + "%");

		}
	}

	/**
	 * 
	 * @return which side has possession
	 */
	private int getPossesionSide() {
		return posessionSide;
	}

	/**
	 * set up menu for saved games
	 */
	private void setGameNames() {
		statsNamesMenu = new ArrayList<String>();

		for (int i = 0; i < UserStatistics.getGames().size(); i++) {
			statsNamesMenu.add(UserStatistics.getGames().get(i).getName());
		}

	}

	/**
	 * Called when chronometer changes (every second)
	 */
	@Override
	public void onChronometerTick(Chronometer chronometer) {
		// TODO Auto-generated method stub
		//update the values for posession in real-time
		if (homePosession.isEnabled() || awayPosession.isEnabled())
			updatePosession();
	}

	/**
	 * Called when an item is clicked
	 * @param parent, view, position, id
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		// get selected item
		String sel_item = savedList.getItemAtPosition(position).toString();

		// show game
		showSavedGame(sel_item);

		// close the drawer
		savedDrawer.closeDrawers();

	}

	/**
	 * open a view to see saved game stats
	 * @param n name of game
	 */
	private void showSavedGame(String n) {
		
		final String name = n;
		
		//show the saved game as a popup
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.statistics_saved, null);
		builder.setView(dialogView);
		


		//add values to the text view
		Games game = new Games();
		game = UserStatistics.getGame(n);
		TextView txtVw = (TextView) dialogView.findViewById(R.id.homeSavedPossession);

		txtVw.setText(game.getHomePossession() + "%");
		txtVw = (TextView) dialogView.findViewById(R.id.awaySavedPossession);
		txtVw.setText(game.getAwayPossession() + "%");
		txtVw = (TextView) dialogView.findViewById(R.id.homeSavedShots);
		txtVw.setText(String.valueOf(game.getHomeShots()));
		txtVw = (TextView) dialogView.findViewById(R.id.awaySavedShots);
		txtVw.setText(String.valueOf(game.getAwayShots()));
		txtVw = (TextView) dialogView.findViewById(R.id.homeSavedGoals);
		txtVw.setText(String.valueOf(game.getHomeGoals()));
		txtVw = (TextView) dialogView.findViewById(R.id.awaySavedGoals);
		txtVw.setText(String.valueOf(game.getAwayGoals()));
		txtVw = (TextView) dialogView.findViewById(R.id.homeSavedYellow);
		txtVw.setText(String.valueOf(game.getHomeYellow()));
		txtVw = (TextView) dialogView.findViewById(R.id.awaySavedYellow);
		txtVw.setText(String.valueOf(game.getAwayYellow()));
		txtVw = (TextView) dialogView.findViewById(R.id.homeSavedRed);
		txtVw.setText(String.valueOf(game.getHomeRed()));
		txtVw = (TextView) dialogView.findViewById(R.id.awaySavedRed);
		txtVw.setText(String.valueOf(game.getAwayRed()));


		builder.setTitle(n);

		
		// Set up the buttons
		builder.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
					@Override
					//remove the game from the list
					public void onClick(DialogInterface dialog, int which) {
						UserStatistics.deleteGame(name);
					}
				});
		builder.setNegativeButton("Close",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//do nothing
					}
				});

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(true);
		builder.show();

	}

	/**
	 * saves the current game
	 */
	private void saveStats() {
		//ask user with a popup to name it
		AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
		saveDialog.setTitle("Save Formation");
		saveDialog.setMessage("Save Game Statistics?");

		// Set up the input
		final EditText input = new EditText(this);
		input.setHint("Name");
		input.setTextSize(20);

		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(25);
		input.setFilters(FilterArray);

		// Specify the type of input expected; this, for example, sets the input
		// as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		saveDialog.setView(input);

		// Set up the buttons
		saveDialog.setPositiveButton("Save",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						setValues();
						currentGame.setName(input.getText().toString());
						UserStatistics.saveGame(currentGame,
								currentGame.getName(), StatisticsActivity.this);
						UserStatistics.load();

						// hide keyboard after use
						InputMethodManager key = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						key.hideSoftInputFromWindow(input.getWindowToken(),
								key.HIDE_IMPLICIT_ONLY);
					}
				});
		saveDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// hide keyboard after use
						InputMethodManager key = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						key.hideSoftInputFromWindow(input.getWindowToken(),
								key.HIDE_IMPLICIT_ONLY);
						dialog.cancel();
					}
				});

		final AlertDialog alertDialog = saveDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
		final Button button = alertDialog
				.getButton(AlertDialog.BUTTON_POSITIVE);
		button.setEnabled(false);

		input.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// my validation condition
				if (input.getText().length() > 0
						&& !input.getText().toString().trim().equals("")) {
					button.setEnabled(true);
				} else {
					button.setEnabled(false);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

}
