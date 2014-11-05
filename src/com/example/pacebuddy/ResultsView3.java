package com.example.pacebuddy;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ResultsView3 extends View {
	
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
	
	float imgheight;
	
	int TOTAL_HEIGHT;
	
	float[] records;
	
	Paint blackpaint = new Paint(Color.BLACK);
	
	DecimalFormat format = new DecimalFormat("00");
	DecimalFormat format2 = new DecimalFormat("0.000");
	

	public ResultsView3(Context context) {
		super(context);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		
		float maxspeed = 0;
		int maxindex = 0;
		float minspeed = 9999;
		int minindex = 0;
		
		float maxlapspeed = 0;
		int maxlapindex = 0;
		float minlapspeed = 9999;
		int minlapindex = 0;
		
		blackpaint.setTextSize(40);
		
		float alldist = 0;
		float alltime = 0;
		
		if (periods != 0) {
			for(int i=0;i<periods;i++) {
				
				if (((float) period_distances[i]/5280)/(((float) period_time/(float) (3600*1000))) > maxspeed) {
					maxindex = i;
					maxspeed = ((float) period_distances[i]/5280)/(((float) period_time/(float) (3600*1000)));
				}
				if (((float) period_distances[i]/5280)/(((float) period_time/(float) (3600*1000))) < minspeed) {
					minindex = i;
					minspeed = ((float) period_distances[i]/5280)/(((float) period_time/(float) (3600*1000)));
				}
			}
			
			int maxtime = maxindex * period_time;
			int minutes = (int) Math.floor(maxtime/60000);
			int seconds = (int) Math.floor((maxtime - (minutes * 60000))/1000);
			int milliseconds = (int) (maxtime - (minutes * 60000) - (1000 * seconds));
			
			String s = format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds);
			
			int mintime = minindex * period_time;
			minutes = (int) Math.floor(mintime/60000);
			seconds = (int) Math.floor((mintime - (minutes * 60000))/1000);
			milliseconds = (int) (mintime - (minutes * 60000) - (1000 * seconds));
			
			String s2 = format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds);			
			
			canvas.drawText("Max Speed", 10, 40, blackpaint);
			canvas.drawText(maxspeed + " MPH, " + s, 10, 90, blackpaint);
			canvas.drawText("Min Speed", 10, 140, blackpaint);
			canvas.drawText(minspeed + " MPH, " + s2, 10, 190, blackpaint);
			
		}
		if (laps > 1) {
			for(int i=0;i<laps;i++) {
				
				if (((float) lap_distances[i]/5280)/(((float) lap_times[i]/(float) (3600*1000))) > maxlapspeed) {
					maxlapindex = i;
					maxlapspeed = ((float) period_distances[i]/5280)/(((float) lap_times[i]/(float) (3600*1000)));
				}
				if (((float) lap_distances[i]/5280)/(((float) lap_times[i]/(float) (3600*1000))) < minlapspeed) {
					minlapindex = i;
					minlapspeed = ((float) period_distances[i]/5280)/(((float) lap_times[i]/(float) (3600*1000)));
				}
			}		
			
			canvas.drawText("Max Speed", 10, (height/2) + 40, blackpaint);
			canvas.drawText(maxlapspeed + " MPH, LAP " + (maxlapindex+1), 10, (height/2) + 90, blackpaint);
			canvas.drawText("Min Speed", 10, (height/2) + 140, blackpaint);
			canvas.drawText(minlapspeed + " MPH, LAP " + (minlapindex+1), 10, (height/2) + 190, blackpaint);
			
			
		}
		
		//total average
		for(int i=0;i<laps;i++) {
			alldist += lap_distances[i];
			alltime += lap_times[i];
		}
		
		float this_height = (height/2);
		
		if (laps > 1) {
			imgheight = (float) (height + 360);
			this_height = height;
		}
		
		float avgtime = ((float) alldist/5280)/(((float) alltime/(float) (3600*1000)));
		canvas.drawText("Average Speed", 10, this_height + 40, blackpaint);
		canvas.drawText(avgtime + " MPH", 10, this_height + 80, blackpaint);
		
		canvas.drawText("Record 1", 10, this_height + 120, blackpaint);
		canvas.drawText(records[0] + " MPH", 10, this_height + 160, blackpaint);
		canvas.drawText("Record 2", 10, this_height + 200, blackpaint);
		canvas.drawText(records[1] + " MPH", 10, this_height + 240, blackpaint);
		canvas.drawText("Record 3", 10, this_height + 280, blackpaint);
		canvas.drawText(records[2] + " MPH", 10, this_height + 320, blackpaint);
		
	}
	
	public void getData(int p, float[] pd, int l, float[] lt, float[] ld, int h, int w, int ti, int pti, int[] sp, float rec1, float rec2, float rec3) {
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
		
		imgheight = h/2 + 360;
		
		MIN_HEIGHT = h/4;
		MAX_HEIGHT = h/2;
		MIN_WIDTH = 50;
		MAX_WIDTH = w-50;
		MIN_LAP_HEIGHT = 3*h/4;
		MAX_LAP_HEIGHT = h;
		TOTAL_HEIGHT = h;
		records = new float[3];
		records[0] = rec1;
		records[1] = rec2;
		records[2] = rec3;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    setMeasuredDimension(width,(int)imgheight);
	}

}
