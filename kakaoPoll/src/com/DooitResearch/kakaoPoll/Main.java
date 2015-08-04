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
	    Common.customTitle(this, "메인");
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
				.setTitle("메뉴얼 (manual)")
				.setMessage("카카오폴 만들기 및 \n\n투표결과등을 이용하는 방법을\n\n메뉴얼을 통해 확인하세요.")
				.setPositiveButton("POLL 만들기",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								Common.goWebview(Main.this, "http://doooit.tistory.com/142", 0);
							} catch(Exception e) {
								Common.nToast(Main.this, "접속에 실패하였습니다. \n 다시한번 시도해 주시기 바랍니다.");
							}
						}
					}).setNegativeButton("POLL 보관함 ",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								Common.goWebview(Main.this, "http://doooit.tistory.com/141", 0);
							} catch(Exception e) {
								Common.nToast(Main.this, "접속에 실패하였습니다. \n 다시한번 시도해 주시기 바랍니다.");
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
			message="카카오폴 만들기";
			url="http://doooit.tistory.com/142";
		}else if(n==2){
			message="카카오폴 보관함";
			url="http://doooit.tistory.com/141";
		}
		new AlertDialog.Builder(this)
		.setTitle("")
		.setMessage("두잇서베이에\n\n가입하신것을 환영합니다.\n\n최초 메뉴 이용 1회는\n\n가입자분들의 이용편의를 위하여\n\n"+message+"에 대한 \n\n설명페이지로 자동이동 합니다.\n\n사용법은 메인 오른쪽 하단의\n\n메뉴얼 페이지를 통해\n\n확인하실 수 있습니다.\n")
		.setPositiveButton("확인",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					try {
						Common.goWebview(Main.this, url, 0);
						Common.saveReadState(Main.n);
					} catch(Exception e) {
						Common.nToast(Main.this, "접속에 실패하였습니다. \n 다시한번 시도해 주시기 바랍니다.");
					}
				}
			}).setCancelable(false).show();
	}
	public void onBackPressed(){
		if(!Flag){
			Toast.makeText(Main.this, "뒤로버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
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