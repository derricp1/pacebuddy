package com.example.pacebuddy;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
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
	
	Paint blackpaint = new Paint(Color.BLACK);
	
	
	DecimalFormat format = new DecimalFormat("00");
	DecimalFormat format2 = new DecimalFormat("0.000");

	public ResultsView2(Context context) {
		super(context);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		
		ArrayList<Integer> is_over = new ArrayList<Integer>();
		ArrayList<Integer> is_under = new ArrayList<Integer>();

		boolean over_min = false;
		
		for (int i=0;i<periods;i++) {
			float speed = (float)(period_distances[i]/(float)5280)/(((float) period_time/(float) (3600*1000)));
			if (over_min == false && speed >= speeds[1]) {
				over_min = true;
			}
			
			if (over_min == true) {
				
				if (speed > speeds[0]) {
					is_over.add(i);
				}
				
				if (speed < speeds[1]) {
					is_under.add(i);
				}
				
			}
				
		}
		
		float height1 = 50;
		float height2 = 50;
		int q1 = listChecker(is_over);
		int q2 = listChecker(is_under);
		
		height1 = Math.min(50, height/(q1+2));
		height2 = Math.min(50, height/(q2+2));
		float height = Math.min(height1, height2);
		
		blackpaint.setColor(Color.BLACK);
		blackpaint.setTextSize(height);
		
		canvas.drawText("Over Max", 10, height, blackpaint);
		canvas.drawText("Under Min", (MAX_WIDTH+50)/2 + 10, height, blackpaint);

		canvas = listWriter(is_over, 10, height, canvas);
		canvas = listWriter(is_under, (MAX_WIDTH+50)/2 + 10, height, canvas);
			
	}
	
	public Canvas listWriter(ArrayList<Integer> list, float x, float size, Canvas canvas) {
		
		float h = size * 2;
		
		int index = 0;
		int start = -1; //nothing available
		int end = -1;
		//boolean endprint = false;
		
		while (index < list.size()) {
			
			if (start == -1) {
				start = list.get(index);
				end = start+1;
			}
			else {
				if (list.get(index) == end) {
					end += 1;
				}
				else {
					
					int time = start * period_time;
					int end_time = end * period_time;
					int[] bucket = {time,end_time};
					for(int q=0;q<2;q++) {
						int minutes = (int) Math.floor(bucket[q]/60000);
						int seconds = (int) Math.floor((bucket[q] - (minutes * 60000))/1000);
						int milliseconds = (int) (bucket[q] - (minutes * 60000) - (1000 * seconds));
						
						String s = format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds);
						if (q == 0) {
							s = s + "-";
						}
						canvas.drawText(s, x, h, blackpaint);
						h += size;						
					}
					
					//print range
					
					start = list.get(index);
					end = start + 1;
				}
			}
			
			index++;
			
		}
		
		if (start > -1) {
			int time = start * period_time;
			int end_time = end * period_time;
			int[] bucket = {time,end_time};
			for(int q=0;q<2;q++) {
				int minutes = (int) Math.floor(bucket[q]/60000);
				int seconds = (int) Math.floor((bucket[q] - (minutes * 60000))/1000);
				int milliseconds = (int) (bucket[q] - (minutes * 60000) - (1000 * seconds));
				
				String s = format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds);
				if (q == 0) {
					s = s + "-";
				}
				canvas.drawText(s, x, h, blackpaint);
				h += size;						
			}
		}
		
		return canvas;
		
	}
	
	public int listChecker(ArrayList<Integer> list) { //get how many periods we have.
		int ranges = 0;
		
		int index = 0;
		int start = -1; //nothing available
		int end = -1;
		//boolean endprint = false;
		
		while (index < list.size()) {
			
			if (start == -1) {
				start = list.get(index);
				end = start+1;
			}
			else {
				if (list.get(index) == end) {
					end += 1;
				}
				else {
					
					int time = start * period_time;
					int end_time = end * period_time;
					int[] bucket = {time,end_time};
					for(int q=0;q<2;q++) {
						int minutes = (int) Math.floor(bucket[q]/60000);
						int seconds = (int) Math.floor((bucket[q] - (minutes * 60000))/1000);
						int milliseconds = (int) (bucket[q] - (minutes * 60000) - (1000 * seconds));
						
						String s = format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds);
						if (q == 0) {
							s = s + "-";
						}
						ranges += 1;						
					}
					
					//print range
					
					start = list.get(index);
					end = start + 1;
				}
			}
			
			index++;
			
		}
		
		if (start > -1) {
			int time = start * period_time;
			int end_time = end * period_time;
			int[] bucket = {time,end_time};
			for(int q=0;q<2;q++) {
				int minutes = (int) Math.floor(bucket[q]/60000);
				int seconds = (int) Math.floor((bucket[q] - (minutes * 60000))/1000);
				int milliseconds = (int) (bucket[q] - (minutes * 60000) - (1000 * seconds));
				
				String s = format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds);
				if (q == 0) {
					s = s + "-";
				}
				ranges += 1;					
			}
		}		
		
		return ranges;
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
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    setMeasuredDimension(width,height);
	}

}
