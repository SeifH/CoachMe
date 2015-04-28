package com.example.coachme;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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
	private ImageButton draw, eraser, refresh, ball, bPlayer, rPlayer, save;

	// keep track of number of drags for each button
	private int ballDragNum, rPlayerDragNum, bPlayerDragNum;
	private int helpNum;
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
		helpNum = 0;

		drawView = (DrawingView) findViewById(R.id.drawing);

		eraser = (ImageButton) findViewById(R.id.erase);
		eraser.setOnClickListener(this);

		draw = (ImageButton) findViewById(R.id.draw);
		draw.setOnClickListener(this);

		refresh = (ImageButton) findViewById(R.id.refresh);
		refresh.setOnClickListener(this);

		save = (ImageButton) findViewById(R.id.save);
		save.setOnClickListener(this);

		ball = (ImageButton) findViewById(R.id.ball);
		// set the tag
		ball.setTag(BALL_TAG);
		ball.setOnTouchListener(new TouchListener());
		// ball.setOnLongClickListener(new LongClickListener());

		bPlayer = (ImageButton) findViewById(R.id.bPlayer);
		// set the tag
		bPlayer.setTag(BLACK_PLAYER_TAG);
		bPlayer.setOnTouchListener(new TouchListener());
		// bPlayer.setOnLongClickListener(new LongClickListener());

		rPlayer = (ImageButton) findViewById(R.id.rPlayer);
		// set the tag
		rPlayer.setTag(RED_PLAYER_TAG);
		rPlayer.setOnTouchListener(new TouchListener());
		// rPlayer.setOnLongClickListener(new LongClickListener());

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
		} else if (view.getId() == R.id.save) {
			// save drawing
			saveDrawing();
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
							resetDrawingField();
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
					// newImgButton
					// .setOnLongClickListener(new LongClickListener());
					newImgButton.setOnTouchListener(new TouchListener());
					newImgButton.setTag("Duplicate");
					newImgButton.getBackground().setAlpha(0);

					containView.addView(newImgButton);
					imageDuplicates.add(newImgButton);
					// containView.addView(view);

					// set the coordinates of the new Image Button
					// newImgButton.setX(x_cord - (view.getWidth() / 2));
					// newImgButton.setY(y_cord - (view.getHeight() / 2)+25);

					// set the width and height of the new Image Button
					Drawable d = (oldImgButton.getDrawable());
					Rect dimensions = d.getBounds();
					int height = dimensions.height();
					int width = dimensions.width();
					FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
							new LayoutParams(width, height));
					newImgButton.setLayoutParams(params);

					newImgButton.setX(x_cord - (view.getWidth() / 2));
					if (oldImgButton.getTag().equals("Duplicate")) {
						newImgButton.setY(y_cord - (view.getHeight() / 2));
					} else
						newImgButton.setY(y_cord - (view.getHeight() / 2) + 15);

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

	/**
	 * TouchListener will handle touch events on draggable views
	 *
	 */
	private final class TouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {

			ImageButton btn = (ImageButton) view;

			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				// TODO Auto-generated method stub
				if (!btn.getTag().equals("Duplicate") && helpNum == 0) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Drag onto field", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
					toast.show();
					helpNum++;

				}

				// get tag of dragged item
				dragTag = view.getTag().toString();

				// create it from the object's tag
				ClipData.Item item = new ClipData.Item(
						(CharSequence) view.getTag());

				// clip data passes data as part of drag
				String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
				ClipData data = new ClipData(view.getTag().toString(),
						mimeTypes, item);
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
						view);

				// Start the drag shadow builder
				view.startDrag(data, // data to be dragged
						shadowBuilder, // drag shadow
						view, // local data about the drag and drop operation
						0 // no needed flags
				);

				return true;
			} else {
				return false;
			}
		}
	}

	private void resetDrawingField() {
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

	private void finalDrop(ImageButton ImgButton) {

		ImgButton.setEnabled(false);
		imageDisable.add(ImgButton);

		float alpha = 0.15f;
		AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
		alphaUp.setFillAfter(true);
		ImgButton.startAnimation(alphaUp);
	}

	private void saveDrawing() {

		AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
		saveDialog.setTitle("Save Formation");
		saveDialog.setMessage("Save formation to device?");

		// Setup an EditText view to get user input
		final EditText input = new EditText(this);
		input.setText("Name");

		saveDialog.setView(input);
		saveDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which)

					{
						
						if (UserDrawings.fileExists(input.getText().toString())){
							// user feedback - save success
							Toast HII = Toast.makeText(
									getApplicationContext(),
									"exists!",
									Toast.LENGTH_SHORT);
							HII.show();
						}
						
						// save drawing
						FrameLayout saveView = (FrameLayout) findViewById(R.id.FrameLayout1);
						saveView.setDrawingCacheEnabled(true);
						saveView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

						Bitmap bitmap = saveView.getDrawingCache();

						// attempt to save
						if (UserDrawings.saveToFile(input.getText().toString(),
								bitmap)) {
							// user feedback - save success
							Toast savedToast = Toast.makeText(
									getApplicationContext(),
									"Formation saved!",
									Toast.LENGTH_SHORT);
							savedToast.show();
						} else {

							// user feedback - save failed
							Toast unsavedToast = Toast.makeText(
									getApplicationContext(),
									"Failed to save!",
									Toast.LENGTH_SHORT);
							unsavedToast.show();
						}

						saveView.destroyDrawingCache();

					}

				});
		saveDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		saveDialog.show();
	}

}
