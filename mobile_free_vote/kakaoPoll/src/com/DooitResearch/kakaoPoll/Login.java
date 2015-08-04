package com.DooitResearch.kakaoPoll;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
public class Login extends Activity implements OnClickListener {
	Button btnin, btnJoin, btnFound;
	EditText edt1, edt2;
	LinearLayout loginlayout;
	String id, password, phone_id, phone;
	boolean Flag = false;
	boolean response;
	ProgressDialog dialog;
	CookieManager cookieManager;
	ImageView mal1, mal2, mal3, mal4;
	Animation ani1, ani2, ani3, ani4, ani5;
	Animation[] ani={ani1,ani2,ani3,ani4,ani5};
	PowerManager.WakeLock wl;
	String AsyncTaskError = null;
	boolean backgroundResponse;
	Common common;
	SharedPreferences spf;
	ArrayList<NameValuePair> postParameters;
	Context context=this;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.login);
		Common.customTitle(this, "���ռ�����");
		common=new Common(this);		
		btnin = (Button) findViewById(R.id.btnin);
		btnJoin = (Button) findViewById(R.id.btnJoin);
		btnFound = (Button) findViewById(R.id.btnFound);
		edt1 = (EditText) findViewById(R.id.edt1);
		edt2 = (EditText) findViewById(R.id.edt2);
		mal1 = (ImageView) findViewById(R.id.mal1);
		mal2 = (ImageView) findViewById(R.id.mal2);
		mal3 = (ImageView) findViewById(R.id.mal3);
		mal4 = (ImageView) findViewById(R.id.mal4);
		loginlayout = (LinearLayout) findViewById(R.id.loginLayer);
		graphic();
		if(Common.checkPreference()){
			handler.sendEmptyMessageDelayed(1, 2000);
		} else {
			loginlayout.startAnimation(ani5);
		}
		try{
        findViewById(Window.ID_ANDROID_CONTENT).post(new Runnable() {
			@Override
			public void run() {
				Rect rcStatus =new Rect();
				Window window=getWindow();
				window.getDecorView().getWindowVisibleDisplayFrame(rcStatus);
		        Common.status= rcStatus.top;
		        Common.content=window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		        Common.title=Common.content-Common.status;
			}
		});
		}catch(Exception e){
			Log.e("checkDevice", " Error Occur");
		}
		btnin.setOnClickListener(this);
		btnJoin.setOnClickListener(this);
		btnFound.setOnClickListener(this);
	}
	public void onClick(View v) {
		if(!Common.checkNetwork(this)||!Common.checkPhoneNum(this))
				return;
		id = edt1.getText().toString().trim();
		password = edt2.getText().toString().trim();
		phone=Common.phone;
		phone_id=Common.phone_id;
		switch (v.getId()) {
		case R.id.btnin:
			if((id.length()==0)||(password.length()==0)){
				Common.nToast(this, "�Է°��� ������ �ֽ��ϴ�.");
				return;
			}
				new LoginCheck().execute();
			break;
		case R.id.btnJoin:
			Common.goWebview(this, Common.join,1);
			break;
		case R.id.btnFound:
			Common.goWebview(this, Common.found,1);
			break;
		}
		overridePendingTransition(R.anim.activity_in,R.anim.activity_stop);
	}
	private class LoginCheck extends AsyncTask<String, Integer, Long> {
		boolean isReload=false;
		protected void onPreExecute() {
			super.onPreExecute();
			if(Common.checkPreference()){
				isReload=true;
				id=Common.id;
				phone=Common.phone;
				phone_id=Common.phone_id;
			}
			dialog = ProgressDialog.show(Login.this, "�޽�����", "�α��� ���� ��..");
		}
		@Override
		protected Long doInBackground(String... arg0) {
				postParameters = new ArrayList<NameValuePair>();
			if(!Common.checkPreference()){
				postParameters.add(new BasicNameValuePair("phone", phone));
				postParameters.add(new BasicNameValuePair("phone_id", phone_id));
				postParameters.add(new BasicNameValuePair("id", id));
				postParameters.add(new BasicNameValuePair("password", password));
					try {
						response = Common.executeHttpPost(
								"http://app.dooit.co.kr/member/login_check",postParameters);
						if (response) {
							Common.savePreference(id);
							backgroundResponse = true;
						} else {
							backgroundResponse = false;
						}
					} catch (Exception e) {
						AsyncTaskError = e.getMessage();
					}
			}else if(Common.checkPreference()){
				postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("phone", phone));
				postParameters.add(new BasicNameValuePair("phone_id", phone_id));
				postParameters.add(new BasicNameValuePair("id", id));
				postParameters.add(new BasicNameValuePair("lat", "kakaoPoll"));
				postParameters.add(new BasicNameValuePair("lng", "kakaoPoll"));
				Log.e("param", ""+id+phone+phone_id);
					try {
						response = Common.executeHttpPost(
						"http://app.dooit.co.kr/member/login_check/reload",postParameters);
						if (response) {
							backgroundResponse = true;
						} else {
							backgroundResponse = false;
						}
					} catch (Exception e) {
						AsyncTaskError = e.getMessage();
					}
			}else{
				AsyncTaskError="Login unexpected Error occur";
			}
				return null;
		}
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			if (backgroundResponse == true) {
				dialog.dismiss();
				startActivity(new Intent(Login.this, Main.class));
				finish();
			} else {
				if (AsyncTaskError != null) {
					Log.e("loginError", "AsyncTaskError");
					Toast.makeText(
							Login.this,
							"�α��� ��������\n(" + AsyncTaskError
									+ ")�� �߻��Ͽ����ϴ�.\n �ٽ� �õ��� �ֽñ� �ٶ��ϴ�.  ",
							Toast.LENGTH_LONG).show();
				} else {
					if(isReload){
						Log.e("AsyncTaskError isreload",AsyncTaskError);
					}else{
						Toast.makeText(Login.this, "���̵�� ��й�ȣ�� �ٽ� Ȯ���� �ּ���",
								Toast.LENGTH_LONG).show();
					}
				}
				Common.deletePreference();
				dialog.dismiss();
				if(isReload)
				loginlayout.startAnimation(ani5);
			}	
		}
	}
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Flag = false;
				break;
			case 1:
				new LoginCheck().execute();
				break;
			}
		}
	};
	public void onBackPressed() {
		if (!Flag) {
			Toast.makeText(Login.this, "�ڷι�ư�� �ѹ� �� �����ø� ����˴ϴ�.",
					Toast.LENGTH_LONG).show();
			Flag = true;
			handler.sendEmptyMessageDelayed(0, 1500);
		} else {
			finish();
		}
	};
	private void graphic() {
		ani1 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				(float) -0.5, Animation.RELATIVE_TO_PARENT, (float) -0.1,
				Animation.RELATIVE_TO_PARENT, (float) 0.0,
				Animation.RELATIVE_TO_PARENT, (float) 0.0);
		ani2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				(float) 0.6, Animation.RELATIVE_TO_PARENT, (float) 0.1,
				Animation.RELATIVE_TO_PARENT, (float) 0.2,
				Animation.RELATIVE_TO_PARENT, (float) 0.2);
		ani3 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				(float) -0.5, Animation.RELATIVE_TO_PARENT, (float) -0.1,
				Animation.RELATIVE_TO_PARENT, (float) 0.0,
				Animation.RELATIVE_TO_PARENT, (float) 0.0);
		ani4 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				(float) 0.7, Animation.RELATIVE_TO_PARENT, (float) 0.1,
				Animation.RELATIVE_TO_PARENT, (float) 0.1,
				Animation.RELATIVE_TO_PARENT, (float) 0.1);
		ani5 = new AlphaAnimation(0, 1);
		ani1.setInterpolator(this, android.R.anim.overshoot_interpolator);
		ani1.setDuration(1000);
		ani1.setFillAfter(true);
		ani2.setInterpolator(this, android.R.anim.overshoot_interpolator);
		ani2.setDuration(1000);
		ani2.setFillAfter(true);
		ani2.setStartOffset(300);
		ani3.setInterpolator(this, android.R.anim.overshoot_interpolator);
		ani3.setDuration(1000);
		ani3.setFillAfter(true);
		ani3.setStartOffset(600);
		ani4.setInterpolator(this, android.R.anim.overshoot_interpolator);
		ani4.setDuration(1000);
		ani4.setFillAfter(true);
		ani4.setStartOffset(900);
		ani5.setInterpolator(this, android.R.anim.overshoot_interpolator);
		ani5.setDuration(2000);
		ani5.setFillAfter(true);
		ani5.setStartOffset(2000);
		mal1.startAnimation(ani1);
		mal2.startAnimation(ani2);
		mal3.startAnimation(ani3);
		mal4.startAnimation(ani4);
	}
}