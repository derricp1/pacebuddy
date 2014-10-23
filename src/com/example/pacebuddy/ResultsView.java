package com.example.pacebuddy;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ResultsView extends View {
	
	int MIN_HEIGHT = 100;
	int MAX_HEIGHT = 300;
	int MIN_WIDTH;
	int MAX_WIDTH;
	int MIN_LAP_HEIGHT = 400;
	int MAX_LAP_HEIGHT = 600;
	
	int periods;
	float[] period_distances;
	int laps;
	float[] lap_times;
	float[] lap_distances;
	int height;
	int width;

	Paint redpaint = new Paint(Color.RED);

	public ResultsView(Context context) {
		super(context);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		//periods
		if (periods == 0)
			return;
		
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
		
		float maxlapdistance = (float) 0.01;
		for(int q=0;q<laps;q++) {
			maxlapdistance = Math.max(maxlapdistance, lap_distances[q]);
		}
		
		//laps
		if (laps == 0)
			return;		
		
		float totallaptime = (float) 0.01;
		for(int q=0;q<laps;q++) {
			totallaptime += lap_times[q];
		}	
		
		currstart = MIN_WIDTH;
		
		for(int i=0;i<laps;i++) {
			canvas.drawRect(currstart, MAX_LAP_HEIGHT-((MAX_LAP_HEIGHT-MIN_LAP_HEIGHT)*(lap_distances[i]/maxlapdistance)), currstart+(MAX_WIDTH-MAX_HEIGHT)*(lap_times[i]/totallaptime), MAX_LAP_HEIGHT, redpaint);
			currstart += (MAX_WIDTH-MAX_HEIGHT)*(lap_times[i]/totallaptime);
		}		
		
	}
	
	public void getData(int p, float[] pd, int l, float[] lt, float[] ld, int h, int w) {
		periods = p;
		period_distances = pd;
		laps = l;
		lap_times = lt;
		lap_distances = ld;
		height = h;
		width = w;
		
		MIN_WIDTH = 0;
		MAX_WIDTH = w;
	}

}
