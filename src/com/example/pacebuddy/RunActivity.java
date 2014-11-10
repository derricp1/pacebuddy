package com.example.pacebuddy;

import java.util.Arrays;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RunActivity extends Activity implements SensorEventListener {
	
	public final static String PERIOD_MESSAGE = "derricp1.apps.RMESSAGE";
	public final static String PERIOD_DISTANCE_MESSAGE = "derricp1.apps.RMESSAGE2";
	public final static String LAP_MESSAGE = "derricp1.apps.RMESSAGE3";
	public final static String LAP_TIME_MESSAGE = "derricp1.apps.RMESSAGE4";
	public final static String LAP_DISTANCE_MESSAGE = "derricp1.apps.RMESSAGE5";
	public final static String TIME_MESSAGE = "derricp1.apps.RMESSAGE6";
	public final static String PERIOD_TIME_MESSAGE = "derricp1.apps.RMESSAGE7";
	public final static String SPEEDS_MESSAGE = "derricp1.apps.RMESSAGE8";
	public final static String COLOR_MESSAGE = "derricp1.apps.RMESSAGE9";
	
	//strings for state saving
	public final static String SAVE_AUTOSTOP = "SAVE_AUTOSTOP";
	public final static String SAVE_AUTOSTOPTIMER = "SAVE_AUTOSTOPTIMER";
	public final static String SAVE_LAP_TIMES = "SAVE_LAP_TIMES";
	public final static String SAVE_PERIOD_DISTANCES = "SAVE_PERIOD_DISTANCES";
	public final static String SAVE_LAP_DISTANCES = "SAVE_LAP_DISTANCES";
	public final static String SAVE_DELAY = "SAVE_DELAY";
	public final static String SAVE_PERIOD = "SAVE_PERIOD";
	public final static String SAVE_MAX_SPEED = "SAVE_MAX_SPEED";
	public final static String SAVE_MIN_SPEED = "SAVE_MIN_SPEED";
	public final static String SAVE_LAPS = "SAVE_LAPS";
	public final static String SAVE_LAPCLOCK = "SAVE_LAPCLOCK";
	public final static String SAVE_LAPDISTANCE = "SAVE_LAPDISTANCE";
	public final static String SAVE_LAP_LIMIT = "SAVE_LAP_LIMIT";
	public final static String SAVE_PERIODS = "SAVE_PERIODS";
	public final static String SAVE_PERIOD_LIMIT = "SAVE_PERIOD_LIMIT";
	public final static String SAVE_PERIODDISTANCE = "SAVE_PERIODDISTANCE";
	public final static String SAVE_MINUTES = "SAVE_MINUTES";
	public final static String SAVE_SECONDS = "SAVE_SECONDS";
	public final static String SAVE_CENTISECONDS = "SAVE_CENTISECONDS";
	public final static String SAVE_DISTANCE = "SAVE_DISTANCE";
	public final static String SAVE_CURRACCEL = "SAVE_CURRACCEL";
	public final static String SAVE_PASTACCEL = "SAVE_PASTACCEL";
	public final static String SAVE_LASTCHANGE = "SAVE_LASTCHANGE";
	public final static String SAVE_DELAYMS = "SAVE_DELAYMS";
	public final static String SAVE_INDELAY = "SAVE_INDELAY";
	public final static String SAVE_PERIODCLOCK = "SAVE_PERIODCLOCK";
	public final static String SAVE_TICKS = "SAVE_TICKS";
	public final static String SAVE_ABLE = "SAVE_ABLE";
	public final static String SAVE_MAX_TIMES = "SAVE_MAX_TIMES";
	public final static String SAVE_MIN_TIMES = "SAVE_MIN_TIMES";
	public final static String SAVE_REACHED_MIN = "SAVE_REACHED_MIN";
	public final static String SAVE_LAST_PERIOD_SPEED = "SAVE_LAST_PERIOD_SPEED";
	public final static String SAVE_NUM_MAX_TIMES = "SAVE_NUM_MAX_TIMES";
	public final static String SAVE_NUM_MIN_TIMES = "SAVE_NUM_MIN_TIMES";
	public final static String SAVE_COLOR = "SAVE_COLOR";
	public final static String SAVE_TIMEOUT = "SAVE_TIMEOUT";
	public final static String SAVE_MARGINDISTANCE = "SAVE_MARGINDISTANCE";
	public final static String SAVE_MARGINTIME = "SAVE_MARGINTIME";
	
	public final static String TIME_1 = "derricp1.apps.MESSAGET1";
	public final static String TIME_2 = "derricp1.apps.MESSAGET2";
	public final static String TIME_3 = "derricp1.apps.MESSAGET3";
	public final static String SPEED_1 = "derricp1.apps.MESSAGES1";
	public final static String SPEED_2 = "derricp1.apps.MESSAGES2";
	public final static String SPEED_3 = "derricp1.apps.MESSAGES3";
	
	int QUIT_ALL = 0;
	
	int color;
	
	final int MS = 20;
	final float QUOTA = (float) 1.28; //minimum accel to count
	final int NEXT_TICK = 8;
	
	int ticks = 0;
	boolean able = true;
	
	int delay;
	int period;
	int max_speed;
	int min_speed;
	
	int timeout;
	
	SensorManager mSensorManager;
	Sensor mAccelerometer;
	int curror;
	int rotation;
	float[] gravity = {(float)9.8,(float)9.8,(float)9.8};
	final float alpha = (float) 0.8;
	float[] linearacceleration = {0,0,0};
	
	float this_period_speed;
	
	DecimalFormat format = new DecimalFormat("00");
	DecimalFormat format2 = new DecimalFormat("0.000");
	
	TextView time_text;
	int minutes;
	int seconds;
	int centiseconds;
	
	float distance;
	float curraccel;
	float pastaccel;
	int lastchange; //was last change positive or negative
	
	int delayms;
	boolean indelay = true;
	int periodclock;
	
	TextView tempview;
	
	Timer timer = new Timer();
	
	Button lap_button;

	//data recording
	
	float[] period_distances;
	int periods;
	int period_limit;
	float perioddistance;
	
	float[] lap_times;
	float[] lap_distances;
	int laps;
	int lapclock;
	float lapdistance;
	int lap_limit;
	
	int autostop;
	int autostoptimer;
	
	SharedPreferences sharedPref;
	
	boolean canstart = false;
	
	//timing stuff
	TextView dist_text; //uses distance
	
	float[] margindistances;
	int margintime;
	TextView margintext;
	//maxes out at 5 s

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run);
		
		sharedPref = getApplicationContext().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
		
		canstart = false;
		
		//handle returning from a state
		if (savedInstanceState != null) {
			lap_times = savedInstanceState.getFloatArray(SAVE_LAP_TIMES);
			period_distances = savedInstanceState.getFloatArray(SAVE_PERIOD_DISTANCES);
			lap_distances = savedInstanceState.getFloatArray(SAVE_LAP_DISTANCES);
			delay = savedInstanceState.getInt(SAVE_DELAY);
			period = savedInstanceState.getInt(SAVE_PERIOD);
			max_speed = savedInstanceState.getInt(SAVE_MAX_SPEED);
			min_speed = savedInstanceState.getInt(SAVE_MIN_SPEED);
			lapclock = savedInstanceState.getInt(SAVE_LAPCLOCK);
			lapdistance = savedInstanceState.getFloat(SAVE_LAPDISTANCE);
			lap_limit = savedInstanceState.getInt(SAVE_LAP_LIMIT);
			periods = savedInstanceState.getInt(SAVE_PERIODS);
			perioddistance = savedInstanceState.getFloat(SAVE_PERIODDISTANCE);
			period_limit = savedInstanceState.getInt(SAVE_PERIOD_LIMIT);
			minutes = savedInstanceState.getInt(SAVE_MINUTES);
			seconds = savedInstanceState.getInt(SAVE_SECONDS);
			centiseconds = savedInstanceState.getInt(SAVE_CENTISECONDS);
			distance = savedInstanceState.getFloat(SAVE_DISTANCE);
			curraccel = savedInstanceState.getFloat(SAVE_CURRACCEL);
			pastaccel = savedInstanceState.getFloat(SAVE_PASTACCEL);
			lastchange = savedInstanceState.getInt(SAVE_LASTCHANGE);
			delayms = savedInstanceState.getInt(SAVE_DELAYMS);
			indelay = savedInstanceState.getBoolean(SAVE_INDELAY);
			periodclock = savedInstanceState.getInt(SAVE_PERIODCLOCK);	
			ticks = savedInstanceState.getInt(SAVE_TICKS);
			able = savedInstanceState.getBoolean(SAVE_ABLE);
			laps = savedInstanceState.getInt(SAVE_LAPS);
			autostop = savedInstanceState.getInt(SAVE_AUTOSTOP);
			autostoptimer = savedInstanceState.getInt(SAVE_AUTOSTOPTIMER);
			color = savedInstanceState.getInt(SAVE_COLOR);
			color = savedInstanceState.getInt(SAVE_TIMEOUT);
			margindistances = savedInstanceState.getFloatArray(SAVE_MARGINDISTANCE);
			margintime = savedInstanceState.getInt(SAVE_MARGINTIME);
		}
		else {
			lap_times = new float[5];
			lap_distances = new float[5];
			period_distances = new float[5];	
	        Intent intent = getIntent();
	        delay = 1000 * intent.getIntExtra(MainActivity.DELAY, 0); //Will never need the default or it would not even get here. 0 is a choice though
	        period = 1000 * intent.getIntExtra(MainActivity.PERIOD, 0); //Will never need the default or it would not even get here. 0 is a choice though
	        max_speed = intent.getIntExtra(MainActivity.MAX_SPEED, 0); //Will never need the default or it would not even get here. 0 is a choice though
	        min_speed = intent.getIntExtra(MainActivity.MIN_SPEED, 0); //Will never need the default or it would not even get here. 0 is a choice though
	    	laps = 0;
	    	lapclock = 0;
	    	lapdistance = 0;
	    	lap_limit = 5;
	    	periods = 0;
	    	period_limit = 5;
	    	perioddistance = 0;
	    	
	    	timeout = 60000 * intent.getIntExtra(MainActivity.TIMEOUT, 0);
	    	
	    	minutes = 0;
	    	seconds = 0;
	    	centiseconds = 0;
	    	
	    	distance = 0;
	    	curraccel = 0;
	    	pastaccel = 0;
	    	lastchange = 0; //was last change positive or negative
	    	
	    	delayms = 0;
	    	indelay = true;
	    	periodclock = 0;
	    	
	    	ticks = 0;
	    	able = true;
	    	
	    	autostop = 1000 * intent.getIntExtra(MainActivity.AUTOSTOP, 0);
	    	autostoptimer = 0;
	    	color = intent.getIntExtra(MainActivity.COLOR, 0);
	    	
	    	margindistances = new float[250];
	    	for (int i=0;i<25;i++)
	    		margindistances[i] = 0;
	    	
	    	margintime = 0;
	    	
		}
		
    	if (timeout == 0) {
    		timeout = -999;
    	}
		
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //Also need to be able to recover info on destroy and recreate

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		mSensorManager.registerListener((SensorEventListener) this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		        
		//check out orientation
		Configuration config = getResources().getConfiguration();
		curror = config.orientation; //1 for port, 2 for landscape
		rotation = getWindowManager().getDefaultDisplay().getRotation();

		time_text = (TextView) findViewById(R.id.time_text);

		tempview = (TextView) findViewById(R.id.textView4);
		lap_button = (Button) findViewById(R.id.lap_button);
		
		canstart = true;
		
		dist_text = (TextView) findViewById(R.id.dist_text);
		margintext = (TextView) findViewById(R.id.margin_text);
		
		timer.schedule(new updateTask(), 0, MS); //should be last
		
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
		alertDialog.setTitle("Cancel this run?");
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
			delayms += MS;
			if (delayms > delay) {
				indelay = false;
			}
			
			if (indelay == false) {
				
				autostoptimer += MS;
				
				if (timeout > -999) {
					timeout -= MS;
					if (timeout <= 0) {
						autostopper(); //this one
					}
				}
				
				if (autostop > 0 && autostoptimer > autostop) { //0 = off
					autostopper();
				}
				
				if (ticks > 0)
					ticks--;
				
				centiseconds += 2;
				if (centiseconds >= 100) {
					centiseconds = 0;
					seconds++;
					if (seconds == 60) {
						minutes++;
						seconds = 0;
					}
				}
				
				//margin
				if (margintime < 5000) {
					margintime += MS;
				}
				else {
					for(int q=1;q<250;q++) {
						margindistances[q-1] = margindistances[q];
					}
					margindistances[249] = 0;
				}
					
				periodclock += MS;
				if (periodclock >= period) {
					//update stats
					
					this_period_speed = (perioddistance/5280)/(period/(1000*3600)) /*period*/; //needs to be calculated
					
					period_distances[periods] = perioddistance;
					perioddistance = 0;
					
					periodclock = 0;
					periods++;
					
					if (periods == period_limit) {
						period_limit *= 2;
						period_distances = Arrays.copyOf(period_distances, period_limit); 
					}
					
				}
				
				lapclock += MS;
				
			}
			
			runOnUiThread(new timerUp());

		}
		
	}
	
	public class timerUp implements Runnable {
		
		public void run() {
			if (indelay == false) {
				time_text.setText(minutes + ":" + format.format(seconds) + ":" + format.format(centiseconds));
		        tempview.setText(Float.toString(distance));
		        dist_text.setText(format2.format((float) distance/(float)5280) + " Miles");
				
				//set margintext
				float marginsum = 0;
				for (int q=0;q<250;q++) {
					marginsum += margindistances[q];
				}
				if (margintime > 0) {
					margintext.setText(format2.format(((float)marginsum/(float)5280)/((float)margintime/(float)(1000*3600))) + " MPH");
				}
				else {
					margintext.setText("0.000 MPH");					
				}
				
			}
			else {
				time_text.setText("IN DELAY PERIOD");
			}

	        //tempview.setText(Float.toString(distance));
		}
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		//Left turns seem to have a positive x briefly, right turns negative
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER && indelay == false && ticks == 0){

	          gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
	          gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
	          gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

	          linearacceleration[0] = event.values[0] - gravity[0];
	          linearacceleration[1] = event.values[1] - gravity[1];
	          linearacceleration[2] = event.values[2] - gravity[2];
	          
	          //work with distances
	          pastaccel = curraccel;
	          curraccel = linearacceleration[2];
	          
	          if (able == true) {
		          if (pastaccel > curraccel && lastchange == 1) { //was increasing, now slowing
		        	  if (Math.abs(pastaccel) > QUOTA) {
		        		  float d = getExtraDist(pastaccel);
		        		  addDistance(d);
		        	  }
		          }
		          if (pastaccel < curraccel && lastchange == -1) { //was slowing, now increasing
		        	  if (Math.abs(pastaccel) > QUOTA) {
		        		  float d = getExtraDist(pastaccel);
		        		  addDistance(d);
		        	  }	        	  
		          }
	          }
	          else {
	        	  if (Math.abs(curraccel) < QUOTA/2) {
	        		  able = true;
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
	
	public void addDistance(float d) {
		  autostoptimer = 0;
		  ticks = NEXT_TICK;
		  distance += d;
		  lapdistance += d;
		  perioddistance += d;
		  margindistances[(int) Math.max(0,(Math.floor(margintime/20) - 1))] += d;
	}
	
	public int compressTime() {
		return (centiseconds + 100 * seconds + 6000 * minutes);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		//does nothing new, shouldn't matter
	}
	
	public float getExtraDist(float pa) {
		return (float) (0.5 + Math.pow(Math.abs(pa),0.93));
	}
	
	public void register_lap(View view) {
		if (!indelay) { //should only operate when moving in proper time
			lap_times[laps] = lapclock; //seconds from MS
			lap_distances[laps] = lapdistance;
			
			laps++;
			lapclock = 0;
			lapdistance = 0;
			
			if (laps == lap_limit) {
				lap_limit *= 2;
				lap_times = Arrays.copyOf(lap_times,lap_limit);
				lap_distances = Arrays.copyOf(lap_distances,lap_limit);
			}
		}
	}
	
	public void finish_run(View view) { //advance to results
		
		//add the final period and lap
		if (!indelay) { //should only operate when moving in proper time		
			autostopper();
		}
		else {
			Goto_Return(view);
		}
		
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
	        Intent resultIntent = new Intent();
	        setResult(1,resultIntent); //quit everything
	        finish();
	        //System.exit(0);			
		}
		else {
			Intent resultIntent = new Intent();
			setResult(0,resultIntent); //don't quit everything
			finish();
	        //System.exit(0);
		}
	}
	
	public void checkRecords() {
		
		float[] records = {sharedPref.getFloat("SPEED_1", 0),sharedPref.getFloat("SPEED_2", 0),sharedPref.getFloat("SPEED_3", 0)};
		int[] times = {sharedPref.getInt("TIME_1", 0),sharedPref.getInt("TIME_2", 0),sharedPref.getInt("TIME_3", 0)};
	
		for (int i=0;i<3;i++) {
			if (centiseconds + 100 * seconds + 6000 * minutes > 6000 * times[i]) {
				float sum = 0;
				for (int q=0; q<periods; q++) {
					sum += period_distances[q];
				}
				if ((sum/5280)/(((float) (compressTime())/(float) (3600*1000))) > records[i]) {
					records[i] = sum; //mph
				}
			}
		}
		
		Editor editor = sharedPref.edit();
		editor.putFloat(SPEED_1, records[0]);
		editor.putFloat(SPEED_2, records[1]);
		editor.putFloat(SPEED_3, records[2]);
		editor.commit();
	}
	
	public void autostopper() {
		lap_times[laps] = lapclock; //seconds from MS
		lap_distances[laps] = lapdistance;
		
		laps++;
		lapclock = 0;
		lapdistance = 0;
		
		if (laps == lap_limit) {
			lap_limit *= 2;
			lap_times = Arrays.copyOf(lap_times,lap_limit);
			lap_distances = Arrays.copyOf(lap_distances,lap_limit);
		}
		
		period_distances[periods] = perioddistance;
		perioddistance = 0;
		
		periodclock = 0;
		periods++;
		
		if (periods == period_limit) {
			period_limit *= 2;
			period_distances = Arrays.copyOf(period_distances, period_limit); 
		}	
		
		checkRecords();
		
		Intent i = new Intent(this, ResultsActivity.class);
		
		i.putExtra(PERIOD_MESSAGE, periods);
		i.putExtra(PERIOD_DISTANCE_MESSAGE, period_distances);
		i.putExtra(LAP_MESSAGE, laps);
		i.putExtra(LAP_TIME_MESSAGE, lap_times);
		i.putExtra(LAP_DISTANCE_MESSAGE, lap_distances);
		i.putExtra(TIME_MESSAGE, (centiseconds + 100 * seconds + 6000 * minutes));
		i.putExtra(PERIOD_TIME_MESSAGE, period);
		i.putExtra(COLOR_MESSAGE, color);
		
		int[] speeds = {max_speed,min_speed};
		i.putExtra(SPEEDS_MESSAGE, speeds);
		
		timer.cancel();

		startActivityForResult(i,QUIT_ALL);
	}
	
	public void onStop() {
		
		super.onStop();
		
		timer.cancel();
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) { //this is important, rotation happens

	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putFloatArray(SAVE_LAP_TIMES, lap_times);
		savedInstanceState.putFloatArray(SAVE_PERIOD_DISTANCES, period_distances);
		savedInstanceState.putFloatArray(SAVE_LAP_DISTANCES, lap_distances);
		savedInstanceState.putInt(SAVE_DELAY, delay);
		savedInstanceState.putInt(SAVE_PERIOD, period);
		savedInstanceState.putInt(SAVE_MAX_SPEED, max_speed);
		savedInstanceState.putInt(SAVE_MIN_SPEED, min_speed);
		savedInstanceState.putInt(SAVE_LAPCLOCK, lapclock);
		savedInstanceState.putFloat(SAVE_LAPDISTANCE, lapdistance);
		savedInstanceState.putInt(SAVE_LAP_LIMIT, lap_limit);
		savedInstanceState.putInt(SAVE_PERIODS, periods);
		savedInstanceState.putFloat(SAVE_PERIODDISTANCE, perioddistance);
		savedInstanceState.putInt(SAVE_PERIOD_LIMIT, period_limit);
		savedInstanceState.putInt(SAVE_MINUTES, minutes);
		savedInstanceState.putInt(SAVE_SECONDS, seconds);
		savedInstanceState.putInt(SAVE_CENTISECONDS, centiseconds);
		savedInstanceState.putFloat(SAVE_DISTANCE, distance);
		savedInstanceState.putFloat(SAVE_CURRACCEL, curraccel);
		savedInstanceState.putFloat(SAVE_PASTACCEL, pastaccel);
		savedInstanceState.putInt(SAVE_LASTCHANGE, lastchange);
		savedInstanceState.putInt(SAVE_DELAYMS, delayms);
		savedInstanceState.putBoolean(SAVE_INDELAY, indelay);
		savedInstanceState.putInt(SAVE_PERIODCLOCK, periodclock);	
		savedInstanceState.putBoolean(SAVE_ABLE, able);
		savedInstanceState.putInt(SAVE_LAPS, laps);
		savedInstanceState.putInt(SAVE_AUTOSTOP, autostop);
		savedInstanceState.putInt(SAVE_AUTOSTOPTIMER, autostoptimer);
		savedInstanceState.putInt(SAVE_COLOR, color);
		savedInstanceState.putInt(SAVE_TIMEOUT, timeout);
		savedInstanceState.putInt(SAVE_MARGINTIME, margintime);
		savedInstanceState.putFloatArray(SAVE_MARGINDISTANCE, margindistances);
	}
	
}
