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
	    Common.customTitle(this, "ģ����õ");
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
		String message ="������ ���� īī����\n\"�ǻ������ ���� ������!\"\n"
			+"���Խ� ��õ�� ���̵� \n[ "+Common.id+" ]�� �Է��Ͻø� \n��õ�� 300P �� ������ 2000P��\n��� �����˴ϴ�.\n";
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
			        		message, "īī����", arrMetaInfo, "UTF-8");
			        if (link.isAvailable()) {
			        	startActivity(link.getIntent());
			        } else {
			        	Common.nToast(this, "īī������ ��ġ�Ǿ� ���� �ʽ��ϴ�.");
			        }
			  }catch(UnsupportedEncodingException e){
			  e.printStackTrace(); 
			  }		
		}else{
		switch(v.getId()){
		case R.id.btnTwitter :
			recommendURL="http://twitter.com/home?status="+message+"\t\"�ٿ�ε� ��ũ : \""+referenceURLString;
			break;
		case R.id.btnFacebook :
			recommendURL="http://www.facebook.com/sharer/sharer.php?s=100&p[url]="+referenceURLString
								+"&p[images][0]=http://dooit.co.kr/include/img/dooit/btn_main01.gif&p[title]="+message;
			break;
		case R.id.btnMetoday :
			recommendURL="http://me2day.net/posts/new?new_post[body]="+message+"\t\"�ٿ�ε� ��ũ : \""+referenceURLString;
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