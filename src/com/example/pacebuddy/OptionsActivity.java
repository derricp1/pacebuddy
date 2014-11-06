package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class OptionsActivity extends Activity {
	
	public static final String AUTOSTOP_MESSAGE = "AUTOSTOP_MESSAGE";
	public static final String COLOR_MESSAGE = "COLOR_MESSAGE";
	public static final String TIME1_MESSAGE = "TIME1_MESSAGE";
	public static final String TIME2_MESSAGE = "TIME2_MESSAGE";
	public static final String TIME3_MESSAGE = "TIME3_MESSAGE";
	public static final String TIMEOUT_MESSAGE = "TIMEOUT_MESSAGE";
	
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
	
	public static final String SAVE_AUTOSTOP = "SAVE_AUTOSTOP";
	public static final String SAVE_COLOR = "SAVE_COLOR";
	public static final String SAVE_TIME1 = "SAVE_TIME1";
	public static final String SAVE_TIME2 = "SAVE_TIME2";
	public static final String SAVE_TIME3 = "SAVE_TIME3";
	public static final String SAVE_TIMEOUT = "SAVE_TIMEOUT";
	
	int autostop;
	TextView autostop_text;
	SeekBar autostopbar;
	int color;
	
	int timeout;
	
	int[] times;
	SeekBar[] timebars;
	TextView[] timetexts;
	
	SeekBar timeoutbar;
	TextView timeouttext;
	
	SharedPreferences sharedPref;
	
	RadioButton[] buttons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
	
		times = new int[3];
		sharedPref = getApplicationContext().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
		
		if (savedInstanceState != null) {
			autostop = savedInstanceState.getInt(SAVE_AUTOSTOP);
			timeout = savedInstanceState.getInt(SAVE_TIMEOUT);
			times[0] = savedInstanceState.getInt(SAVE_TIME1);
			times[1] = savedInstanceState.getInt(SAVE_TIME2);
			times[2] = savedInstanceState.getInt(SAVE_TIME3);
		}
		else {
			autostop = 0;
			times[0] = sharedPref.getInt("TIME_1", 1);
			times[1] = sharedPref.getInt("TIME_2", 10);
			times[2] = sharedPref.getInt("TIME_3", 30);
			timeout = 0;
		}
		
		autostopbar = (SeekBar) findViewById(R.id.AutoStopBar);
		autostop_text = (TextView) findViewById(R.id.AutoStopText);

		buttons = new RadioButton[7];
		
		timebars = new SeekBar[3];
		timetexts = new TextView[3];
		timebars[0] = (SeekBar) findViewById(R.id.Time1Bar);
		timebars[1] = (SeekBar) findViewById(R.id.Time2Bar);
		timebars[2] = (SeekBar) findViewById(R.id.Time3Bar);
		timetexts[0] = (TextView) findViewById(R.id.Time1Text);
		timetexts[1] = (TextView) findViewById(R.id.Time2Text);
		timetexts[2] = (TextView) findViewById(R.id.Time3Text);
		timebars[0].setProgress(times[0]);
		timebars[1].setProgress(times[1]);
		timebars[2].setProgress(times[2]);
		timetexts[0].setText("Low time: " + times[0] + ":00");
		timetexts[1].setText("Medium time: " + times[1] + ":00");
		timetexts[2].setText("High time: " + times[2] + ":00");
		
		//set
		
		buttons[0] = (RadioButton) findViewById(R.id.radio0);
		buttons[1] = (RadioButton) findViewById(R.id.radio1);
		buttons[2] = (RadioButton) findViewById(R.id.radio2);
		buttons[3] = (RadioButton) findViewById(R.id.radio3);
		buttons[4] = (RadioButton) findViewById(R.id.radio4);
		buttons[5] = (RadioButton) findViewById(R.id.radio5);
		buttons[6] = (RadioButton) findViewById(R.id.radio6);
		
		if (savedInstanceState != null) {
			for(int i=0;i<7;i++) {
				buttons[i].setChecked(false);
			}
			color = savedInstanceState.getInt(SAVE_COLOR);
			buttons[color].setChecked(true);
		}
		else {
			color = 0;
			buttons[0].setChecked(true);
			for(int i=1;i<7;i++) {
				buttons[i].setChecked(false);
			}
		}
		
		timeoutbar = (SeekBar) findViewById(R.id.TimeOutBar);
		timeouttext = (TextView) findViewById(R.id.TimeOutText);
		
		timeoutbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if (arg2) {
					timeout = (int) (Math.floor(arg1));
					arg0.setProgress(timeout);
					if (timeout == 0) {
						timeouttext.setText("No Time Out");
					}
					else
						timeouttext.setText("Time out after: " + timeout + " minutes");
				}
				
			}		
		});	
			
		autostopbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if (arg2) {
					autostop = (int) (5*Math.floor(arg1/5));
					arg0.setProgress(autostop);
					if (autostop == 0) {
						autostop_text.setText("AutoStop: Off");
					}
					else
						autostop_text.setText("AutoStop: " + autostop + " seconds");
				}
				
			}			
		});
		
	
		
		
		
		timebars[0].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if (arg2) {
					times[0] = (int) Math.max(1,(Math.floor(arg1)));
					arg0.setProgress(times[0]);
					timetexts[0].setText("Low time: " + times[0] + ":00");
				}
				
			}			
		});
		timebars[2].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if (arg2) {
					times[2] = (int) Math.max(3,(Math.floor(arg1)));
					arg0.setProgress(times[2]);
					timetexts[2].setText("High time: " + times[2] + ":00");
				}
				
			}			
		});
		timebars[1].setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if (arg2) {
					times[1] = (int) Math.max(2,(Math.floor(arg1)));
					arg0.setProgress(times[1]);
					timetexts[1].setText("Medium time: " + times[1] + ":00");
				}
				
			}			
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_return:
	        	Goto_Return(this.findViewById(android.R.id.content));
	        	return true;
	        case R.id.action_quit:
	        	Goto_Quit(this.findViewById(android.R.id.content));
	    }
	    return true;
	}
	
	public void Goto_Return(View view) { //return
		finishReturn(0);
	}
	
	public void Reset(View view) { //return
		finishReturn(1);
	}
	
	public void Reset_All(View view) { //return
		finishReturn(2);
	}
	
	public void finishReturn(int clear) {
		Intent in = new Intent();

		for(int i=0;i<7;i++) {
			if (buttons[i].isChecked() == true) {
				color = i;
			}
		}		
		
		Editor editor = sharedPref.edit();
		editor.putInt(AUTOSTOP, autostop);
		editor.putInt(COLOR, color);
		editor.putInt(TIME_1, times[0]);
		editor.putInt(TIME_2, times[1]);
		editor.putInt(TIME_3, times[2]);
		editor.commit();
		
		in.putExtra("AUTOSTOP_MESSAGE", autostop);
		in.putExtra("COLOR_MESSAGE", color);
		in.putExtra("TIMEOUT_MESSAGE", timeout);
		in.putExtra("RESET", clear);
		setResult(3,in); //quit
        finish();		
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
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) { //this is important, rotation happens

	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt(SAVE_AUTOSTOP, autostop);
		
		for(int i=0;i<7;i++) {
			if (buttons[i].isChecked() == true) {
				color = i;
			}
		}
		savedInstanceState.putInt(SAVE_COLOR, color);
		savedInstanceState.putInt(SAVE_TIMEOUT, timeout);
		savedInstanceState.putInt(SAVE_TIME1, times[0]);
		savedInstanceState.putInt(SAVE_TIME2, times[1]);
		savedInstanceState.putInt(SAVE_TIME3, times[2]);
		
	}
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
		
		Editor editor = sharedPref.edit();
		editor.putInt(AUTOSTOP, autostop);
		editor.putInt(COLOR, color);
		editor.putInt(TIMEOUT, timeout);
		editor.commit();		
	}
	
}
