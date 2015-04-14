package com.example.coachme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class StatisticsActivity extends Activity implements OnClickListener {

	private Button newGameButton, homePosession, awayPosession, homeRed,
			homeYellow, awayRed, awayYellow;
	private ImageButton save, pause, homeShotsPlus, homeShotsMinus,
			awayShotsPlus, awayShotsMinus, homeGoalsPlus, homeGoalsMinus,
			awayGoalsPlus, awayGoalsMinus;
	private Chronometer timer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.statistics);

		newGameButton = (Button) findViewById(R.id.newGame);
		newGameButton.setOnClickListener(this);

		// save = (ImageButton) findViewById(R.id.save);
		// save.setOnClickListener(this);

		homeRed = (Button) findViewById(R.id.home_redcard);
		homeRed.setOnClickListener(this);
		
		homeYellow = (Button) findViewById(R.id.home_yellowcard);
		homeYellow.setOnClickListener(this);
		
		awayRed = (Button) findViewById(R.id.away_redcard);
		awayRed.setOnClickListener(this);
		
		awayYellow = (Button) findViewById(R.id.away_yellowcard);
		awayYellow.setOnClickListener(this);
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
		Button b = (Button) v;

		if (b.getId() == R.id.home_redcard) {

			increaseNum(b);
		} else if (b.getId() == R.id.home_yellowcard) {

			increaseNum(b);
		} else if (b.getId() == R.id.away_yellowcard) {

			increaseNum(b);
		} else if (b.getId() == R.id.away_redcard) {

			increaseNum(b);
		}

	}

	private void increaseNum(Button b) {
		int n = Integer.parseInt(b.getText().toString());

		String num = String.valueOf(n + 1);

		b.setText(num);
	}

}
