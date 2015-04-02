package com.example.coachme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.content.ClipData;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;


public class FormationsActivity extends Activity implements OnClickListener{

	private DrawingView drawView;
	private ImageButton draw, eraser, refresh;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.formations);
		
		drawView = (DrawingView)findViewById(R.id.drawing);
		
		eraser = (ImageButton)findViewById(R.id.erase);
		eraser.setOnClickListener(this);
		
		draw = (ImageButton)findViewById(R.id.draw);
		draw.setOnClickListener(this);
		
		refresh = (ImageButton)findViewById(R.id.refresh);
		refresh.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view)
	{
		//what to do when clicked
		if(view.getId()==R.id.draw){
		    //draw button clicked
			drawView.setErase(false); 
		}
		else if(view.getId()==R.id.erase){
		    //switch to eraser
			drawView.setErase(true); 

		}
		else if(view.getId()==R.id.refresh){
			AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
			newDialog.setTitle("New Formation");
			newDialog.setMessage("Start new formation? (you will lose the current progress)");
			newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        drawView.clearCanvas();
			        drawView.setErase(false);
			        dialog.dismiss();
			    }
			});
			newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			    public void onClick(DialogInterface dialog, int which){
			        dialog.cancel();
			    }
			});
			newDialog.show();
	
		}
		
		
	
		
	}
	
	
}
