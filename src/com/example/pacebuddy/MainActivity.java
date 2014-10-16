package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	final String DELAY = null;
	final String PERIOD = null;
	final String MAX_SPEED = null;
	final String MIN_SPEED = null;
	
	int delay;
	int period;
	int max_speed;
	int min_speed;
	
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
						period = (int) Math.floor(arg1);
						arg0.setProgress(period);
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
						max_speed = (int) Math.floor(arg1);
						arg0.setProgress(max_speed);
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
						min_speed = (int) Math.floor(arg1);
						arg0.setProgress(min_speed);
					}
					
				}			
			});
		 
		 delay_text = (TextView) findViewById(R.id.DelayText);
		 period_text = (TextView) findViewById(R.id.PeriodText);
		 max_speed_text = (TextView) findViewById(R.id.MaxSpeedText);
		 min_speed_text = (TextView) findViewById(R.id.MinSpeedText);
		 
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
			
			startActivity(i);
		
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
	
	public void Goto_Return(View view) {
        //finish();
        //System.exit(0);		
	}
	
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
	        	//this.findViewById(android.R.id.content);
	        case R.id.action_return:

	        case R.id.action_quit:
	        	Goto_Quit(this.findViewById(android.R.id.content));
	    }
	    return true;
	}

}
