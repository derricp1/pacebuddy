package com.example.pacebuddy;
import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ResultsView2 extends View {
	
	int MIN_HEIGHT;
	int MAX_HEIGHT;
	int MIN_WIDTH;
	int MAX_WIDTH;
	int MIN_LAP_HEIGHT;
	int MAX_LAP_HEIGHT;
			
	int periods;
	float[] period_distances;
	int laps;
	float[] lap_times;
	float[] lap_distances;
	int height;
	int width;
	int time;
	
	int[] max_times;
	int[] min_times;
	int num_max_times;
	int num_min_times;
	
	int TOTAL_HEIGHT;
	
	Paint redpaint = new Paint(Color.RED);
	Paint blackpaint = new Paint(Color.BLACK);
	
	
	DecimalFormat format = new DecimalFormat("00");
	DecimalFormat format2 = new DecimalFormat("0.000");

	public ResultsView2(Context context) {
		super(context);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		float height = 50;
		if (TOTAL_HEIGHT/(num_max_times+1) < 50 || TOTAL_HEIGHT/(num_min_times+1) < 50)
			height = Math.min((num_max_times+1)/TOTAL_HEIGHT, (num_min_times+1)/TOTAL_HEIGHT);
		
		blackpaint.setTextSize(Math.max(20,height));
		blackpaint.setColor(Color.BLACK);
		
		canvas.drawText("Over Max", 10, 10, blackpaint);
		canvas.drawText("Under Min", (MAX_WIDTH+50)/2 + 10, 10, blackpaint);
		
		float currloc = height;
		for (int i=0;i<num_max_times;i++) {
			
			int minutes = (int) Math.floor(max_times[i]/6000);
			int seconds = (int) Math.floor((max_times[i] - (minutes * 6000))/100);
			int milliseconds = (int) (max_times[i] - (minutes * 6000) - (100 * seconds));
			
			canvas.drawText(format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), 10, currloc, blackpaint);
			currloc += height;
		}
		currloc = height;
		for (int i=0;i<num_min_times;i++) {
			int minutes = (int) Math.floor(min_times[i]/6000);
			int seconds = (int) Math.floor((min_times[i] - (minutes * 6000))/100);
			int milliseconds = (int) (min_times[i] - (minutes * 6000) - (100 * seconds));
			
			canvas.drawText(format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), 10, currloc, blackpaint);
			currloc += height;
		}
			
	}
	
	public void getData(int p, float[] pd, int l, float[] lt, float[] ld, int h, int w, int ti, int[] maxt, int[] mint, int max, int min) {
		periods = p;
		period_distances = pd;
		laps = l;
		lap_times = lt;
		lap_distances = ld;
		height = h;
		width = w;
		time = ti;
		
		max_times = maxt;
		min_times = mint;
		num_max_times = max;
		num_min_times = min;
		
		MIN_HEIGHT = h/4;
		MAX_HEIGHT = h/2;
		MIN_WIDTH = 50;
		MAX_WIDTH = w-50;
		MIN_LAP_HEIGHT = 3*h/4;
		MAX_LAP_HEIGHT = h;
		TOTAL_HEIGHT = h;
	}

}
