package com.DooitResearch.kakaoPoll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;
import android.widget.Toast;

public class Common extends Activity {
	static String version = "";
	static Context context;
	static SharedPreferences spf;
	static SharedPreferences.Editor editor;
	static String id, phone, phone_id;	
	static int status, title, content;
	static boolean read_make, read_report;
	static final int HTTP_TIMEOUT = 5 * 1000;
	static HttpClient mHttpClient;
	static String report="http://app.dooit.co.kr/mypage/poll_report";
	static String regist="http://app.dooit.co.kr/survey/regist/poll_regist";
	static String join="http://app.dooit.co.kr/member/join1";
	static String found="http://m.dooit.co.kr/member/idpw";
	static String solution1="http://m.dooit.co.kr/page/dooit01";
	static String solution2="http://doooit.tistory.com/m/post/view/id/2";
	static String researchAD="http://doooit.tistory.com/m/post/view/id/44";
	static String current;
	public Common(Context context){
		Common.context=context;
		spf=PreferenceManager.getDefaultSharedPreferences(context);
		editor=spf.edit();
		id=spf.getString("id", null);
		phone=spf.getString("phone", null);
		phone_id=spf.getString("phone_id", null);
		read_make=spf.getBoolean("read_make",false);
		read_report=spf.getBoolean("read_result",false);
	}
	public static String titleCheck(String url){
		if(url.equalsIgnoreCase(report)){
			current="POLL 보관함";			
		}else if(url.equalsIgnoreCase(regist)){
			current="POLL 만들기";
		}else if(url.equalsIgnoreCase(join)){
			current="회원가입";			
		}else if(url.equalsIgnoreCase(found)){
			current="ID & PW 찾기";
		}else if(url.equalsIgnoreCase(solution1)){
			current="두잇이란?";
		}
		return current;
	}
	public static void goWebview(Context context, String url, int style){
		Intent intent=new Intent(context, Webview.class);
		intent.putExtra("url", url);
		intent.putExtra("web_style", style);
		context.startActivity(intent);
	}
	public static boolean customTitle(Context context, String title){
		((Activity) context).getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		final TextView leftText = (TextView)((Activity) context).findViewById(R.id.left_text);
		leftText.setText(title);
		return false;
	}
	public static boolean checkPreference(){
		if(id==null || phone==null|| phone_id==null)
			return false;
			return true;
	}
	public static void savePreference(String inputText) {
		editor.putString("id", inputText);
		editor.putString("phone", phone);
		editor.putString("phone_id", phone_id);
		editor.commit();
	}
	public static void saveReadState(int n){
		if(n==0){
			Log.e("Common.saveReadState","Error Occur");
		}else if(n==1){
			editor.putBoolean("read_make", true);
			read_make=true;
		}else if(n==2){
			editor.putBoolean("read_result", true);
			read_report=true;
		}
		editor.commit();
	}
	public static void deletePreference() {
		CookieSyncManager.createInstance(context);
		CookieSyncManager.getInstance();
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
			editor.clear();
			editor.commit();
	}
	public static boolean checkPhoneNum(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			phone_id = tm.getDeviceId();
			phone = tm.getLine1Number();
		if(phone_id==null || phone==null)
			return false;
			return true;
	}
	public static void centerToast(Context context, String message){
		Toast toast=Toast.makeText(context, message, Toast.LENGTH_LONG);
	      toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
	      toast.show();
	}
	public static void nToast(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	public static boolean checkNetwork(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    boolean isWifiConn = ni.isConnected(); 
	    ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    boolean isMobileConn = ni.isConnected(); 
	    if (isWifiConn == true || isMobileConn == true)
	    	return true;
	    	centerToast(context, "인터넷에 연결되지 않았습니다. \n\n연결 후 재실행 해주시기 바랍니다.");
			return false;
	}
	public boolean menuCreate(Menu menu){
		MenuItem menuItem1,menuItem2,menuItem3, menuItem4;
		menuItem1=menu.add(0, 1, 0, "두잇 홈페이지").setIcon(R.drawable.menu_home);
		menuItem1.setIntent(new Intent
				(Intent.ACTION_VIEW, Uri.parse("http://www.dooit.co.kr")));
		menuItem2=menu.add(0, 2, 0, "두잇앱 다운받기").setIcon(R.drawable.menu_down);
		menuItem2.setIntent(new Intent
				(Intent.ACTION_VIEW, Uri.parse("market://search?q=두잇서베이(DOOITSURVEY)")));
		menuItem3=menu.add(0, 3, 0, "의견보내기").setIcon(R.drawable.menu_mail);
		Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:info@dooit.co.kr"));
		intent.putExtra(Intent.EXTRA_SUBJECT, "투표서비스 이용자 의견보내기");
		menuItem3.setIntent(intent);
		menuItem4=menu.add(0, 4, 0, "로그아웃").setIcon(R.drawable.menu_logout);
		menuItem4.setIntent(new Intent(context, Login.class));
		return true;		
	}
	public boolean menuAction(MenuItem item,Context context) {
		if(item.getItemId()==4){
			deletePreference();
			((Activity) context).finish();
		}
		return false;
	}
	private static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);  
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}
	public static boolean executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);		
		cookieManager.removeAllCookie();	
		HttpEntity entity=null;
		try {
				HttpClient client = getHttpClient();
				HttpPost request = new HttpPost(url);
				request.addHeader("User-Agent", "DooitKakaoPollApp");
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters, HTTP.UTF_8);
				request.setEntity(formEntity);
				HttpResponse response = client.execute(request);
				entity=response.getEntity();			
				String res=EntityUtils.toString(entity);
				if(res.equalsIgnoreCase("true")){
					List<Cookie> cookies = ((DefaultHttpClient) client).getCookieStore().getCookies();
					if (!cookies.isEmpty()) {
						for (int i = 0; i < cookies.size(); i++) {
							String cookieString = cookies.get(i).getName() + "="
									+ cookies.get(i).getValue();
							cookieManager.setCookie("app.dooit.co.kr", cookieString);
					}
					CookieSyncManager.getInstance().sync();
				}
				return true;
			}
			}catch(Exception e){
				Log.e("Common.executehttpPost", "Error occur");
					return false;
			}
		return false;
	}
	public static String getNewVersion(Context context) {
		String result = "";
		try {
		   PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		   version = i.versionName;
		} catch(NameNotFoundException e) {
			return null;
		}
		try {
			result = getHttpData("http://app.dooit.co.kr/push/kakaopoll_version_check/"
					+ version + "/" + id);
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch(NullPointerException e) {
			return null;
		}
		if(result == null || result.equals("") || result.equals("error") || result.equals("null")) {
			return null;
		}
		return result;		
	}
	public static String getHttpData(String url) throws UnsupportedEncodingException {
		try {
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("User-Agent", "DooitKakaoPollApp;" + version);
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter("http.protocol.expect-continue", false);
			httpclient.getParams().setParameter("http.connection.timeout", 3000);
			httpclient.getParams().setParameter("http.socket.timeout", 3000);
			HttpResponse response = httpclient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) { 
                return null;
            }
			InputStream webdata = response.getEntity().getContent();
			if(webdata == null) {
				return null;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(webdata, "utf-8"));
			StringBuffer sb = new StringBuffer();
			try {
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				return sb.toString();
			} catch (IOException e) {
				return null;
			}	
		} catch (Exception e) {
			return null;
		}			
	}
}
