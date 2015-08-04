package com.DooitResearch.kakaoPoll;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

public class Splash extends Activity {

public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.splash);
    Common.customTitle(this, "���ռ�����");
    CookieSyncManager.createInstance(this);
	CookieManager cookieManager = CookieManager.getInstance();
	cookieManager.removeAllCookie();
    if(!Common.checkNetwork(this)) {
		finish();
		return;
	}
    	Timer timer=new Timer();
    	timer.schedule(myTask, 400);
}
	TimerTask myTask=new TimerTask(){
		public void run(){
			final String newVersion = Common.getNewVersion(Splash.this);
			if(newVersion == null) {
				Common.centerToast(Splash.this, "�������ῡ �����Ͽ����ϴ�.��� �� �̿��Ͻñ� �ٶ��ϴ�.");
				finish();
				return;
			}
			if(newVersion.equals("1.0") == false) {
				Log.e("msg", newVersion);
				new AlertDialog.Builder(Splash.this)
				.setTitle("������Ʈ �˸�")
				.setMessage("���ο� ������ �����մϴ�. ������Ʈ �� �̿��ϼ���.")
				.setPositiveButton("������Ʈ",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newVersion)));
								finish();
							} catch(Exception e) {
								Toast.makeText(Splash.this, "������Ʈ ���ӿ� �����Ͽ����ϴ�.", Toast.LENGTH_SHORT).show();
								finish();
							}
						}
					}).setNegativeButton("����",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
				}).show();
				return;
			}
			startActivity(new Intent(Splash.this, Login.class));
			finish();
		}
	};
}