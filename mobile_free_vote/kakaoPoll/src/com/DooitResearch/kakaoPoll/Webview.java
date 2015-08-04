package com.DooitResearch.kakaoPoll;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.Browser;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class Webview extends Activity implements OnClickListener {
	Intent intent;
	private static String url;
	int web_style;
    WebView web;
    LinearLayout entire1, entire2, btn_layout, btn_margin;
    LinearLayout.LayoutParams entireParam1, entireParam2, webParam,btn_layout_Param, btn_margin_Param, btnParam;   
    Button btn1,btn2,btn3;
    Button[] btn;
    private static final int FILECHOOSER_RESULTCODE = 1;
	private ValueCallback<Uri> uploadMessage = null;
	ProgressBar pb;
    FrameLayout fr;
    ScrollView scv;
    FrameLayout.LayoutParams frameParam, pbParam;
    int Li_parent=LinearLayout.LayoutParams.FILL_PARENT;
    int d_Width, d_Height, e2_Width, e2_Height, Fr_Height, int_btn_margin, btn_width, btn_height, updown_margin;
    int[] btn_drawable;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		CookieSyncManager.createInstance(this);
	    intent=getIntent();
	    url=intent.getStringExtra("url");
	    web_style=intent.getIntExtra("web_style", 1); 
		if(web_style==0){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}else{
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		}
        Resources res=getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        btn_width=(int)(53*dm.density);
        btn_height=(int)(53*dm.density);
        updown_margin=(int)(53*dm.density);
        Display display=((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        d_Width=(int)display.getWidth();
        d_Height=(int)display.getHeight()-Common.status-Common.title;
        if(web_style==0){
        	e2_Width=d_Width/20*17;
        	e2_Height=d_Height/20*17;
        	Fr_Height=d_Height/20*17;
       	}else{
       		e2_Width=Li_parent;
        	e2_Height=Li_parent;
        	Fr_Height=d_Height-updown_margin;
        	if(web_style==1)
        		Fr_Height=d_Height;
       	}
        entire1=new LinearLayout(this);
        entireParam1=new LinearLayout.LayoutParams(Li_parent,Li_parent);
        entire1.setGravity(Gravity.CENTER);
        entire1.setBackgroundColor(0);
        entire2=new LinearLayout(this);
        entire2.setOrientation(LinearLayout.VERTICAL);
       	entireParam2=new LinearLayout.LayoutParams(e2_Width,e2_Height);
       	fr=new FrameLayout(this);
       	frameParam=new FrameLayout.LayoutParams(Li_parent, Fr_Height);
       	scv=new ScrollView(this);
       	scv.setScrollBarStyle(ScrollView.SCROLLBARS_INSIDE_OVERLAY);
       	scv.setVerticalFadingEdgeEnabled(false);
       	scv.setHorizontalFadingEdgeEnabled(false);
       	scv.setBackgroundResource(R.drawable.webview_bg);
       	web=new WebView(this);
        webParam=new LinearLayout.LayoutParams(Li_parent,Li_parent);
        pb = new ProgressBar(Webview.this, null, android.R.attr.progressBarStyleLarge);
        pbParam = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        pbParam.gravity=Gravity.CENTER;
        if((web_style==2)){
        int_btn_margin=(d_Width-(btn_width*3))/4;    
        btn_margin=new LinearLayout(this);
        btn_margin_Param=new LinearLayout.LayoutParams(int_btn_margin,updown_margin);
        btn_layout=new LinearLayout(this);
   		btn_layout.setBackgroundResource(R.drawable.web_layout);
        btn_layout.setOrientation(LinearLayout.HORIZONTAL);
        btn_layout.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        btn_layout_Param=new LinearLayout.LayoutParams(Li_parent, updown_margin);
        btnParam=new LinearLayout.LayoutParams(btn_width, btn_height);
        btn=new Button[]{btn1,btn2,btn3};
        btn_drawable=new int[]
        	{R.drawable.web_btn_main, R.drawable.web_btn_main_pressed,
             R.drawable.web_btn_my, R.drawable.web_btn_my_pressed,
             R.drawable.web_btn_regist, R.drawable.web_btn_regist_pressed};       
        for(int i=0;i<3;i++){
        	btn[i]=new Button(this);
        	btn[i].setTag(i);
        	btn[i].setOnClickListener(this);
        	btn[i].setBackgroundDrawable(createBackground(btn_drawable[2*i],btn_drawable[2*i+1]));
        }
        for(int i=0;i<3;i++){
        btn_layout.addView(new LinearLayout(this),btn_margin_Param);
        btn_layout.addView(btn[i], btnParam);
        }
        btn_layout.addView(new LinearLayout(this),btn_margin_Param);
        
        btnStatus();
        }
        scv.addView(web);
        fr.addView(scv,webParam);
        fr.addView(pb,pbParam);
        entire2.addView(fr,frameParam);
        if(web_style==2){
        entire2.addView(btn_layout,btn_layout_Param);
        }
        entire1.addView(entire2, entireParam2);
        setContentView(entire1, entireParam1);
        if(web_style!=0){
	    Common.customTitle(this, Common.titleCheck(url));
        }
        WebSettings setting=web.getSettings();
        StringBuffer userAgent = new StringBuffer(setting.getUserAgentString());
        userAgent.append(";DooitKakaoPollApp;");
        if(!url.contains("doooit.tistory")){
        	web.setBackgroundResource(R.drawable.webview_bg);    
			web.setBackgroundColor(0);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        web.setWebViewClient(new ViewClient());      
        setting.setJavaScriptEnabled(true);
        setting.setAppCacheEnabled(false);
        setting.setAllowFileAccess(true);
        setting.getLoadsImagesAutomatically();
        setting.setPluginsEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		setting.setUserAgentString(userAgent.toString());
		setting.setUseWideViewPort(true);
        web.setWebChromeClient(new JSWebChromeClient());
        web.setVerticalScrollbarOverlay(true);
		web.addJavascriptInterface(new MyJavaScriptInterface(), "DooitLocalResearch");
		web.requestFocus(View.FOCUS_DOWN);
		web.setFocusable(true);
		web.setFocusableInTouchMode(true);
		web.clearCache(true);
        web.loadUrl(url);
	}
	public void onClick(View v){
		int tag=(Integer) v.getTag();
		switch(tag){
		case 0:
			finish();
			if(!(web_style==0))
			overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);
			break;
		case 1:
			web.loadUrl(Common.report);
			break;
		case 2:
			web.loadUrl(Common.regist);
			break;
		}
	}
	final class MyJavaScriptInterface {
		MyJavaScriptInterface() {}
		public void JoinEnd() {
	        Common.centerToast(Webview.this, "감사합니다. 회원가입되었습니다.");
	        finish();
			if(web_style!=0)
				overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);
	    }
	}
	private StateListDrawable createBackground(int focused, int pressed ){
		StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{android.R.attr.state_pressed }, getResources().getDrawable(pressed));
        sld.addState(new int[]{ -android.R.attr.state_focused },getResources().getDrawable(focused));
     return sld;
	}
	private class ViewClient extends WebViewClient{
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Webview.url=url;
			if(("kakaolink").equals(url.substring(0, 9))){
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.putExtra(Browser.EXTRA_APPLICATION_ID, Webview.this.getPackageName());
		        Webview.this.startActivity(intent);
				return true;
			} else if(url.equalsIgnoreCase("http://m.dooit.co.kr")||url.equalsIgnoreCase("http://m.dooit.co.kr/member/login")){
				finish();
				return true;
			} else {
				return super.shouldOverrideUrlLoading(view, url);
			}		
		}
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
		}
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Webview.url=url;
			btnStatus();
			super.onPageStarted(view, url, favicon);
		}
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
		public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
			super.doUpdateVisitedHistory(view, url, isReload);
		}
		public void onFormResubmission(WebView view, Message dontResend, Message resend) {
			super.onFormResubmission(view, dontResend, resend);
		}
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
		}
		public void onScaleChanged(WebView view, float oldScale, float newScale) {
			super.onScaleChanged(view, oldScale, newScale);
		}
		public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
			super.onTooManyRedirects(view, cancelMsg, continueMsg);
		}
		public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
			super.onUnhandledKeyEvent(view, event);
		}
		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
			return super.shouldOverrideKeyEvent(view, event);
		}
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	        super.onReceivedError(view, errorCode, description, failingUrl);
	        Common.nToast(Webview.this, "네트워크 상태가 원활하지 않습니다. 잠시 후 다시 시도해 주세요.");
	        finish();
	    }
	}
	private class JSWebChromeClient extends WebChromeClient{
		public void openFileChooser(ValueCallback<Uri> uploadMsg) {
			uploadMessage = uploadMsg;
			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			i.addCategory(Intent.CATEGORY_OPENABLE);
			i.setType("image/*");
			startActivityForResult(Intent.createChooser(i, "이미지 파일 첨부"), FILECHOOSER_RESULTCODE);
		}
		public void onProgressChanged(WebView view, int Progress) {
			if(Progress<100){
				pb.setVisibility(View.VISIBLE);
			}else if(Progress==100){
				pb.setVisibility(View.GONE);
			}
			pb.setProgress(Progress);
			super.onProgressChanged(view, Progress);
		}
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
	          final JsResult finalRes = result;
	           new AlertDialog.Builder(view.getContext())
	           	 .setTitle("알림")
	               .setMessage(message)
	               .setPositiveButton(android.R.string.ok,
	                       new DialogInterface.OnClickListener()
	                       {
	         public void onClick(DialogInterface dialog, int which) {
	          finalRes.confirm();  
	         }})
	               .setCancelable(false)
	               .create()
	               .show();
	           return true;
		}
		public boolean onJsConfirm(WebView view, String url,
				String message, JsResult result) {
		 	  final JsResult finalRes = result;
	            new AlertDialog.Builder(view.getContext())
	            	.setTitle("제목")
	                .setMessage(message)
	                .setPositiveButton(android.R.string.ok,
				                       new DialogInterface.OnClickListener()
				                       {
								         @Override
								         public void onClick(DialogInterface dialog, int which) {
								          finalRes.confirm();  
								         }
								       }
	                				  )
	                .setNegativeButton(android.R.string.cancel,  
	                					new DialogInterface.OnClickListener() {
	                						@Override
						                   public void onClick(DialogInterface dialog, int which) {
						                   	finalRes.cancel();
						                   }
										}
	                				  )
	                .setCancelable(true)
	                .create()
	                .show();
	            return true;
	    	}
	}
	public void onBackPressed(){
		if(web.canGoBack()){
			web.goBack();
		}else{
		finish();
		if(web_style!=0)
		overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);
		}
	}
	public void onStart() {
		super.onStart();
		CookieSyncManager.createInstance(this);
    }
	protected void onResume() {
	    super.onResume();
	    CookieSyncManager.getInstance().startSync();
	}
	protected void onPause() {
	    super.onPause();
	    if (CookieSyncManager.getInstance() != null) {
			CookieSyncManager.getInstance().stopSync();
		}
	}
    public void btnStatus(){
        if(url.contains(Common.regist)){
        	btn[1].setBackgroundResource(btn_drawable[2]);
        	btn[2].setBackgroundResource(btn_drawable[5]);
        }else if(url.contains(Common.report)){
        	btn[1].setBackgroundResource(btn_drawable[3]);
        	btn[2].setBackgroundResource(btn_drawable[4]);
        }
    }
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FILECHOOSER_RESULTCODE && uploadMessage != null) {
			Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
			uploadMessage.onReceiveValue(result);
			uploadMessage = null;
		}
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(web_style==1){
			return false;
		}else{
			return super.onPrepareOptionsMenu(menu);	
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		if(web_style==1){
			return false;
		}else{
		boolean result=new Common(this).menuCreate(menu);
		return result;
		}
	}
	public boolean onOptionsItemSelected(MenuItem item) {	
		if(web_style==1){
			return false;
		}else{
			boolean result=new Common(this).menuAction(item, Webview.this);
			return result;
		}
	}
}