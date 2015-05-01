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

public class DrawingView extends View {

	// drawing path
	private Path drawPath;
	// drawing and canvas paint
	private Paint drawPaint, canvasPaint;
	// initial color
	private int paintColor = 0xFF660000;
	// canvas
	private Canvas drawCanvas;
	// canvas bitmap
	private Bitmap canvasBitmap;
	private boolean erase = false;

	private final int MARKER_STROKE_WIDTH = 10;
	private final int ERASER_STROKE_WIDTH = 25;

	public void setErase(boolean isErase) {
		// set erase true or false
		erase = isErase;
		if (erase)
			drawPaint
					.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		else
			drawPaint.setXfermode(null);

		if (isErase == true) {
			drawPaint.setStrokeWidth(ERASER_STROKE_WIDTH);
		} else
			drawPaint.setStrokeWidth(MARKER_STROKE_WIDTH);
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupDrawing();

	}

	private void setupDrawing() {
		// get drawing area setup for interaction

		drawPath = new Path();
		drawPaint = new Paint();

		// set paint colour
		drawPaint.setColor(paintColor);
		// setproperties
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(MARKER_STROKE_WIDTH);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);

		// set up drawing
		canvasPaint = new Paint(Paint.DITHER_FLAG);

	}

	public void clearCanvas() {
		drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
		invalidate();
	}

	// make custom view function as a drawing view
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// view given size
		super.onSizeChanged(w, h, oldw, oldh);

		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}

	// allowclass to function as a custom drawing view
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}

	// facilitate drawing
	// get user touches to create drawing
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// detect user touch
		float touchX = event.getX();
		float touchY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			drawPath.moveTo(touchX, touchY);
			break;
		case MotionEvent.ACTION_MOVE:
			drawPath.lineTo(touchX, touchY);
			break;
		case MotionEvent.ACTION_UP:
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

}
