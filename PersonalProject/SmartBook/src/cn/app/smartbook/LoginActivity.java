package cn.app.smartbook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	/**
	 * 登录函数
	 */
	public void login(View view) {
		
	}
	
	/**
	 * 注册函数
	 * 说明：注册用户数据，存储在sql数据库里面，每个用户数据包括：ID、用户名、密码
	 */
	public void register(View view) {
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		startActivity(intent);
	}

}
