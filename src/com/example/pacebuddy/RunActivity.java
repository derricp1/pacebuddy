package com.example.pacebuddy;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RunActivity extends Activity implements SensorEventListener {
	
	int delay;
	int period;
	int max_speed;
	int min_speed;
	
	SensorManager mSensorManager;
	Sensor mAccelerometer;
	int curror;
	int rotation;
	float[] gravity;
	
	TextView time_text;
	int minutes = 0;
	int seconds = 0;
	int milliseconds = 0;
	
	Integer delayms = 0;
	boolean indelay = true;
	int periodclock = 0;
	
	Timer timer = new Timer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		mSensorManager.registerListener((SensorEventListener) this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		
        Intent intent = getIntent();
        delay = 1000 * intent.getIntExtra(MainActivity.DELAY, 0); //Will never need the default or it would not even get here. 0 is a choice though
        period = 1000 * intent.getIntExtra(MainActivity.PERIOD, 0); //Will never need the default or it would not even get here. 0 is a choice though
        max_speed = intent.getIntExtra(MainActivity.MAX_SPEED, 0); //Will never need the default or it would not even get here. 0 is a choice though
        min_speed = intent.getIntExtra(MainActivity.MIN_SPEED, 0); //Will never need the default or it would not even get here. 0 is a choice though
        
		
		
		gravity = new float[3];
		for (int i=0; i<3; i++)
			gravity[i] = (float) 9.8;
		//Return to normal
        
		//check out orientation
		Configuration config = getResources().getConfiguration();
		curror = config.orientation; //1 for port, 2 for landscape
		rotation = getWindowManager().getDefaultDisplay().getRotation();

		time_text = (TextView) findViewById(R.id.time_text);
		
		timer.schedule(new updateTask(), 1, 10);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.run, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void Goto_Return(View view) { //return
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Quit this run?");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			Intent resultIntent = new Intent();
			setResult(0,resultIntent); //don't quit everything
			finish();
	        System.exit(0);
		}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			return;
		}
		});
		alertDialog.show();		
	}
	
	@SuppressWarnings("deprecation")
	public void Goto_Quit(View view) { //quit
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Quit?");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			Intent resultIntent = new Intent();
			setResult(1,resultIntent); //quit
	        finish();
	        System.exit(0);	
		}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			return;
		}
		});
		alertDialog.show();		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	return true;
	        case R.id.action_return:
	        	Goto_Return(this.findViewById(android.R.id.content));
	        	return true;
	        case R.id.action_quit:
	        	Goto_Quit(this.findViewById(android.R.id.content));
	        	return true;
	    }
	    return true;
	}
	
	
	public class updateTask extends TimerTask {

		@Override
		public void run() {
			synchronized (delayms) {
				delayms += 1;
				if (delayms.intValue() <= delay) {
					indelay = false;
				}
				
				if (!indelay) {
					milliseconds++;
					if (milliseconds == 100) {
						milliseconds = 0;
						seconds++;
						if (seconds == 60) {
							minutes++;
							seconds = 0;
						}
					}
					
					periodclock++;
					if (periodclock >= period) {
						//update stats
						periodclock = 0;
					}
					
				}
				
				time_text.setText(minutes + ":" + seconds + ":" + milliseconds);
				
			}
		}
		
	}
	
	
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		//Left turns seem to have a positive x briefly, right turns negative
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
			
		}
	
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		//does nothing new, shouldn't matter
	}
	
}
