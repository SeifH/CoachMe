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
			homeYellow, awayRed, awayYellow;
	private ImageButton save, pause, homeShotsPlus, homeShotsMinus,
			awayShotsPlus, awayShotsMinus, homeGoalsPlus, homeGoalsMinus,
			awayGoalsPlus, awayGoalsMinus, awayRefresh, homeRefresh, play;
	private Chronometer timer;
	private TextView homeShots, awayShots, homeGoals, awayGoals, homePercent,
			awayPercent;
	private long lastPause;
	private int lastPosession; //minutes passed at last possession

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.statistics);
		
		lastPosession = 0;

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

		homeShotsPlus = (ImageButton) findViewById(R.id.homeShotsPlus);
		homeShotsPlus.setOnClickListener(this);

		homeShotsMinus = (ImageButton) findViewById(R.id.homeShotsMinus);
		homeShotsMinus.setOnClickListener(this);

		awayShotsPlus = (ImageButton) findViewById(R.id.awayShotsPlus);
		awayShotsPlus.setOnClickListener(this);

		awayShotsMinus = (ImageButton) findViewById(R.id.awayShotsMinus);
		awayShotsMinus.setOnClickListener(this);

		homeGoalsPlus = (ImageButton) findViewById(R.id.homeGoalsPlus);
		homeGoalsPlus.setOnClickListener(this);

		homeGoalsMinus = (ImageButton) findViewById(R.id.homeGoalsMinus);
		homeGoalsMinus.setOnClickListener(this);

		awayGoalsPlus = (ImageButton) findViewById(R.id.awayGoalsPlus);
		awayGoalsPlus.setOnClickListener(this);

		awayGoalsMinus = (ImageButton) findViewById(R.id.awayGoalsMinus);
		awayGoalsMinus.setOnClickListener(this);

		awayRefresh = (ImageButton) findViewById(R.id.away_refresh);
		awayRefresh.setOnClickListener(this);

		homeRefresh = (ImageButton) findViewById(R.id.home_refresh);
		homeRefresh.setOnClickListener(this);

		homePercent= (TextView) findViewById(R.id.home_percent);
		awayPercent= (TextView) findViewById(R.id.away_percent);
		
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
				
				//System.out.println(timer.getText());//format 00:00 or 00:00:00
				
				String time = (String) timer.getText();
				
				if(time.length()==5)
				{
					//System.out.println(timer.getText());
					int hrs = 0;
					int mins = Integer.parseInt(time.substring(0, 1));
					int secs = Integer.parseInt(time.substring(3, 4));
				}
				else //an hour has passed
				{
					int hrs = Integer.parseInt(time.substring(0, 1));
					int mins = Integer.parseInt(time.substring(3, 4));
					int secs = Integer.parseInt(time.substring(6, 7));
				}
				
				

			} else if (b.getId() == R.id.away) {

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

}
