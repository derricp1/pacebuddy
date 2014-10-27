package com.example.pacebuddy;
import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ResultsView extends View {
	
	int MIN_HEIGHT = 100;
	int MAX_HEIGHT = 300;
	int MIN_WIDTH = 0;
	int MAX_WIDTH = 0;
	int MIN_LAP_HEIGHT = 400;
	int MAX_LAP_HEIGHT = 600;
	
	int periods;
	float[] period_distances;
	int laps;
	float[] lap_times;
	float[] lap_distances;
	int height;
	int width;
	int time;
	
	Paint redpaint = new Paint(Color.RED);
	Paint blackpaint = new Paint(Color.BLACK);
	DecimalFormat format = new DecimalFormat("00");

	public ResultsView(Context context) {
		super(context);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		float totaldistance = 0;
		for (int i=0; i<periods; i++)
			totaldistance += period_distances[i];
		
		totaldistance /= 5280;
		
		//distance on top
		canvas.drawText("Total Distance: " + totaldistance + " miles", 10, 10, blackpaint);
		
		int minutes = (int) Math.floor(time/6000);
		int seconds = (int) Math.floor((time - (minutes * 6000))/100);
		int milliseconds = (int) (time - (minutes * 6000) - (100 * seconds));
		
		canvas.drawText("Total Time: " + format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), 10, 10, blackpaint);

		//periods
		if (periods != 0) {
		
			canvas.drawText("Periods", 10, (float) (MAX_HEIGHT*0.15), blackpaint);
			
			int dividedwidth = (MAX_WIDTH-MIN_WIDTH)/periods;
			int currstart = MIN_WIDTH;
			
			float maxdistance = (float) 0.01;
			for(int q=0;q<periods;q++) {
				maxdistance = Math.max(maxdistance, period_distances[q]);
			}
			
			for(int i=0;i<periods;i++) {
				canvas.drawRect(currstart, MAX_HEIGHT-((MAX_HEIGHT-MIN_HEIGHT)*(period_distances[i]/maxdistance)), currstart+dividedwidth, MAX_HEIGHT, redpaint);
				currstart += dividedwidth;
			}
		
		}
		
		//laps
		if (laps > 1) {
			
			canvas.drawText("Laps", 10, (float) (MAX_HEIGHT*0.65), blackpaint);
			
			float maxlapdistance = (float) 0.01;
			for(int q=0;q<laps;q++) {
				maxlapdistance = Math.max(maxlapdistance, lap_distances[q]);
			}
		
			float totallaptime = (float) 0.01;
			for(int q=0;q<laps;q++) {
				totallaptime += lap_times[q];
			}	
			
			float currstart = MIN_WIDTH;
			
			for(int i=0;i<laps;i++) {
				canvas.drawRect(currstart, MAX_LAP_HEIGHT-((MAX_LAP_HEIGHT-MIN_LAP_HEIGHT)*(lap_distances[i]/maxlapdistance)), currstart+(MAX_WIDTH-MAX_HEIGHT)*(lap_times[i]/totallaptime), MAX_LAP_HEIGHT, redpaint);
				currstart += (MAX_WIDTH-MAX_HEIGHT)*(lap_times[i]/totallaptime);
			}	
		
		}
		
	}
	
	public void getData(int p, float[] pd, int l, float[] lt, float[] ld, int h, int w, int ti) {
		periods = p;
		period_distances = pd;
		laps = l;
		lap_times = lt;
		lap_distances = ld;
		height = h;
		width = w;
		time = ti;
		
		MIN_HEIGHT = h/4;
		MAX_HEIGHT = h/2;
		MIN_WIDTH = 50;
		MAX_WIDTH = w-50;
		MIN_LAP_HEIGHT = 3*h/4;
		MAX_LAP_HEIGHT = h;
	}

}
