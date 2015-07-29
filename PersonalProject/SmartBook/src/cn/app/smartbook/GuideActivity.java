/**
 * 登陆引导界面
 * 1、点击进入主界面
 * 2、判断是否第一次登录
 */
package cn.app.smartbook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class GuideActivity extends Activity {
	//是否第一次登录
	private boolean isFirstLoign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(isFirstLoign==true) {
			setContentView(R.layout.activity_login);
		} else {
			loign();
		}
		
	}
	
	//启动登录界面
	private void loign() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		
	}

	public void enter(View view) {
		System.out.println("点击进入");
		
	}



}
