package com.example.coachme;

import java.util.ArrayList;

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
import android.widget.Button;
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

public class FormationsActivity extends Activity implements OnClickListener {

	private DrawingView drawView;
	private ImageButton draw, eraser, refresh, ball, bPlayer, rPlayer;

	// keep track of number of drags for each button
	private int ballDragNum, rPlayerDragNum, bPlayerDragNum;
	private ArrayList<ImageButton> imageDuplicates;
	private ArrayList<ImageButton> imageDisable;
	private String dragTag;

	// constant tags for each button that is being dragged
	private static final String BALL_TAG = "Soccer Ball";
	private static final String RED_PLAYER_TAG = "Red Player";
	private static final String BLACK_PLAYER_TAG = "Black Player";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Go fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.formations);

		ballDragNum = 0;
		rPlayerDragNum = 0;
		bPlayerDragNum = 0;

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

		bPlayer = (ImageButton) findViewById(R.id.bPlayer);
		// set the tag
		bPlayer.setTag(BLACK_PLAYER_TAG);
		bPlayer.setOnLongClickListener(new LongClickListener());

		rPlayer = (ImageButton) findViewById(R.id.rPlayer);
		// set the tag
		rPlayer.setTag(RED_PLAYER_TAG);
		rPlayer.setOnLongClickListener(new LongClickListener());

		findViewById(R.id.RelativeLayout01).setOnDragListener(
				new DragListener());
		findViewById(R.id.FrameLayout1).setOnDragListener(new DragListener());

		imageDuplicates = new ArrayList<ImageButton>();
		imageDisable = new ArrayList<ImageButton>();

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
							resetDrawingField ();
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

		}

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
			// the View
			case DragEvent.ACTION_DRAG_EXITED:

				break;

			case DragEvent.ACTION_DRAG_LOCATION:

				break;

			// drag shadow has been released,the drag point is within the
			// bounding box of the View
			case DragEvent.ACTION_DROP:

				// get location coordinates of touch
				float x_cord = event.getX();
				float y_cord = event.getY();

				if (v == findViewById(R.id.FrameLayout1)) {
					// Reassign View to ViewGroup
					View view = (View) event.getLocalState();
					View view2 = (View) (findViewById(R.id.FrameLayout1));
					// ViewGroup viewgroup = (ViewGroup) view.getParent();
					// viewgroup.removeView(view);
					FrameLayout containView = (FrameLayout) v;

					// Create a copy of the ImageButton (Duplicate)
					ImageButton oldImgButton = (ImageButton) view;
					ImageButton newImgButton = new ImageButton(
							getApplicationContext());
					newImgButton.setImageDrawable(oldImgButton.getDrawable());
					newImgButton
							.setOnLongClickListener(new LongClickListener());
					newImgButton.setTag("Duplicate");
					newImgButton.getBackground().setAlpha(0);

					containView.addView(newImgButton);
					imageDuplicates.add(newImgButton);
					// containView.addView(view);

					// set the coordinates of the new Image Button
//					newImgButton.setX(x_cord - (view.getWidth() / 2));
//					newImgButton.setY(y_cord - (view.getHeight() / 2)+25);

					// set the width and height of the new Image Button
					Drawable d = (oldImgButton.getDrawable());
					Rect dimensions = d.getBounds();
					int height = dimensions.height();
					int width = dimensions.width();
					FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
							new LayoutParams(width, height));
					newImgButton.setLayoutParams(params);
					
					newImgButton.setX(x_cord - (view.getWidth() / 2));
					if (oldImgButton.getTag().equals("Duplicate")){
						newImgButton.setY(y_cord - (view.getHeight() / 2));
					}else
					newImgButton.setY(y_cord - (view.getHeight() / 2)+15);

					// newView.setY(y_cord - (view2.getWidth() / 2));
					// newView.setY(y_cord - (view2.getHeight() / 2));

					view.setVisibility(View.VISIBLE);

					// Check if drag and drop does not require a copy
					if (oldImgButton.getTag().toString().equals("Duplicate")) {
						oldImgButton.setVisibility(View.INVISIBLE);
					} else if (oldImgButton.getTag().toString()
							.equals(BALL_TAG)) {
						ballDragNum++;
						finalDrop(oldImgButton);

					} else if (oldImgButton.getTag().toString()
							.equals(RED_PLAYER_TAG)) {
						rPlayerDragNum++;
						
						if (rPlayerDragNum == 11) 
							finalDrop(oldImgButton);
						
					} else if (oldImgButton.getTag().toString()
							.equals(BLACK_PLAYER_TAG)) {
						bPlayerDragNum++;
						
						if (bPlayerDragNum == 11)
							finalDrop(oldImgButton);
					}

					newImgButton.setAlpha(255);

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

	private float adjustXCoord(float x) {

		// get screen dimensions
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		if (dragTag.equals(BALL_TAG)) {
			if (ballDragNum == 0) {
				float adjustedValue = (size.x / 800) * 145;
				ballDragNum++;
				return x + adjustedValue;
			}
		} else if (dragTag.equals(RED_PLAYER_TAG)) {
			if (rPlayerDragNum == 0) {
				float adjustedValue = (size.x / 800) * 80;
				rPlayerDragNum++;
				return x + adjustedValue;
			}
		} else if (dragTag.equals(BLACK_PLAYER_TAG)) {
			if (bPlayerDragNum == 0) {
				float adjustedValue = (size.x / 800) * 25;
				bPlayerDragNum++;
				return x + adjustedValue;
			}
		}

		return x;

	}

	private final class LongClickListener implements OnLongClickListener {

		// called when the item is long-clicked
		@Override
		public boolean onLongClick(View view) {
			// TODO Auto-generated method stub

			// get tag of dragged item
			dragTag = view.getTag().toString();

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

			return true;
		}
	}
	
	private void resetDrawingField (){
		drawView.clearCanvas();
		drawView.setErase(false);
		
		for (ImageButton btn : imageDuplicates) {
			btn.setVisibility(View.INVISIBLE);
		}
		
		for (ImageButton btn : imageDisable) {
			btn.setEnabled(true);
			float alpha = 1f;
			AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
			alphaUp.setFillAfter(true);
			btn.startAnimation(alphaUp);
		}
		
		imageDisable.clear();
		imageDuplicates.clear();
		
		ballDragNum = 0;
		rPlayerDragNum = 0;
		bPlayerDragNum = 0;
	}
	
	private void finalDrop (ImageButton ImgButton){
		
		ImgButton.setEnabled(false);
		imageDisable.add(ImgButton);

		float alpha = 0.15f;
		AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
		alphaUp.setFillAfter(true);
		ImgButton.startAnimation(alphaUp);
	}


}
