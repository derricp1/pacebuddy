package com.example.pacebuddy;

import java.text.DecimalFormat;
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
	
	int delay = 0;
	int period = 0;
	int max_speed = 0;
	int min_speed = 0;
	
	SensorManager mSensorManager;
	Sensor mAccelerometer;
	int curror;
	int rotation;
	float[] gravity = {(float)9.8,(float)9.8,(float)9.8};
	final float alpha = (float) 0.8;
	float[] linearacceleration = {0,0,0};
	
	DecimalFormat format = new DecimalFormat("00");
	
	TextView time_text;
	int minutes = 0;
	int seconds = 0;
	int centiseconds = 0;
	
	float distance = 0;
	float curraccel = 0;
	float pastaccel = 0;
	int lastchange = 0; //was last change positive or negative
	
	int delayms = 0;
	boolean indelay = true;
	int periodclock = 0;
	
	TextView[] views = new TextView[4];
	
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
        
		//check out orientation
		Configuration config = getResources().getConfiguration();
		curror = config.orientation; //1 for port, 2 for landscape
		rotation = getWindowManager().getDefaultDisplay().getRotation();

		time_text = (TextView) findViewById(R.id.time_text);
		
		views[0] = (TextView) findViewById(R.id.textView1);
		views[1] = (TextView) findViewById(R.id.textView2);
		views[2] = (TextView) findViewById(R.id.textView3);
		views[3] = (TextView) findViewById(R.id.textView4);

		timer.schedule(new updateTask(), 0, 20);
		
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
				delayms += 20;
				if (delayms >= delay) {
					indelay = false;
				}
				
				if (indelay == false) {
					centiseconds += 2;
					if (centiseconds >= 100) {
						centiseconds = 0;
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
				
				runOnUiThread(new timerUp());

		}
		
	}
	
	public class timerUp implements Runnable {
		
		public void run() {
			if (indelay == false) {
				time_text.setText(minutes + ":" + format.format(seconds) + ":" + format.format(centiseconds));
			}
			else {
				time_text.setText("IN DELAY PERIOD");
			}
	          
	        views[0].setText(Float.toString(linearacceleration[0]));
	        views[1].setText(Float.toString(linearacceleration[1]));
	        views[2].setText(Float.toString(linearacceleration[2]));
	        views[3].setText(Float.toString(distance));
		}
		
	}
	
	
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		//Left turns seem to have a positive x briefly, right turns negative
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER && indelay == false){

	          gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
	          gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
	          gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

	          linearacceleration[0] = event.values[0] - gravity[0];
	          linearacceleration[1] = event.values[1] - gravity[1];
	          linearacceleration[2] = event.values[2] - gravity[2];
	          
	          //work with distances
	          pastaccel = curraccel;
	          curraccel = linearacceleration[2];
	          
	          if (pastaccel > curraccel && lastchange == 1) { //was increasing, now slowing
	        	  if (Math.abs(pastaccel) > 1) {
	        		  distance += (2 + Math.pow(Math.abs(pastaccel),1.3));
	        	  }
	          }
	          if (pastaccel < curraccel && lastchange == -1) { //was slowing, now increasing
	        	  if (Math.abs(pastaccel) > 1) {
	        		  distance += (2 + Math.pow(Math.abs(pastaccel),1.3));
	        	  }	        	  
	          }
	          
	          lastchange = 0;
	          if (pastaccel > curraccel) {
	        	  lastchange = -1;
	          }
	          if (pastaccel < curraccel) {
	        	  lastchange = 1;
	          }
	          
		}
	
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		//does nothing new, shouldn't matter
	}
	
}
