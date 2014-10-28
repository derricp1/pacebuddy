package com.example.pacebuddy;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
	int period_time;
	int[] speeds;
	
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
		
		ArrayList<Integer> over_periods = new ArrayList<Integer>();
		ArrayList<Integer> under_periods = new ArrayList<Integer>();
		boolean over_min = false;
		
		for (int i=0;i<periods;i++) {
			float speed = (period_distances[i]/5280)/(period_time/(3600*1000));
			if (over_min == false && speed >= speeds[1]) {
				over_min = true;
			}
			if (speed > speeds[0]) {
				over_periods.add((i+1)*period_time);
			}
			else if (over_min == true && speed < speeds[1]) {
				under_periods.add((i+1)*period_time);
			}
				
		}
		
		float iheight = 50;
		if (TOTAL_HEIGHT/(over_periods.size()+2) < 50 || TOTAL_HEIGHT/(under_periods.size()+2) < 50)
			iheight = Math.min((over_periods.size()+2)/TOTAL_HEIGHT, (under_periods.size()+2)/TOTAL_HEIGHT);
		
		blackpaint.setTextSize(Math.max(20,iheight));
		blackpaint.setColor(Color.BLACK);
		
		canvas.drawText("Over Max", 10, iheight, blackpaint);
		canvas.drawText("Under Min", (MAX_WIDTH+50)/2 + 10, iheight, blackpaint);
		
		float currloc = iheight*2;
		for (int i=0;i<over_periods.size();i++) {
			
			int minutes = (int) Math.floor(over_periods.get(i)/60000);
			int seconds = (int) Math.floor((over_periods.get(i) - (minutes * 60000))/1000);
			int milliseconds = (int) (over_periods.get(i) - (minutes * 60000) - (1000 * seconds));
			
			canvas.drawText(format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), 10, currloc, blackpaint);
			currloc += iheight;
		}
		currloc = iheight*2;
		for (int i=0;i<under_periods.size();i++) {
			int minutes = (int) Math.floor(under_periods.get(i)/60000);
			int seconds = (int) Math.floor((under_periods.get(i) - (minutes * 60000))/1000);
			int milliseconds = (int) (under_periods.get(i) - (minutes * 60000) - (1000 * seconds));
			
			canvas.drawText(format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), 10, currloc, blackpaint);
			currloc += iheight;
		}

			
	}
	
	public void getData(int p, float[] pd, int l, float[] lt, float[] ld, int h, int w, int ti, int pti, int[] sp) {
		periods = p;
		period_distances = pd;
		laps = l;
		lap_times = lt;
		lap_distances = ld;
		height = h;
		width = w;
		time = ti;
		period_time = pti;
		speeds = sp;
		
		MIN_HEIGHT = h/4;
		MAX_HEIGHT = h/2;
		MIN_WIDTH = 50;
		MAX_WIDTH = w-50;
		MIN_LAP_HEIGHT = 3*h/4;
		MAX_LAP_HEIGHT = h;
		TOTAL_HEIGHT = h;
	}

}
