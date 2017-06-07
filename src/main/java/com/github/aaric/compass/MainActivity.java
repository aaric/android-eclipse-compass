package com.github.aaric.compass;

import android.app.Activity;
import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Main Activity.
 * 
 * @author Aaric
 *
 */
public class MainActivity extends Activity implements SensorEventListener {
	
	private ImageView mImageView;
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private float startAngle = 0;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_compass);
		mImageView = (ImageView) this.findViewById(R.id.iv_compass);
		mImageView.startAnimation(animation);
		
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(this);
		super.onPause();
	}

	/**
	 * Inflate the menu; this adds items to the action bar if it is present.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(com.github.aaric.compass.R.menu.main, menu);
		return true;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float[] values = event.values;
		float angle = values[0];
		RotateAnimation animation = new RotateAnimation(startAngle, angle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1000);
		mImageView.startAnimation(animation);
		startAngle = -angle;
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Nothing.
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_about:
				new AlertDialog.Builder(this)
					.setTitle(R.string.action_about)
					.setIcon(R.drawable.ic_launcher)
					.setMessage(R.string.dialog_about_message)
					.show();
				break;
			case R.id.action_exit:
				Process.killProcess(Process.myPid());
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
