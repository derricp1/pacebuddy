package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ResultsActivity extends Activity {
	
	int QUIT_ALL = 0;

	int periods;
	float[] period_distances;
	int laps;
	float[] lap_times;
	float[] lap_distances;
	int time;
	int[] max_times;
	int[] min_times;
	int num_max_times;
	int num_min_times;
	
	public final static String PERIOD_MESSAGE = "derricp1.apps.RMESSAGE";
	public final static String PERIOD_DISTANCE_MESSAGE = "derricp1.apps.RMESSAGE2";
	public final static String LAP_MESSAGE = "derricp1.apps.RMESSAGE3";
	public final static String LAP_TIME_MESSAGE = "derricp1.apps.RMESSAGE4";
	public final static String LAP_DISTANCE_MESSAGE = "derricp1.apps.RMESSAGE5";
	public final static String TIME_MESSAGE = "derricp1.apps.RMESSAGE6";
	public final static String MAX_TIMES_MESSAGE = "derricp1.apps.RMESSAGE3";
	public final static String MIN_TIMES_MESSAGE = "derricp1.apps.RMESSAGE4";
	public final static String NUM_MAX_TIMES_MESSAGE = "derricp1.apps.RMESSAGE5";
	public final static String NUM_MIN_TIMES_MESSAGE = "derricp1.apps.RMESSAGE6";
	
	final String SAVE_PERIODS = "q";
	final String SAVE_PERIOD_DISTANCES = "q";
	final String SAVE_LAPS = "q";
	final String SAVE_LAP_TIMES = "q";
	final String SAVE_LAP_DISTANCES = "q";
	final String SAVE_TIME = "q";
	final String SAVE_MAX_TIMES = "SAVE_MAX_TIMES";
	final String SAVE_MIN_TIMES = "SAVE_MIN_TIMES";
	final String SAVE_NUM_MAX_TIMES = "SAVE_NUM_MAX_TIMES";
	final String SAVE_NUM_MIN_TIMES = "SAVE_NUM_MIN_TIMES";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
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
			max_times = savedInstanceState.getIntArray(SAVE_MAX_TIMES);
			min_times = savedInstanceState.getIntArray(SAVE_MIN_TIMES);
			num_max_times = savedInstanceState.getInt(SAVE_NUM_MAX_TIMES);	
			num_min_times = savedInstanceState.getInt(SAVE_NUM_MIN_TIMES);	
		}
		else {
			periods = intent.getIntExtra(RunActivity.PERIOD_MESSAGE, 0);
			period_distances = intent.getFloatArrayExtra(RunActivity.PERIOD_DISTANCE_MESSAGE);
			laps = intent.getIntExtra(RunActivity.LAP_MESSAGE, 0);
			lap_times = intent.getFloatArrayExtra(RunActivity.LAP_TIME_MESSAGE);
			lap_distances = intent.getFloatArrayExtra(RunActivity.LAP_DISTANCE_MESSAGE);
			time = intent.getIntExtra(RunActivity.TIME_MESSAGE, 0);
			max_times = intent.getIntArrayExtra(RunActivity.MAX_TIMES_MESSAGE);
			min_times = intent.getIntArrayExtra(RunActivity.MIN_TIMES_MESSAGE);
			num_max_times = intent.getIntExtra(RunActivity.NUM_MAX_TIMES_MESSAGE,0);
			num_min_times = intent.getIntExtra(RunActivity.NUM_MIN_TIMES_MESSAGE,0);
		}
		
		//ResultsView rv = (ResultsView) findViewById(R.id.results);
		//rv.getData(periods, period_distances, laps, lap_times, lap_distances, height, width);
		
		ResultsView rv = new ResultsView(getApplicationContext());
		rv.getData(periods, period_distances, laps, lap_times, lap_distances, height, width, time, max_times, min_times, num_max_times, num_min_times);
		
		//hv.addView(rv);
		//sv.addView(hv);
		setContentView(rv);
		rv.requestFocus();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}
	
	public void Goto_Next(View view) { //next
		
		Intent i = new Intent(this, ResultActivityTwo.class);
		
		i.putExtra(PERIOD_MESSAGE, periods);
		i.putExtra(PERIOD_DISTANCE_MESSAGE, period_distances);
		i.putExtra(LAP_MESSAGE, laps);
		i.putExtra(LAP_TIME_MESSAGE, lap_times);
		i.putExtra(LAP_DISTANCE_MESSAGE, lap_distances);
		i.putExtra(TIME_MESSAGE, time);
		i.putExtra(MAX_TIMES_MESSAGE, max_times);
		i.putExtra(MIN_TIMES_MESSAGE, min_times);
		i.putExtra(NUM_MAX_TIMES_MESSAGE, num_max_times);
		i.putExtra(NUM_MIN_TIMES_MESSAGE, num_min_times);
		
		startActivityForResult(i, QUIT_ALL);		

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
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	case R.id.action_next:
	    		Goto_Next(this.findViewById(android.R.id.content));
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
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
	        Intent resultIntent = new Intent();
	        setResult(1,resultIntent); //quit everything
	        finish();
	        System.exit(0);			
		}
		else {
			Intent resultIntent = new Intent();
			setResult(0,resultIntent); //don't quit everything
			finish();
	        System.exit(0);
		}
	}

}
