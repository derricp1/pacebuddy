package com.example.pacebuddy;
import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ResultsView extends View {
	
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
	int color;
	
	Paint redpaint = new Paint(Color.RED);
	Paint blackpaint = new Paint(Color.BLACK);
	Paint graphpaint = new Paint(Color.BLACK);
	Paint linepaint = new Paint(Color.BLACK);
	
	DecimalFormat format = new DecimalFormat("00");
	DecimalFormat format2 = new DecimalFormat("0.000");

	public ResultsView(Context context) {
		super(context);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		blackpaint.setTextSize(50);
		blackpaint.setColor(Color.BLACK);
		redpaint.setColor(Color.RED);
		
		graphpaint.setTextSize(18);
		graphpaint.setColor(Color.BLACK);
		
		linepaint.setColor(Color.DKGRAY);
		
		switch (color) {
			case 1:
				redpaint.setColor(Color.GRAY);
				break;
			case 2:
				redpaint.setColor(Color.YELLOW);
				break;
			case 3:
				redpaint.setColor(Color.GREEN);
				break;
			case 4:
				redpaint.setColor(Color.BLUE);
				break;
			case 5:
				redpaint.setColor(Color.MAGENTA);
				break;
			case 6:
				redpaint.setColor(Color.BLACK);
				break;
		}
		
		float max_speed = 0;
		int max_index = 0;
		float totaldistance = 0;
		
		for (int i=0; i<periods; i++) {
			totaldistance += period_distances[i];
			if (period_distances[i] > period_distances[max_index])
				max_index = i;
		
		}
		
		totaldistance /= 5280;

		max_speed = ((float) period_distances[max_index]/5280)/(((float) period_time/(float) (3600*1000)));
		
		//distance on top
		canvas.drawText("Distance: " + format2.format(totaldistance) + " miles", 10, (float) (TOTAL_HEIGHT*0.07), blackpaint);
		
		int minutes = (int) Math.floor(time/6000);
		int seconds = (int) Math.floor((time - (minutes * 6000))/100);
		int milliseconds = (int) (time - (minutes * 6000) - (100 * seconds));
		
		canvas.drawText("Time: " + format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), 10, (float) (TOTAL_HEIGHT*0.14), blackpaint);		
		
		int etime = period_time*periods/10;
		minutes = (int) Math.floor(etime/6000);
		seconds = (int) Math.floor((etime - (minutes * 6000))/100);
		milliseconds = (int) (etime - (minutes * 6000) - (100 * seconds));
		
		int halfminutes = (int) Math.floor(minutes/2);
		int halfseconds = (int) Math.floor(seconds/2);
		if (minutes > halfminutes * 2)
			halfseconds += 30;
		int halfmilliseconds = (int) Math.floor(milliseconds/2); 
		if (seconds> halfseconds * 2)
			halfmilliseconds += 50;		
		
		//periods
		if (periods != 0) {
		
			canvas.drawText("Periods", 10, (float) (TOTAL_HEIGHT*0.21), blackpaint);
			
			int dividedwidth = (MAX_WIDTH-MIN_WIDTH)/periods;
			int currstart = MIN_WIDTH;
			
			float maxdistance = (float) 0.01;
			for(int q=0;q<periods;q++) {
				maxdistance = Math.max(maxdistance, period_distances[q]);
			}
			
			for(int i=0;i<periods;i++) {
				canvas.drawRect(currstart, Math.min(MAX_HEIGHT-1,MAX_HEIGHT-((MAX_HEIGHT-MIN_HEIGHT)*(period_distances[i]/maxdistance))), currstart+dividedwidth, MAX_HEIGHT, redpaint);
				currstart += dividedwidth;
			}
			
			//y axis
			canvas.drawText("0", graphpaint.getTextSize()/2, MAX_HEIGHT, graphpaint);
			canvas.drawText(format2.format((max_speed/2)), graphpaint.getTextSize()/4, MIN_HEIGHT-((MIN_HEIGHT-MAX_HEIGHT)/2), graphpaint);
			canvas.drawText(format2.format(max_speed), graphpaint.getTextSize()/4, MIN_HEIGHT, graphpaint);
		
			//x axis
			canvas.drawText("0", MIN_WIDTH, MAX_HEIGHT+graphpaint.getTextSize(), graphpaint);
			
			canvas.drawText(format.format(halfminutes) + ":" + format.format(halfseconds) + ":" + format.format(halfmilliseconds), (float) (MAX_WIDTH-graphpaint.getTextSize()*1.5-(MAX_WIDTH-MIN_WIDTH)/2), MAX_HEIGHT+graphpaint.getTextSize(), graphpaint);
			canvas.drawText(format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), (float) (MAX_WIDTH-graphpaint.getTextSize()*1.5), MAX_HEIGHT+graphpaint.getTextSize(), graphpaint);
			
			if (max_speed > speeds[1]) {
				canvas.drawRect(MIN_WIDTH, (MIN_HEIGHT - (MIN_HEIGHT-MAX_HEIGHT)*(1-(speeds[1]/max_speed))), MAX_WIDTH, (MIN_HEIGHT - (MIN_HEIGHT-MAX_HEIGHT)*(1-(speeds[1]/max_speed)))+3, linepaint);
				if (max_speed > speeds[0]) {
					canvas.drawRect(MIN_WIDTH, (MIN_HEIGHT - (MIN_HEIGHT-MAX_HEIGHT)*(1-(speeds[0]/max_speed))), MAX_WIDTH, (MIN_HEIGHT - (MIN_HEIGHT-MAX_HEIGHT)*(1-(speeds[0]/max_speed)))+3, linepaint);
				}			
			}
			
		}
		
		//laps
		if (laps > 1) {
			
			canvas.drawText("Laps", 10, (float) (TOTAL_HEIGHT*0.61), blackpaint);
			
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
				canvas.drawRect(currstart, Math.min(MAX_LAP_HEIGHT-1,MAX_LAP_HEIGHT-((MAX_LAP_HEIGHT-MIN_LAP_HEIGHT)*(lap_distances[i]/maxlapdistance))), currstart+(MAX_WIDTH-MIN_WIDTH)*(lap_times[i]/totallaptime), MAX_LAP_HEIGHT, redpaint);
				currstart += (MAX_WIDTH-MIN_WIDTH)*(lap_times[i]/totallaptime);
			}
			
			//y axis
			canvas.drawText("0", graphpaint.getTextSize()/2, MAX_LAP_HEIGHT, graphpaint);
			canvas.drawText(format2.format((max_speed/2)), graphpaint.getTextSize()/4, MIN_LAP_HEIGHT-((MIN_LAP_HEIGHT-MAX_LAP_HEIGHT)/2), graphpaint);
			canvas.drawText(format2.format(max_speed), graphpaint.getTextSize()/4, MIN_LAP_HEIGHT, graphpaint);
		
			//x axis
			canvas.drawText("0", MIN_WIDTH, MAX_HEIGHT+graphpaint.getTextSize(), graphpaint);
			
			canvas.drawText(format.format(halfminutes) + ":" + format.format(halfseconds) + ":" + format.format(halfmilliseconds), (float) (MAX_WIDTH-graphpaint.getTextSize()*1.5-(MAX_WIDTH-MIN_WIDTH)/2), MAX_LAP_HEIGHT+graphpaint.getTextSize(), graphpaint);
			canvas.drawText(format.format(minutes) + ":" + format.format(seconds) + ":" + format.format(milliseconds), (float) (MAX_WIDTH-graphpaint.getTextSize()*1.5), MAX_LAP_HEIGHT+graphpaint.getTextSize(), graphpaint);
		
		}
		
	}
	
	public void getData(int p, float[] pd, int l, float[] lt, float[] ld, int h, int w, int ti, int c, int pt, int[] spe) {
		periods = p;
		period_distances = pd;
		laps = l;
		lap_times = lt;
		lap_distances = ld;
		height = h;
		width = w;
		time = ti;
		color = c;
		period_time = pt;
		speeds = spe;
		
		MIN_HEIGHT = h/4;
		MAX_HEIGHT = h/2;
		MIN_WIDTH = 50;
		MAX_WIDTH = w-50;
		MIN_LAP_HEIGHT = (int) (h*0.65);
		MAX_LAP_HEIGHT = (int) (h*0.9);
		TOTAL_HEIGHT = h;
	}

}
