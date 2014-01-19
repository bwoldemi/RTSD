package com.example.rtds;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import android.widget.FrameLayout;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class RTDSActivity extends Activity {
	private Camera mCamera;
	private CameraPreview mPreview;
	private SensorManager smgr;
	private Target target;
	
	
	//private MediaPlayer mediaPlayer;
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rtds);

		mCamera = getCameraInstance();

		// Camera Preview
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		
		// Targets 
		target=new Target(this);
		
		FrameLayout trargetFrameLayout = (FrameLayout) findViewById(R.id.target);
		trargetFrameLayout.addView(target);
		// sensor to get device orientation  
		smgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		smgr.registerListener(eventListener,
				smgr.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		//setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rtd, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera();
		smgr.unregisterListener(eventListener);
	}

	/**
	 * Releasing Camera
	 */

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

	public void releaseCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
	SensorEventListener eventListener = new SensorEventListener() {
		@SuppressWarnings("deprecation")
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				
				float[] value= event.values;
				float x=value[0];				
				if((x>0 && x<90)||(x>180 && x<270) ){
					target.setFirst(true);
					//target.setSecond(false);
					target.invalidate();
				}else{
					target.setFirst(false);
					//target.setSecond(false);
					target.invalidate();
				}
				//Log.v("X ",Float.toString(value[0]));
				
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
		}
	};
	

}
