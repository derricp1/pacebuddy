package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	
	public static final String SAVE_AUTOSTOP = "SAVE_AUTOSTOP";
	public static final String SAVE_COLOR = "SAVE_COLOR";
	
	int autostop;
	TextView autostop_text;
	SeekBar autostopbar;
	int color;
	
	RadioButton[] buttons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
	
		if (savedInstanceState != null) {
			autostop = savedInstanceState.getInt(SAVE_AUTOSTOP);
		}
		else {
			autostop = 0;
		}
		
		autostopbar = (SeekBar) findViewById(R.id.AutoStopBar);
		autostop_text = (TextView) findViewById(R.id.AutoStopText);
		
		buttons = new RadioButton[7];
		
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
		Intent in = new Intent();

		for(int i=0;i<7;i++) {
			if (buttons[i].isChecked() == true) {
				color = i;
			}
		}		
		
		in.putExtra("AUTOSTOP_MESSAGE", autostop);
		in.putExtra("COLOR_MESSAGE", color);
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
		
	}

}
