package com.DooitResearch.kakaoPoll;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
	ImageButton regist_btn, result_btn, recommend_btn, manual, about_dooit; 
	private boolean Flag=false;
	PowerManager.WakeLock wl;
	static int n=0;
	String url;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
	    setContentView(R.layout.main);
	    Common.customTitle(this, "����");
	   regist_btn=(ImageButton)findViewById(R.id.btn1);
	   result_btn=(ImageButton)findViewById(R.id.btn2);
	   recommend_btn=(ImageButton)findViewById(R.id.btn3);
	   about_dooit=(ImageButton)findViewById(R.id.aboutdoit);
	   manual=(ImageButton)findViewById(R.id.manual);
	   regist_btn.setOnClickListener(this);
	   result_btn.setOnClickListener(this);
	   recommend_btn.setOnClickListener(this);
	   about_dooit.setOnClickListener(this);
	   manual.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn1:
			if(Common.read_make){
				Common.goWebview(this, Common.regist,2);
			}else{
				readCheck(1);
			}
				break;
		case R.id.btn2:
			if(Common.read_report){
				Common.goWebview(this, Common.report,2);
			}else{
				readCheck(2);
			}
				break;
		case R.id.btn3:
			startActivity(new Intent(Main.this, Recommend.class));
			break;
		case R.id.manual:
			new AlertDialog.Builder(this)
				.setTitle("�޴��� (manual)")
				.setMessage("īī���� ����� �� \n\n��ǥ������� �̿��ϴ� �����\n\n�޴����� ���� Ȯ���ϼ���.")
				.setPositiveButton("POLL �����",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								Common.goWebview(Main.this, "http://doooit.tistory.com/142", 0);
							} catch(Exception e) {
								Common.nToast(Main.this, "���ӿ� �����Ͽ����ϴ�. \n �ٽ��ѹ� �õ��� �ֽñ� �ٶ��ϴ�.");
							}
						}
					}).setNegativeButton("POLL ������ ",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								Common.goWebview(Main.this, "http://doooit.tistory.com/141", 0);
							} catch(Exception e) {
								Common.nToast(Main.this, "���ӿ� �����Ͽ����ϴ�. \n �ٽ��ѹ� �õ��� �ֽñ� �ٶ��ϴ�.");
							}
						}
				}).show();
			break;
		case R.id.aboutdoit:
			startActivity(new Intent(Main.this, Intro_Dooit.class));
			break;
		}
		overridePendingTransition(R.anim.activity_in,R.anim.activity_stop);
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0){
				Flag=false;
			}
		}
	};
	public void readCheck(int n){
		String message="";
		url=""; 
		Main.n=n;
		if(n==1){
			message="īī���� �����";
			url="http://doooit.tistory.com/142";
		}else if(n==2){
			message="īī���� ������";
			url="http://doooit.tistory.com/141";
		}
		new AlertDialog.Builder(this)
		.setTitle("")
		.setMessage("���ռ����̿�\n\n�����ϽŰ��� ȯ���մϴ�.\n\n���� �޴� �̿� 1ȸ��\n\n�����ںе��� �̿����Ǹ� ���Ͽ�\n\n"+message+"�� ���� \n\n������������ �ڵ��̵� �մϴ�.\n\n������ ���� ������ �ϴ���\n\n�޴��� �������� ����\n\nȮ���Ͻ� �� �ֽ��ϴ�.\n")
		.setPositiveButton("Ȯ��",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					try {
						Common.goWebview(Main.this, url, 0);
						Common.saveReadState(Main.n);
					} catch(Exception e) {
						Common.nToast(Main.this, "���ӿ� �����Ͽ����ϴ�. \n �ٽ��ѹ� �õ��� �ֽñ� �ٶ��ϴ�.");
					}
				}
			}).setCancelable(false).show();
	}
	public void onBackPressed(){
		if(!Flag){
			Toast.makeText(Main.this, "�ڷι�ư�� �ѹ� �� �����ø� ����˴ϴ�.", Toast.LENGTH_LONG).show();
			Flag=true;
			handler.sendEmptyMessageDelayed(0, 2000);
		}else{
			moveTaskToBack(true);
			finish();
		}
	};
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result=new Common(this).menuCreate(menu);
		return result;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result=new Common(this).menuAction(item, Main.this);
		return result;
	}
	public void onResume(){
		super.onResume();
		new Common(Main.this);
	}
}