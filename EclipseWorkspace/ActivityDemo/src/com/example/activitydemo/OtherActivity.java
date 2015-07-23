package com.example.activitydemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class OtherActivity extends Activity {
	public static final String TAG = "myActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "OtherActivity-onCreate");
		setContentView(R.layout.activity_other);
	}

	//进入Three
	public void enterThree(View v) {
		Intent intent = new Intent();
		intent.setClass(this, ThreeActivity.class);
		startActivity(intent);
	}

	//返回Main
	public void enterMain(View v) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}
	
	//进入自己
	public void enterOther(View v) {
		Intent intent = new Intent();
		intent.setClass(this, OtherActivity.class);
		startActivity(intent);
	}
	
	//关闭Activity返回数据
	public void returnData(View v) {
		Intent intent = new Intent();
		/**设置要返回的数据,数据封装在intent中
		 * resultCode：结果码
		 * data：Intent：存放要返回的数据
		 */
		intent.putExtra("myData", "关闭Activity返回数据");
		setResult(RESULT_OK, intent);
		finish();
	}
	
	@Override
	protected void onStart() {
		Log.v(TAG, "OtherActivity-onStart");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.v(TAG, "OtherActivity-onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.v(TAG, "OtherActivity-onPause");
		super.onPause();
	}
	
	@Override
	protected void onRestart() {
		Log.v(TAG, "OtherActivity-onRestart");
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		Log.v(TAG, "OtherActivity-onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.v(TAG, "OtherActivity-onDestroy");
		super.onDestroy();
	}

}
