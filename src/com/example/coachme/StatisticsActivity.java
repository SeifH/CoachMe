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
import android.widget.TextView;

public class StatisticsActivity extends Activity implements OnClickListener,
		OnChronometerTickListener, OnItemClickListener {

	private Button newGameButton, homePosession, awayPosession, homeRed,
			awayShotsMinus, homeGoalsMinus, awayGoalsMinus, homeYellow,
			awayRed, awayYellow, awayGoalsPlus, homeShotsPlus, homeGoalsPlus,
			homeShotsMinus, awayShotsPlus;
	private ImageButton save, pause, awayRefresh, homeRefresh, play, drawerBtn;
	private Chronometer timer;
	private TextView homeShots, awayShots, homeGoals, awayGoals, homePercent,
			awayPercent, listHeader;
	private long lastPause;
	private int lastPosession; // seconds passed at last possession
	private int homeSec, awaySec; // total number of seconds with possession
	private int posessionSide;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.statistics);

		lastPosession = 0;
		homeSec = 0;
		awaySec = 0;
		posessionSide = -1;

		currentGame = new Games();
		UserStatistics.load();

		newGameButton = (Button) findViewById(R.id.newGame);
		newGameButton.setOnClickListener(this);

		save = (ImageButton) findViewById(R.id.saveStats);
		save.setOnClickListener(this);

		drawerBtn = (ImageButton) findViewById(R.id.statsDrawerBtn);
		drawerBtn.setOnClickListener(this);

		homePosession = (Button) findViewById(R.id.home);
		homePosession.setSelected(false);
		homePosession.setOnClickListener(this);

		awayPosession = (Button) findViewById(R.id.away);
		awayPosession.setSelected(false);
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

		homePercent = (TextView) findViewById(R.id.home_percent);
		awayPercent = (TextView) findViewById(R.id.away_percent);

		homeShots = (TextView) findViewById(R.id.numHomeShots);
		awayShots = (TextView) findViewById(R.id.numAwayShots);
		homeGoals = (TextView) findViewById(R.id.numHomeGoals);
		awayGoals = (TextView) findViewById(R.id.numAwayGoals);

		timer = (Chronometer) findViewById(R.id.timer);
		timer.setOnChronometerTickListener(this);
		timer.setBase(SystemClock.elapsedRealtime());
		lastPause = SystemClock.elapsedRealtime();

		pause = (ImageButton) findViewById(R.id.pause);
		pause.setOnClickListener(this);

		play = (ImageButton) findViewById(R.id.play);
		play.setOnClickListener(this);

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

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, statsNamesMenu);

		savedList.setAdapter(adapter);
		savedList.setSelector(android.R.color.holo_blue_dark);
		savedDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		savedList.setOnItemClickListener(this);

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

		// System.out.println("View tag: " + v.getTag());

		if (v.getTag() == null)// its a Button
		{
			Button b = (Button) v;

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
				// timer.setBase(SystemClock.elapsedRealtime());
				// timer.start();
				// System.out.println("timer started");
				timer.stop();
				timer.setBase(SystemClock.elapsedRealtime());
				lastPause = SystemClock.elapsedRealtime();
				pause.setVisibility(View.INVISIBLE);
				play.setVisibility(View.VISIBLE);

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
			//	updateSelected();
				resetValues();

			} else if (b.getId() == R.id.home) {
				posessionSide = HOME_SIDE;
				homePosession.setSelected(true);
				awayPosession.setSelected(false);
				//updateSelected();

			} else if (b.getId() == R.id.away) {
				posessionSide = AWAY_SIDE;
				awayPosession.setSelected(true);
				homePosession.setSelected(false);
				//updateSelected();

			}

		} else if (v.getTag().equals("imageButton")) {
			// System.out.println("clicked image button");

			ImageButton b = (ImageButton) v;

			if (b.getId() == R.id.away_refresh) {

				resetCardNum(awayRed);
				resetCardNum(awayYellow);

				currentGame.setAwayRed(0);
				currentGame.setAwayYellow(0);

			} else if (b.getId() == R.id.home_refresh) {

				resetCardNum(homeRed);
				resetCardNum(homeYellow);

				currentGame.setHomeRed(0);
				currentGame.setHomeYellow(0);

			} else if (b.getId() == R.id.pause) {

				lastPause = SystemClock.elapsedRealtime();
				timer.stop();
				pause.setVisibility(View.INVISIBLE);
				play.setVisibility(View.VISIBLE);

			} else if (b.getId() == R.id.play) {

				timer.setBase(timer.getBase() + SystemClock.elapsedRealtime()
						- lastPause);
				timer.start();
				play.setVisibility(View.INVISIBLE);
				pause.setVisibility(View.VISIBLE);
			} else if (b.getId() == R.id.statsDrawerBtn) {
				// UserDrawings.loadFileNames();
				// statsNamesMenu = UserDrawings.getFileNames();

				setGameNames();

				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, statsNamesMenu);
				savedList.setAdapter(adapter);

				savedDrawer.openDrawer(Gravity.RIGHT);
			} else if (b.getId() == R.id.saveStats) {
				saveStats();
			}

		}

	}

	private void increaseCardNum(Button b) {
		int n = Integer.parseInt(b.getText().toString());

		String num = String.valueOf(n + 1);

		b.setText(num);
	}

	private void resetCardNum(Button b) {
		b.setText("0");
	}

	private void increaseNum(TextView t) {
		int n = (Integer.parseInt(t.getText().toString()));

		String num = String.valueOf(n + 1);

		t.setText(num);
	}

	private void decreaseNum(TextView t) {
		int n = (Integer.parseInt(t.getText().toString()));

		if (n > 0) {
			String num = String.valueOf(n - 1);
			t.setText(num);

		}

	}

	private void setValues() {

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
		currentGame.setHomeYellow(Integer.valueOf(awayYellow.getText()
				.toString()));

	}

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

	private int secondsPassed() {

		String time = (String) timer.getText();
		int secPassed = 0;
		int hrs, mins, secs;

		if (time.length() == 5) {
			// System.out.println(timer.getText());
			hrs = 0;
			mins = Integer.parseInt(time.substring(0, 2));
			secs = Integer.parseInt(time.substring(3, 5));
		} else // an hour has passed
		{
			hrs = Integer.parseInt(time.substring(0, 2));
			mins = Integer.parseInt(time.substring(3, 5));
			secs = Integer.parseInt(time.substring(6, 8));
		}

		secPassed = secs + (60 * mins) + (3600 * hrs);
		// System.out.println(secPassed);

		return secPassed;

	}

	private void updatePosession() {
		// total time passed in seconds
		int totalTime = secondsPassed();
		// System.out.println("Total Time:" + totalTime);

		if (totalTime != 0) {
			int timeWithPossession = totalTime - lastPosession;
			// System.out.println("Time with posession:"+ timeWithPossession);

			if (getPossesionSide() == HOME_SIDE)
				homeSec += timeWithPossession;
			else if (getPossesionSide() == AWAY_SIDE)
				awaySec += timeWithPossession;
			else
				return;

			Log.e("msg", getPossesionSide() + "");

			// System.out.println("Homesec:" + homeSec);

			int homePercent = (int) Math
					.round(((double) homeSec / (double) totalTime) * 100);
			// System.out.println("homepercent:" + homePercent);
			int awayPercent = (int) Math
					.round(((double) awaySec / (double) totalTime) * 100);
			// System.out.println("awaypercent:" + awayPercent);

			lastPosession = totalTime;

			this.homePercent.setText(homePercent + "%");
			this.awayPercent.setText(awayPercent + "%");

			System.out.println(this.awayPercent.getText().toString() + " "
					+ this.homePercent.getText().toString());
			
		}
	}

	private int getPossesionSide() {
		return posessionSide;
	}

	private void setGameNames() {
		statsNamesMenu = new ArrayList<String>();

		for (int i = 0; i < UserStatistics.getGames().size(); i++) {
			statsNamesMenu.add(UserStatistics.getGames().get(i).getName());
		}

	}

	@Override
	public void onChronometerTick(Chronometer chronometer) {
		// TODO Auto-generated method stub
		updatePosession();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle args = new Bundle();
		args.putString("Menu", statsNamesMenu.get(position - 1));
		Fragment detail = new DrawerFragment();
		detail.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.TableLayout01, detail)
				.commit();
	}

	private void saveStats() {
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
						UserStatistics.saveGame(currentGame,currentGame.getName(), StatisticsActivity.this);
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

//	private void updateSelected() {
//		if (homePosession.isSelected())
//			homePosession.setBackgroundResource(R.drawable.rounded_corner_btn);
//		else
//			homePosession.setBackgroundColor(Color.argb(43, 0, 0, 0));
//
//		if (awayPosession.isSelected())
//			awayPosession.setBackgroundResource(R.drawable.rounded_corner_btn);
//
//		else
//			awayPosession.setBackgroundColor(Color.argb(43, 0, 0, 0));
//
//	}
}
