package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public final static String DELAY = "derricp1.apps.MESSAGE";
	public final static String PERIOD = "derricp1.apps.MESSAGE2";
	public final static String MAX_SPEED = "derricp1.apps.MESSAGE3";
	public final static String MIN_SPEED = "derricp1.apps.MESSAGE4";
	public final static String AUTOSTOP = "derricp1.apps.MESSAGE5";
	public final static String COLOR = "derricp1.apps.MESSAGE6";
	public final static String TIME_1 = "derricp1.apps.MESSAGET1";
	public final static String TIME_2 = "derricp1.apps.MESSAGET2";
	public final static String TIME_3 = "derricp1.apps.MESSAGET3";
	public final static String SPEED_1 = "derricp1.apps.MESSAGES1";
	public final static String SPEED_2 = "derricp1.apps.MESSAGES2";
	public final static String SPEED_3 = "derricp1.apps.MESSAGES3";
	public final static String TIMEOUT = "derricp1.apps.MESSAGE7";
	
	public final static String SAVE_DELAY = "SAVE_DELAY";
	public final static String SAVE_PERIOD = "SAVE_PERIOD";
	public final static String SAVE_MAX_SPEED = "SAVE_MAX_SPEED";
	public final static String SAVE_MIN_SPEED = "SAVE_MIN_SPEED";
	public final static String SAVE_AUTOSTOP = "SAVE_AUTOSTOP";
	public final static String SAVE_COLOR = "SAVE_COLOR";
	public final static String SAVE_TIMEOUT = "SAVE_TIMEOUT";
	
	int delay;
	int period;
	int max_speed;
	int min_speed;
	int color;
	int timeout;

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
	
	SharedPreferences sharedPref;
	
	int autostop;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
		 
		sharedPref = getApplicationContext().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
		 
		 if (savedInstanceState != null) {
			delay = savedInstanceState.getInt(SAVE_DELAY, 0);
			period = savedInstanceState.getInt(SAVE_PERIOD, 0);
			max_speed = savedInstanceState.getInt(SAVE_MAX_SPEED, 0);
			min_speed = savedInstanceState.getInt(SAVE_MIN_SPEED, 0);
			autostop = savedInstanceState.getInt(SAVE_AUTOSTOP, 0);
			color = savedInstanceState.getInt(SAVE_COLOR, 0);
			timeout = savedInstanceState.getInt(SAVE_TIMEOUT, 0);
		 }
		 else {
			
			delay = sharedPref.getInt(DELAY, 15);
			period = sharedPref.getInt(PERIOD, 15);
			max_speed = sharedPref.getInt(MAX_SPEED, 5);
			min_speed = sharedPref.getInt(MIN_SPEED, 3);
			autostop = sharedPref.getInt(AUTOSTOP, 0);
			color = sharedPref.getInt(COLOR, 0);
			timeout = sharedPref.getInt(TIMEOUT, 0);
			
		 }
		
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
						delay = (int) (5*Math.floor(arg1/5));
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
						period = Math.max(1,(int) (5*Math.floor(arg1/5)));
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
		 
		 delay_bar.setProgress(delay);
		 period_bar.setProgress(period);
		 max_speed_bar.setProgress(max_speed);
		 min_speed_bar.setProgress(min_speed);
		 
		 delay_text.setText("Delay: " + delay + " seconds");
		 period_text.setText("Period: " + period + " seconds");
		 min_speed_text.setText("Min Speed: " + min_speed + " MPH");
		 max_speed_text.setText("Max Speed: " + max_speed + " MPH");
		 
		 
		 //scale
		 Drawable d = getResources().getDrawable(R.drawable.clouds);
		 Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
		 
		 int height = getWindowManager().getDefaultDisplay().getHeight(); 
		 int width = getWindowManager().getDefaultDisplay().getWidth(); 
		 
		 float factor = ( (float) height / (float) bitmap.getHeight() );
		 if (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90 || getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270) {
			 factor = ( (float) width / (float) bitmap.getWidth() );
		 }
		 
		 Bitmap scalemap = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*factor), (int)(bitmap.getHeight()*factor), false);
		 
		 ImageView img = (ImageView) findViewById(R.id.bg);
		 //img.setMinimumHeight((int)(bitmap.getHeight()*factor));
		 //img.setMinimumWidth((int)(bitmap.getWidth()*factor));
		 img.setImageBitmap(scalemap);
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
			i.putExtra(AUTOSTOP, autostop);
			i.putExtra(COLOR, color);
			i.putExtra(TIMEOUT, timeout);
			
			Editor editor = sharedPref.edit();
			editor.putInt(DELAY, delay);
			editor.putInt(PERIOD, period);
			editor.putInt(MAX_SPEED, max_speed);
			editor.putInt(MIN_SPEED, min_speed);
			editor.putInt(AUTOSTOP, autostop);
			editor.putInt(COLOR, color);
			editor.putInt(TIMEOUT, timeout);
			editor.commit();
			
			startActivityForResult(i,QUIT_ALL);
		
		}
	}

	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
	        finish();
	        System.exit(0);			
		}
		if (resultCode == 0) {
			//do nothing, drop to defaults
		}
		if (resultCode == 3) {
	        autostop = data.getIntExtra(OptionsActivity.AUTOSTOP_MESSAGE, 0);	
	        color = data.getIntExtra(OptionsActivity.COLOR_MESSAGE, 0);
	        
	        if (data.getIntExtra("RESET", 0) > 0) {
	        	delay = 15;
	        	period = 15;
	        	max_speed = 5;
	        	min_speed = 3;
	        	color = 0;
	        	autostop = 0;
	        	timeout = 0;
	        }
	        if (data.getIntExtra("RESET", 0) == 2) {
				Editor editor = sharedPref.edit();
				editor.putInt(DELAY, delay);
				editor.putInt(PERIOD, period);
				editor.putInt(TIMEOUT, timeout);
				editor.putInt(MAX_SPEED, max_speed);
				editor.putInt(MIN_SPEED, min_speed);
				editor.putInt(AUTOSTOP, autostop);
				editor.putInt(COLOR, color);
				editor.putInt(SPEED_1, 0);
				editor.putInt(SPEED_2, 0);
				editor.putInt(SPEED_3, 0);
				editor.putInt(TIME_1, 1);
				editor.putInt(TIME_2, 10);
				editor.putInt(TIME_3, 30);
				editor.commit();	        	
	        }
	        
	        //stay here afterwards
		}

		delay_text.setText("Delay: " + delay + " seconds");
		period_text.setText("Period: " + period + " seconds");
		max_speed_text.setText("Max Speed: " + max_speed + " MPH");
		min_speed_text.setText("Min Speed: " + min_speed + " MPH");
		
		delay_bar.setProgress(delay);
		period_bar.setProgress(period);
		max_speed_bar.setProgress(max_speed);
		min_speed_bar.setProgress(min_speed);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void Goto_Settings(View view) {
		Intent i = new Intent(this, OptionsActivity.class);
		startActivityForResult(i,QUIT_ALL);
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
	        	Goto_Settings(this.findViewById(android.R.id.content));
	        	return true;
	        case R.id.action_about:
	        	return true;
	        case R.id.action_quit:
	        	Goto_Quit(this.findViewById(android.R.id.content));
	    }
	    return true;
	}
	
	public void saveSettings() {

	}
	
	public void onSaveInstanceState(Bundle savedInstanceState) { //this is important, rotation happens

	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	    
	    savedInstanceState.putInt(SAVE_AUTOSTOP, autostop);
	    savedInstanceState.putInt(SAVE_DELAY, delay);
	    savedInstanceState.putInt(SAVE_PERIOD, period);
	    savedInstanceState.putInt(SAVE_MAX_SPEED, max_speed);
	    savedInstanceState.putInt(SAVE_MIN_SPEED, min_speed);
	    savedInstanceState.putInt(SAVE_COLOR, color);
	    savedInstanceState.putInt(SAVE_TIMEOUT, timeout);
	    
	}
	
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
		
		Editor editor = sharedPref.edit();
		editor.putInt(DELAY, delay);
		editor.putInt(PERIOD, period);
		editor.putInt(MAX_SPEED, max_speed);
		editor.putInt(MIN_SPEED, min_speed);
		editor.putInt(AUTOSTOP, autostop);
		editor.putInt(COLOR, color);
		editor.putInt(TIMEOUT, timeout);
		editor.commit();		
	}
	

}
