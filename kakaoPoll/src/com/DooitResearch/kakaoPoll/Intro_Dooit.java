package com.DooitResearch.kakaoPoll;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
public class Intro_Dooit extends Activity implements OnClickListener{
	EditText[] edt=new EditText[5];
	ImageView img1, img2, img3, img4;
	String edt_String;
	BufferedReader br=null;
	StringBuffer sb;
	ImageView[] image={img1,img2,img3,img4};
	int[] say={R.id.say1,R.id.say2,R.id.say3,R.id.say4,R.id.say5}; 
	int[] imgbtn={R.id.imgbtn1,R.id.imgbtn2,R.id.imgbtn3,R.id.imgbtn4};
	int[] strInt={R.raw.about_dooit_1, R.raw.about_dooit_2, 
				  R.raw.about_dooit_3, R.raw.about_dooit_4, R.raw.about_dooit_5};
	StringBuffer[] sbArray = { null, null, null, null, null};
	ArrayList<EditText> edt_array;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	    setContentView(R.layout.intro_dooit);
	    Common.customTitle(this,"About DooIt");
	    edt_array=new ArrayList<EditText>();
	    for(int i=0;i<4;i++){
	    	image[i]=(ImageView)findViewById(imgbtn[i]);
	    	image[i].setTag(i);
	    	image[i].setOnClickListener(this);
	    }
	    for(int i=0;i<5;i++){
	    edt[i]=(EditText)findViewById(say[i]);
	    edt_array.add(edt[i]);
	    }
	    for(int i=0;i<5;i++){
	    	try {
	    	    sb=new StringBuffer();
	    		br = new BufferedReader(
	    				new InputStreamReader(getResources()
	    						.openRawResource(strInt[i]),"euc-kr"));
	    		while ((edt_String= br.readLine()) != null) {
	    			sbArray[i] = sb.append(edt_String+ "\n");
	    		}
	    		  edt_String=null;
    			edt_array.get(i).setText(sbArray[i]);
	    	} catch (Exception e) {
	    	} finally {
	    		try {
	    			br.close();
	    		} catch (Exception e2) {
	    		}
	    	}
	    }
	}
	public void onClick(View v) {		
		int tag=(Integer) v.getTag();
		switch(tag){
			case 0: Common.goWebview(this, Common.solution1,1);
				break;
			case 1: Common.goWebview(this, Common.solution2,0);
				break;
			case 2: 
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=µÎÀÕ¼­º£ÀÌ(DOOITSURVEY)")));
				break;
			case 3:
				Common.goWebview(this, Common.researchAD,0);
				break;
		}
	}
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result=new Common(this).menuCreate(menu);
		return result;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result=new Common(this).menuAction(item, Intro_Dooit.this);
		return result;
	}
}