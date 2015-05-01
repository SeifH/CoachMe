package com.example.coachme;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;

public class DirectoryActivity extends Activity implements OnClickListener{

	private Button newPlayer,sendEmail;
	
	private String playerName, playerEmail;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.directory);
		
		newPlayer = (Button) findViewById(R.id.newPlayer);
        newPlayer.setOnClickListener(this);
        
        sendEmail = (Button) findViewById(R.id.sendEmail);
        sendEmail.setOnClickListener(this);
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
		
		if (b.getId()==R.id.newPlayer){
			
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			
			alert.setTitle("New Player");
			
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			
			final EditText inputName = new EditText(this);
			inputName.setHint("Player's name");
			layout.addView(inputName);
			
			final EditText inputEmail = new EditText(this);
			inputEmail.setHint("Player's email");
			layout.addView(inputEmail);
			
			alert.setView(layout);
			
//			alert.setMessage("Player's information: ");
//			
//			//edit text for user input
//			final EditText input = new EditText(this);
//			alert.setView(input);
			
			alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					playerName = inputName.getText().toString();
					playerEmail = inputEmail.getText().toString();
					
				}
			});
			
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//cancelled
					
				}
			});
			
			alert.show();
			
			
		}
		
	}
	
	public void sendEmail()
	{
//		Alert Dialog?
//		
//		Intent i = new Intent(Intent.ACTION_SEND);
//		i.setType("message/rfc822");
//		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"email"});
//		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
//		try {
//		    startActivity(Intent.createChooser(i, "Send mail..."));
//		} catch (android.content.ActivityNotFoundException ex) {
//		    Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//		}
	}

}
