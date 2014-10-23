package com.example.pacebuddy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ResultsActivity extends Activity {

	int periods;
	float[] period_distances;
	int laps;
	float[] lap_times;
	float[] lap_distances;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_results);
		
		Intent intent = getIntent();
		
		int height = getWindowManager().getDefaultDisplay().getHeight();
		int width = getWindowManager().getDefaultDisplay().getWidth();
		
		periods = intent.getIntExtra(RunActivity.PERIOD_MESSAGE, 0);
		period_distances = intent.getFloatArrayExtra(RunActivity.PERIOD_DISTANCE_MESSAGE);
		laps = intent.getIntExtra(RunActivity.LAP_MESSAGE, 0);
		lap_times = intent.getFloatArrayExtra(RunActivity.LAP_TIME_MESSAGE);
		lap_distances = intent.getFloatArrayExtra(RunActivity.LAP_DISTANCE_MESSAGE);
		
		ScrollView sv = new ScrollView(this);
		HorizontalScrollView hv = new HorizontalScrollView(this);
		
		ResultsView rv = new ResultsView(getApplicationContext());
		rv.getData(periods, period_distances, laps, lap_times, lap_distances, height, width);
		
		//hv.addView(rv);
		//sv.addView(hv);
		setContentView(rv);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
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

}