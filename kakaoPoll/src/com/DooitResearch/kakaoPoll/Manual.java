package com.DooitResearch.kakaoPoll;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class Manual extends Activity {


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.manual);
	    // TODO Auto-generated method stub
		
		
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
	    final TextView leftText=(TextView)findViewById(R.id.left_text);
	    leftText.setText("카카오폴 설명서");
	}
	
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);		
	}
	
	//***********************************************************************************************************************
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		boolean result=new Common(this).menuCreate(menu);
		
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		boolean result=new Common(this).menuAction(item,Manual.this);
		
		
		return result;
	}
}