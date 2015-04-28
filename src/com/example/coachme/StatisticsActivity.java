package com.example.coachme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class StatisticsActivity extends Activity implements OnClickListener {

	private Button newGameButton, homePosession, awayPosession, homeRed,
			awayShotsMinus, homeGoalsMinus, awayGoalsMinus, homeYellow,
			awayRed, awayYellow, awayGoalsPlus, homeShotsPlus, homeGoalsPlus,
			homeShotsMinus, awayShotsPlus;
	private ImageButton save, pause, awayRefresh, homeRefresh, play;
	private Chronometer timer;
	private TextView homeShots, awayShots, homeGoals, awayGoals, homePercent,
			awayPercent;
	private long lastPause;
	private int lastPosession; // seconds passed at last possession
	private int homeSec, awaySec; // total number of seconds with possession

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

		newGameButton = (Button) findViewById(R.id.newGame);
		newGameButton.setOnClickListener(this);

		save = (ImageButton) findViewById(R.id.save);
		save.setOnClickListener(this);

		homePosession = (Button) findViewById(R.id.home);
		homePosession.setOnClickListener(this);

		awayPosession = (Button) findViewById(R.id.away);
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
		timer.setBase(SystemClock.elapsedRealtime());
		lastPause = SystemClock.elapsedRealtime();

		pause = (ImageButton) findViewById(R.id.pause);
		pause.setOnClickListener(this);

		play = (ImageButton) findViewById(R.id.play);
		play.setOnClickListener(this);
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

			} else if (b.getId() == R.id.home) {

				// total time passed in seconds
				int totalTime = secondsPassed();
//				System.out.println("Total Time:" + totalTime);

				if (totalTime != 0) {
					int timeWithPossession = totalTime - lastPosession;
//					System.out.println("Time with posession:"+ timeWithPossession);

					homeSec += timeWithPossession;
//					System.out.println("Homesec:" + homeSec);

					int homePercent = (int) Math
							.round(((double) homeSec / (double) totalTime) * 100);
//					System.out.println("homepercent:" + homePercent);
					int awayPercent = (int) Math
							.round(((double) awaySec / (double) totalTime) * 100);
//					System.out.println("awaypercent:" + awayPercent);

					lastPosession = totalTime;
//					System.out.println("Last possession:" + lastPosession);

					this.homePercent.setText(homePercent + "%");
					this.awayPercent.setText(awayPercent + "%");
				}
			} else if (b.getId() == R.id.away) {
				// total time passed in seconds
				int totalTime = secondsPassed();
				if (totalTime != 0) {
//					System.out.println("Total Time:" + totalTime);

					int timeWithPossession = totalTime - lastPosession;
//					System.out.println("Time with posession:"+ timeWithPossession);

					awaySec += timeWithPossession;

//					System.out.println("Awaysec:" + awaySec);

					int homePercent = (int) Math
							.round(((double) homeSec / (double) totalTime) * 100);
//					System.out.println("homepercent:" + homePercent);
					int awayPercent = (int) Math
							.round(((double) awaySec / (double) totalTime) * 100);
//					System.out.println("awaypercent:" + awayPercent);

					lastPosession = totalTime;
//					System.out.println("Last possession:" + lastPosession);

					this.homePercent.setText(homePercent + "%");
					this.awayPercent.setText(awayPercent + "%");
				}

			}

		} else if (v.getTag().equals("imageButton")) {
			// System.out.println("clicked image button");

			ImageButton b = (ImageButton) v;

			if (b.getId() == R.id.away_refresh) {

				resetCardNum(awayRed);
				resetCardNum(awayYellow);
			} else if (b.getId() == R.id.home_refresh) {

				resetCardNum(homeRed);
				resetCardNum(homeYellow);
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

	private int secondsPassed() {

		// System.out.println(timer.getText());//format 00:00 or
		// 00:00:00

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

//		System.out.println("Hours:" + hrs);
//		System.out.println("Mins:" + mins);
//		System.out.println("Secs:" + secs);

		secPassed = secs + (60 * mins) + (3600 * hrs);
//		System.out.println(secPassed);

		return secPassed;

	}

}
