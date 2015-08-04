package com.DooitResearch.kakaoPoll;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;

public class Bottom_Flipper extends ViewFlipper {
		ViewFlipper flipper;
		Random random;
		Context context;
		Timer timer;
		int ranNum, previousNum;
		LayoutInflater layoutInflater;
		View view;
	public Bottom_Flipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view=layoutInflater.inflate(R.layout.bottom_flipper, this);
		flipper=(ViewFlipper)findViewById(R.id.flipper);
		random = new Random();
		timer=new Timer();
		timer.schedule(myTask, 0, 3000);
	}
	TimerTask myTask=new TimerTask(){
		public void run(){
			do{
				ranNum = random.nextInt(flipper.getChildCount());		 
			}while(ranNum==previousNum);
				previousNum=ranNum;
				handler.sendEmptyMessage(0);
		}
	};
	Handler handler=new Handler(){
		public void handleMessage(Message msg){
			flipper.setDisplayedChild(ranNum);
		}
	};
}