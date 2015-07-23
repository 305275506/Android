package com.example.sharedpreferences;
/**
 * 功能:SharedPreference存储数据演示
 */
import com.example.sharedpreferences.utils.AESEncryptor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private static final String SEED = "myseed";
	private ImageButton login;
	private ImageButton register;
	private EditText account;
	private EditText password;
	private CheckBox rememPassword;
	private SharedPreferences sharePrefer;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		autoFill();
		Log.v("密文转换", password.getText().toString());
	}


	private void autoFill() {
		/**
		 * 根据复选框状态，自动填充帐号密码
		 */
		if(sharePrefer.getBoolean("isCheck", false)) {
			account.setText(sharePrefer.getString("account", null));
			try {
				password.setText(AESEncryptor.decrypt(SEED, sharePrefer.getString("password", null)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//设置复选框为选中状态
			rememPassword.setChecked(true);
		}
		
	}


	private void initView() {
		login = (ImageButton)findViewById(R.id.login);
		register = (ImageButton)findViewById(R.id.register);
		account = (EditText)findViewById(R.id.account);
		password = (EditText)findViewById(R.id.password);
		rememPassword = (CheckBox)findViewById(R.id.rememberPassword);
		sharePrefer = getSharedPreferences("user-info", MODE_PRIVATE);
		
	}
	
	public void login(View v) {
		String mAccount = account.getText().toString().trim();
		String mPassword = password.getText().toString().trim();
		if(TextUtils.isEmpty(mAccount)||TextUtils.isEmpty(mPassword)) {
			Toast.makeText(this, "帐号或者密码不能为空",Toast.LENGTH_SHORT).show();
		} else {
			remenInfo(mAccount,mPassword);
			Intent intent = new Intent();
			intent.setClass(this, WelcomeActivity.class);
			startActivity(intent);
		}
		
	}


	private void remenInfo(String account,String password) {
		Editor editor = sharePrefer.edit();
		/**
		 * 判断复选框是否选上
		 * 如果选上则把帐号、复选框状态和密码都保存到手机
		 * 否则只保存复选框状态
		 */
		if(rememPassword.isChecked()) {
			editor.putString("account", account);
			/**
			 * 给密码加密，加密工具AESEncryptor
			 */
			try {
				editor.putString("password", AESEncryptor.encrypt(SEED, password));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			editor.putBoolean("isCheck", true);
		} else {
			editor.putBoolean("isCheck", false);
		}
		editor.commit();

	}


}
