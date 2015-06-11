package com.example.coachme;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Creates a custom drawing view for the drawing functionality
 * 
 * Adapted from http://code.tutsplus.com/tutorials/android-sdk-create-
 * a-drawing-app-essential-functionality--mobile-19328
 * 
 * @author Seif Hassan & Olivia Perryman
 * @since Tuesday, April 28 2015
 */
public class DrawingView extends View {

	// drawing path
	private Path drawPath;
	// drawing and canvas paint
	private Paint drawPaint, canvasPaint;
	// marker paint color
	private int paintColor = 0xFF660000;
	// drawing canvas
	private Canvas drawCanvas;
	// canvas bitmap
	private Bitmap canvasBitmap;
	// switches between eraser and marker
	private boolean erase = false;

	// constants for stroke width of marker and eraser
	private final int MARKER_STROKE_WIDTH = 10;
	private final int ERASER_STROKE_WIDTH = 25;

	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 */
	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupDrawing();

	}

	/**
	 * Enables and disables the eraser functionality
	 * 
	 * @param isErase
	 *            if the eraser is selected
	 */
	public void setErase(boolean isErase) {
		// set erase true or false
		erase = isErase;

		// checks if eraser is selected
		if (erase) {
			// eraser selected

			// create xfermode to paint
			drawPaint
					.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		} else {
			// eraser not selected, clear any previous xfermode
			drawPaint.setXfermode(null);
		}

		// check is eraser is selected
		if (isErase == true) {
			// set the stroke width of the eraser
			drawPaint.setStrokeWidth(ERASER_STROKE_WIDTH);
		} else {
			// change to stroke width of the marker
			drawPaint.setStrokeWidth(MARKER_STROKE_WIDTH);
		}
	}

	/**
	 * Setup custom drawing view
	 */
	private void setupDrawing() {
		
		// get drawing area setup for interaction
		drawPath = new Path();
		drawPaint = new Paint();

		// set paint colour
		drawPaint.setColor(paintColor);
		
		// set properties
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(MARKER_STROKE_WIDTH);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);

		// set up drawing, initiate canvas
		canvasPaint = new Paint(Paint.DITHER_FLAG);

	}

	/**
	 * Clears the canvas of any drawing
	 */
	public void clearCanvas() {
		//clear canvas and invalidate
		drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
		invalidate();
	}

	/**
	 * Called when view is assigned a size
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// drawing view is given size
		super.onSizeChanged(w, h, oldw, oldh);

		//initiate drawing canvas and bitmaps with given parameters
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}

	/**
	 * This method allows the class to function as a drawing
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//draw the canvas bitmap and the path
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}

	/**
	 * Get the user's touches to draw the path
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// detect user touch
		//get coordinates of user touch
		float touchX = event.getX();
		float touchY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//user pressed on screen
			drawPath.moveTo(touchX, touchY);
			break;
		case MotionEvent.ACTION_MOVE:
			//user moved touch
			drawPath.lineTo(touchX, touchY);
			break;
		case MotionEvent.ACTION_UP:
			//user released touch, draw path
			drawCanvas.drawPath(drawPath, drawPaint);
			drawPath.reset();
			break;
		default:
			return false;

		}

		// invalidate to execute onDraw
		invalidate();
		return true;

	}

	/**
	 * Prevents the user from drawing on the canvas
	 */
	public void disableDrawing() {
		//makes paint color invisible
		drawPaint.setColor(Color.TRANSPARENT);
	}

	/**
	 * Allows the user to draw in the canvas
	 */
	public void enableDrawing() {
		//sets the paint color
		drawPaint.setColor(paintColor);
	}

}//end class
