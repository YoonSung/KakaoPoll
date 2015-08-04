package com.DooitResearch.kakaoPoll;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class Recommend extends Activity implements OnClickListener {
	ImageButton btnKakao, btnTwitter, btnFacebook, btnMetoday ;
	String recommendURL;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	    setContentView(R.layout.recommend);
	    Common.customTitle(this, "친구추천");
	    btnKakao=(ImageButton)findViewById(R.id.btnKakao);
	    btnTwitter=(ImageButton)findViewById(R.id.btnTwitter);
	    btnFacebook=(ImageButton)findViewById(R.id.btnFacebook);
	    btnMetoday=(ImageButton)findViewById(R.id.btnMetoday);
	    btnKakao.setOnClickListener(this);
	    btnTwitter.setOnClickListener(this);
	    btnFacebook.setOnClickListener(this);
	    btnMetoday.setOnClickListener(this);
	}
	public void onBackPressed(){
		finish();
		overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);		
	}
	public void onClick(View v) {
		String message ="돈버는 어플 카카오폴\n\"의사결정을 쉽고 빠르게!\"\n"
			+"가입시 추천인 아이디 \n[ "+Common.id+" ]를 입력하시면 \n추천인 300P 및 가입자 2000P가\n즉시 적립됩니다.\n";
	    String referenceURLString = "https://market.android.com/details?hl=ko&id=com.DooitLocalResearch";
	    String appVersion = "1.0";
		if(v.getId()==R.id.btnKakao){
			  try { 
			        ArrayList< Map < String, String > > arrMetaInfo = new ArrayList< Map< String, String > >();
			        Map < String, String > metaInfoAndroid = new Hashtable < String, String >(1);
			        metaInfoAndroid.put("os", "android");
			        metaInfoAndroid.put("devicetype", "phone");
			        metaInfoAndroid.put("installurl", "market://details?id=com.DooitLocalResearch");
			        metaInfoAndroid.put("executeurl", "market://details?id=com.DooitLocalResearch");
			        arrMetaInfo.add(metaInfoAndroid);
			        KakaoLink link = new KakaoLink(this, "www.dooit.co.kr", referenceURLString, appVersion, 
			        		message, "카카오폴", arrMetaInfo, "UTF-8");
			        if (link.isAvailable()) {
			        	startActivity(link.getIntent());
			        } else {
			        	Common.nToast(this, "카카오톡이 설치되어 있지 않습니다.");
			        }
			  }catch(UnsupportedEncodingException e){
			  e.printStackTrace(); 
			  }		
		}else{
		switch(v.getId()){
		case R.id.btnTwitter :
			recommendURL="http://twitter.com/home?status="+message+"\t\"다운로드 링크 : \""+referenceURLString;
			break;
		case R.id.btnFacebook :
			recommendURL="http://www.facebook.com/sharer/sharer.php?s=100&p[url]="+referenceURLString
								+"&p[images][0]=http://dooit.co.kr/include/img/dooit/btn_main01.gif&p[title]="+message;
			break;
		case R.id.btnMetoday :
			recommendURL="http://me2day.net/posts/new?new_post[body]="+message+"\t\"다운로드 링크 : \""+referenceURLString;
			break;
		}
		Common.goWebview(this, recommendURL, 0);
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result=new Common(this).menuCreate(menu);
		return result;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result=new Common(this).menuAction(item, Recommend.this);
		return result;
	}
}