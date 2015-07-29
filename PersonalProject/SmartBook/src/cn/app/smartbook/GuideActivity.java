/**
 * 登陆引导界面
 * 1、点击进入主界面
 * 2、判断是否第一次登录
 */
package cn.app.smartbook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;

public class GuideActivity extends Activity {
	//是否第一次登录
	private boolean isFirstLogin;
	//用sharepreference来记录软件信息
	private SharedPreferences  loginPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取得SharePreference对象,name:文件名,xml结尾,Mode,存取模式
		loginPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
		isFirstLogin = loginPreferences.getBoolean("isFirstLogin", true);
		//判断软件是否第一次登录
		if(isFirstLogin==true) {
			setContentView(R.layout.activity_guide);
			Editor preferenceEditor = loginPreferences.edit();
			preferenceEditor.putBoolean("isFirstLogin", false);
			preferenceEditor.commit();
		} else {
			login();
		}
		
	}
	
	//启动登录界面
	private void login() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		
	}

	//点击进入登录界面
	public void enter(View view) {
		System.out.println("点击进入");
		login();
		
	}



}
