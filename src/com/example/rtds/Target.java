package com.example.rtds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.graphics.Paint;
import android.graphics.Color;

public class Target extends View {
	// to manipulate the target
	private boolean first;
	private boolean displayTarget;
	private boolean isMediaSnap = true;
	private boolean isGameNotOver; // no more grow in size
	private boolean shoot;
	private String gameOver = "Game Over !!";
	// to draw target
	private Bitmap bitmap;
	// to draw the dead target
	private Bitmap bitmapDeadBug;
	// paint
	Paint paint;

	// bug sound media player
	public MediaPlayer mediaPlayer;
	public MediaPlayer mediaPlayerTwo;
	public MediaPlayer mediaSnap;
	// coordinates of traingle
	private int xCord;
	private int yCord;
	private int lenX;
	private int lenY;

	// Rectangle for scaling target drawing
	private Rect rect;

	// counter
	private int counter;

	public Target(Context context) {
		super(context);

		mediaPlayer = MediaPlayer.create(context, R.raw.mosquito_fly_by);
		mediaPlayerTwo = MediaPlayer.create(context, R.raw.sound2);
		mediaSnap = MediaPlayer.create(context, R.raw.snap);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.index);
		bitmapDeadBug = BitmapFactory.decodeResource(getResources(),
				R.drawable.images);
		// TODO Auto-generated constructor stub

		xCord = getHeight() / 2;
		yCord = getWidth() / 2;
		lenX = bitmap.getWidth() / 10;
		lenY = bitmap.getHeight() / 10;
		rect = new Rect();
		counter = 0;
		first = false;
		displayTarget = true;
		shoot = false;
		isGameNotOver = true;

		paint = new Paint();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isFirst() == true && displayTarget == true) {
			rect.set(xCord, yCord, lenX + counter, lenY + counter);
			canvas.drawBitmap(bitmap, null, rect, null);
			
			if(!isGameNotOver){
				paint.setColor(Color.WHITE);
				paint.setTextSize(100);
				canvas.drawText(gameOver, getWidth() / 2-getWidth()/5, getHeight() / 2,paint);
			}
				
			// if the game over it won't grow in size
			if (isGameNotOver) {
				counter = counter + 50;
				mediaPlayer.start();
			}

			if (counter == 1000 && isGameNotOver) {
				mediaPlayerTwo.start();
				// counter = 0;
				isGameNotOver = false;
			}
		}else{
			if(!isFirst()&& isGameNotOver&&displayTarget){
				paint.setColor(Color.WHITE);
				paint.setTextSize(70);
				canvas.drawText("Please Move Your Device", 10, getHeight() / 2, paint);
			}
			
		}
		if (shoot == true && isGameNotOver) {

			canvas.drawBitmap(bitmapDeadBug, null, rect, null);
			if (isMediaSnap) {
				mediaSnap.start();
				isMediaSnap = false;
			}

			// shoot=false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		shoot = true;
		if (isGameNotOver)
			displayTarget = false;
		Log.v("OnTouchEvent", "Target hit");
		return super.onTouchEvent(event);

	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

}
