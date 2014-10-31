package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ResultActivityTwo extends Activity {
	
	int QUIT_ALL;
	
	int periods;
	float[] period_distances;
	int laps;
	float[] lap_times;
	float[] lap_distances;
	int time;
	int period_time;
	int[] speeds;
	int color;
	
	public final static String PERIOD_MESSAGE = "derricp1.apps.RMESSAGE";
	public final static String PERIOD_DISTANCE_MESSAGE = "derricp1.apps.RMESSAGE2";
	public final static String LAP_MESSAGE = "derricp1.apps.RMESSAGE3";
	public final static String LAP_TIME_MESSAGE = "derricp1.apps.RMESSAGE4";
	public final static String LAP_DISTANCE_MESSAGE = "derricp1.apps.RMESSAGE5";
	public final static String TIME_MESSAGE = "derricp1.apps.RMESSAGE6";
	public final static String PERIOD_TIME_MESSAGE = "derricp1.apps.RMESSAGE7";
	public final static String SPEEDS_MESSAGE = "derricp1.apps.RMESSAGE8";
	public final static String COLOR_MESSAGE = "derricp1.apps.RMESSAGE9";
	
	public final static String SAVE_PERIODS = "SAVE_PERIODS";
	public final static String SAVE_PERIOD_DISTANCES = "SAVE_PERIOD_DISTANCES";
	public final static String SAVE_LAPS = "SAVE_LAPS";
	public final static String SAVE_LAP_TIMES = "SAVE_LAP_TIMES";
	public final static String SAVE_LAP_DISTANCES = "SAVE_LAP_DISTANCES";
	public final static String SAVE_TIME = "SAVE_TIME";
	public final static String SAVE_PERIOD_TIME = "SAVE_PERIOD_TIME";
	public final static String SAVE_SPEEDS = "SAVE_SPEEDS";
	public final static String SAVE_COLOR = "SAVE_COLOR";
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_activity_two);
		
		Intent intent = getIntent();
		
		int height = getWindowManager().getDefaultDisplay().getHeight();
		int width = getWindowManager().getDefaultDisplay().getWidth();
		
		if (savedInstanceState != null) {
			periods = savedInstanceState.getInt(SAVE_PERIODS);
			period_distances = savedInstanceState.getFloatArray(SAVE_PERIOD_DISTANCES);
			laps = savedInstanceState.getInt(SAVE_LAPS);
			lap_times = savedInstanceState.getFloatArray(SAVE_LAP_TIMES);
			lap_distances = savedInstanceState.getFloatArray(SAVE_LAP_DISTANCES);	
			time = savedInstanceState.getInt(SAVE_TIME);	
			period_time = savedInstanceState.getInt(SAVE_PERIOD_TIME);
			speeds = savedInstanceState.getIntArray(SAVE_SPEEDS);
			color = savedInstanceState.getInt(SAVE_COLOR,0);
		}
		else {
			periods = intent.getIntExtra(ResultsActivity.PERIOD_MESSAGE, 0);
			period_distances = intent.getFloatArrayExtra(ResultsActivity.PERIOD_DISTANCE_MESSAGE);
			laps = intent.getIntExtra(ResultsActivity.LAP_MESSAGE, 0);
			lap_times = intent.getFloatArrayExtra(ResultsActivity.LAP_TIME_MESSAGE);
			lap_distances = intent.getFloatArrayExtra(ResultsActivity.LAP_DISTANCE_MESSAGE);
			time = intent.getIntExtra(ResultsActivity.TIME_MESSAGE, 0);
			period_time = intent.getIntExtra(ResultsActivity.PERIOD_TIME_MESSAGE, 0);
			speeds = intent.getIntArrayExtra(ResultsActivity.SPEEDS_MESSAGE);
			color = intent.getIntExtra(ResultsActivity.COLOR_MESSAGE,0);
		}
		
		ResultsView2 rv = new ResultsView2(getApplicationContext());
		rv.getData(periods, period_distances, laps, lap_times, lap_distances, height, width, time, period_time, speeds);
		setContentView(rv);			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_activity_two, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void Goto_Return(View view) { //return
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Return to Menu?");
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
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		// Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putInt("SAVE_PERIODS",periods);
		savedInstanceState.putFloatArray("SAVE_PERIOD_DISTANCES",period_distances);
		savedInstanceState.putInt("SAVE_LAPS",laps);
		savedInstanceState.putFloatArray("SAVE_LAP_TIMES",lap_times);
		savedInstanceState.putFloatArray("SAVE_LAP_DISTANCES",lap_distances);
		savedInstanceState.putInt("SAVE_TIME",time);
		savedInstanceState.putInt("SAVE_PERIOD_TIME",period_time);
		savedInstanceState.putIntArray("SAVE_SPEEDS",speeds);
		savedInstanceState.putInt("SAVE_COLOR",color);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
    		case R.id.action_next:
    			Goto_Next(this.findViewById(android.R.id.content));
    			return true;
        	case R.id.action_back:
        		Goto_Back(this.findViewById(android.R.id.content));
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
	
	public void Goto_Back(View view) { //quit
		Intent resultIntent = new Intent();
		setResult(2,resultIntent); //don't quit everything
		finish();	
	}
	
	public void Goto_Next(View view) { //next
		
		Intent i = new Intent(this, ResultActivityThree.class);
		
		i.putExtra(PERIOD_MESSAGE, periods);
		i.putExtra(PERIOD_DISTANCE_MESSAGE, period_distances);
		i.putExtra(LAP_MESSAGE, laps);
		i.putExtra(LAP_TIME_MESSAGE, lap_times);
		i.putExtra(LAP_DISTANCE_MESSAGE, lap_distances);
		i.putExtra(TIME_MESSAGE, time);
		i.putExtra(PERIOD_TIME_MESSAGE, period_time);
		i.putExtra(SPEEDS_MESSAGE, speeds);
		i.putExtra(COLOR_MESSAGE, color);
		
		startActivityForResult(i, QUIT_ALL);			

	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
	        Intent resultIntent = new Intent();
	        setResult(1,resultIntent); //quit everything
	        finish();
	        System.exit(0);			
		}
		if (resultCode == 0) {
			Intent resultIntent = new Intent();
			setResult(0,resultIntent); //don't quit everything
			finish();
	        System.exit(0);
		}
		if (resultCode == 2) {
			//do nothing, wait in this activity
		}
	}
	
}

