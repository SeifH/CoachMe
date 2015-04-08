package com.example.coachme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;

public class FormationsActivity extends Activity implements OnClickListener {

	private DrawingView drawView;
	private ImageButton draw, eraser, refresh, ball;
	private static final String BALL_TAG = "Soccer Ball";
	private int ballDropNum;
//	private LayoutParams layoutParams;
//	   private android.widget.RelativeLayout.LayoutParams layoutParams;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.formations);
		
		ballDropNum = 0;

		drawView = (DrawingView) findViewById(R.id.drawing);

		eraser = (ImageButton) findViewById(R.id.erase);
		eraser.setOnClickListener(this);

		draw = (ImageButton) findViewById(R.id.draw);
		draw.setOnClickListener(this);

		refresh = (ImageButton) findViewById(R.id.refresh);
		refresh.setOnClickListener(this);

		ball = (ImageButton) findViewById(R.id.ball);
		// set the tag
		ball.setTag(BALL_TAG);
		ball.setOnLongClickListener(new LongClickListener());

		findViewById(R.id.RelativeLayout01).setOnDragListener(
				new DragListener());
		findViewById(R.id.FrameLayout1).setOnDragListener(new DragListener());

	}

	@Override
	public void onClick(View view) {
		// what to do when clicked
		if (view.getId() == R.id.draw) {
			// draw button clicked
			drawView.setErase(false);
		} else if (view.getId() == R.id.erase) {
			// switch to eraser
			drawView.setErase(true);

		} else if (view.getId() == R.id.refresh) {
			AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
			newDialog.setTitle("New Formation");
			newDialog
					.setMessage("Start new formation? (you will lose the current progress)");
			newDialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							drawView.clearCanvas();
							drawView.setErase(false);
							dialog.dismiss();
						}
					});
			newDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			newDialog.show();

		} // else if (view.getId() == R.id.ball) {
			// // create it from the tag
			// ClipData.Item item = new ClipData.Item((CharSequence)
			// view.getTag());
			// String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			// ClipData data = new ClipData(view.getTag().toString(), mimeTypes,
			// item);
			//
			// DragShadowBuilder shadowBuilder = new
			// View.DragShadowBuilder(view);
			// view.startDrag(data, // data to be dragged
			// shadowBuilder, // drag shadow
			// view, // local data about the drag and drop operation
			// 0 // no needed flags
			// );
			//
			// view.setVisibility(View.INVISIBLE);
			// // return true;
			//
			// }

	}

	class DragListener implements OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			// TODO Auto-generated method stub
			// Handles each of the expected events

			final int action = event.getAction();
			switch (action) {

			// signal for the start of a drag and drop operation.
			case DragEvent.ACTION_DRAG_STARTED:

				break;

			// the drag point has entered the bounding box of the View
			case DragEvent.ACTION_DRAG_ENTERED:

				break;

			// the user has moved the drag shadow outside the bounding box of
			// the
			// View
			case DragEvent.ACTION_DRAG_EXITED:
				 
				break;

			case DragEvent.ACTION_DRAG_LOCATION:

			break;

			// drag shadow has been released,the drag point is within the
			// bounding
			// box of the View
			case DragEvent.ACTION_DROP:
//				// get location coordinates of touch
				float x_cord = event.getX();
				float y_cord = event.getY();

				if (v == findViewById(R.id.FrameLayout1)) {
					View view = (View) event.getLocalState();
				View view2 = (View) (findViewById(R.id.FrameLayout1));
					ViewGroup viewgroup = (ViewGroup) view.getParent();
					viewgroup.removeView(view);
					
							
					FrameLayout containView = (FrameLayout) v;
					containView.addView(view);		
//					Log.d("msg", "" + view.getWidth() + " " + view.getHeight()  );
//					Log.d("event3", "" + event.getX() + " " + event.getY()   );
//					float x = ball.getX();
//					float y = ball.getY();
//					Log.d("event", "" + x_cord + " " + y_cord  );
//					Log.d("event2", "" + x + " " + y  );
//					Log.d("view", "" + view2.getWidth() + " " + view2.getHeight()  );


					view.setX(adjustXCoord(x_cord) - (view.getWidth() / 2));
					view.setY(y_cord - (view2.getHeight() / 2));

					view.setVisibility(View.VISIBLE);

				} else {
					View view = (View) event.getLocalState();
					view.setVisibility(View.VISIBLE);
					Context context = getApplicationContext();
					Toast.makeText(context, "You can't drop the image here",
							Toast.LENGTH_LONG).show();
					break;
				}
				break;

			// the drag and drop operation has concluded.
			case DragEvent.ACTION_DRAG_ENDED:
				break;

			default:
				break;
			}

			return true;
		}

	}
	
	private float adjustXCoord (float x){
		
		// get screen dimensions
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		if (ballDropNum == 0){
			float adjustedValue = (size.x/800)*163;
			ballDropNum++;
			return x + adjustedValue;
		}else
			return x;
		
		
	}

	private final class LongClickListener implements OnLongClickListener {

		// called when the item is long-clicked
		@Override
		public boolean onLongClick(View view) {
			// TODO Auto-generated method stub

			// create it from the object's tag
			ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

			String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData data = new ClipData(view.getTag().toString(), mimeTypes,
					item);
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

			// Initiates the drag shadow builder
			view.startDrag(data, // data to be dragged
					shadowBuilder, // drag shadow
					view, // local data about the drag and drop operation
					0 // no needed flags
			);

			//view.setVisibility(View.INVISIBLE);
			return true;
		}
	}

}
