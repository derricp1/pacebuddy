package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public final static String DELAY = "derricp1.apps.MESSAGE";
	public final static String PERIOD = "derricp1.apps.MESSAGE2";
	public final static String MAX_SPEED = "derricp1.apps.MESSAGE3";
	public final static String MIN_SPEED = "derricp1.apps.MESSAGE4";
	
	int delay = 15;
	int period = 15;
	int max_speed = 5;
	int min_speed = 3;

	int QUIT_ALL = 0;
	
	View myView;
	SeekBar delay_bar;
	SeekBar period_bar;
	SeekBar max_speed_bar;
	SeekBar min_speed_bar;
	
	TextView delay_text;
	TextView period_text;
	TextView max_speed_text;
	TextView min_speed_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 myView = this.findViewById(android.R.id.content); //gets view
		 
		 delay_text = (TextView) findViewById(R.id.DelayText);
		 period_text = (TextView) findViewById(R.id.PeriodText);
		 max_speed_text = (TextView) findViewById(R.id.MaxSpeedText);
		 min_speed_text = (TextView) findViewById(R.id.MinSpeedText);
		 
		 delay_bar = (SeekBar) findViewById(R.id.DelayBar);
		 delay_bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	            public void onStopTrackingTouch(SeekBar seekBar) {

	            }
	            public void onStartTrackingTouch(SeekBar seekBar) {

	            }
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					if (arg2) {
						delay = (int) Math.floor(arg1);
						arg0.setProgress(delay);
						delay_text.setText("Delay: " + delay + " seconds");
					}
					
				}			
			});
		 
		 period_bar = (SeekBar) findViewById(R.id.PeriodBar);
		 period_bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	            public void onStopTrackingTouch(SeekBar seekBar) {

	            }
	            public void onStartTrackingTouch(SeekBar seekBar) {

	            }
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					if (arg2) {
						period = Math.max(1,(int) Math.floor(arg1));
						arg0.setProgress(period);
						period_text.setText("Period: " + period + " seconds");
					}
					
				}			
			});
		 
		 max_speed_bar = (SeekBar) findViewById(R.id.MaxSpeedBar);
		 max_speed_bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	            public void onStopTrackingTouch(SeekBar seekBar) {

	            }
	            public void onStartTrackingTouch(SeekBar seekBar) {

	            }
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					if (arg2) {
						max_speed = Math.max(1,(int) Math.floor(arg1));
						arg0.setProgress(max_speed);
						max_speed_text.setText("Max Speed: " + max_speed + " MPH");
					}
					
				}			
			});
		 
		 min_speed_bar = (SeekBar) findViewById(R.id.MinSpeedBar);
		 min_speed_bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	            public void onStopTrackingTouch(SeekBar seekBar) {

	            }
	            public void onStartTrackingTouch(SeekBar seekBar) {

	            }
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					if (arg2) {
						min_speed = Math.max(1,(int) Math.floor(arg1));
						arg0.setProgress(min_speed);
						min_speed_text.setText("Min Speed: " + min_speed + " MPH");
					}
					
				}			
			});
		 
	}
	
	public void BeginRun(View view) {
		
		if (max_speed < min_speed) {
			Context c = getApplicationContext();
			Toast toast = new Toast(c);
			toast.setDuration(Toast.LENGTH_LONG);
			toast = Toast.makeText(c,"Max can't be lower than min.",Toast.LENGTH_LONG);
			toast.show();
		}
		else {

			Intent i = new Intent(this, RunActivity.class);
			
			i.putExtra(DELAY, delay);
			i.putExtra(PERIOD, period);
			i.putExtra(MAX_SPEED, max_speed);
			i.putExtra(MIN_SPEED, min_speed);
			
			startActivityForResult(i,QUIT_ALL);
		
		}
	}

	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
	        finish();
	        System.exit(0);			
		}
		else {
			delay_bar.setProgress(delay);
			period_bar.setProgress(period);
			max_speed_bar.setProgress(max_speed);
			min_speed_bar.setProgress(min_speed);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void Goto_Settings(View view) {
		
	}
	
	@SuppressWarnings("deprecation")
	public void Goto_Quit(View view) { //quit
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Quit?");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
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
	        case R.id.action_about:
	        	return true;
	        case R.id.action_quit:
	        	Goto_Quit(this.findViewById(android.R.id.content));
	    }
	    return true;
	}

}
