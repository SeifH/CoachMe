package com.example.coachme;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Context;
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

public class MainActivity extends Activity implements OnClickListener{

	private Button formationsButton, helpButton, directoryButton, statisticsButton, settingsButton ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		
        formationsButton = (Button) findViewById(R.id.formations);
        formationsButton.setOnClickListener(this);

        statisticsButton = (Button) findViewById(R.id.game_statistics);
        statisticsButton.setOnClickListener(this);
        
        directoryButton = (Button) findViewById(R.id.player_directory);
        directoryButton.setOnClickListener(this);
        
        
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
				Button b = (Button)v;
				
				if (b.getId()==R.id.formations){

					Intent intent = new Intent(this, FormationsActivity.class);
					startActivity(intent);
				}else if (b.getId()==R.id.game_statistics){
					
					Intent intent = new Intent(this,StatisticsActivity.class);
					startActivity(intent);
					
				}else if (b.getId()==R.id.player_directory){
					
					Intent intent = new Intent(this,DirectoryActivity.class);
					startActivity(intent);
					
				}


			}
			
			

}
