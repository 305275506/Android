package com.example.activitydemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class ThreeActivity extends Activity {
	public static final String TAG = "myActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "ThreeActivity-onCreate");
		setContentView(R.layout.activity_three);
	}

	//返回Main
	public void enterMain(View v) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}
	
	//返回Other
	public void enterOther(View v) {
		Intent intent = new Intent();
		intent.setClass(this, OtherActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		Log.v(TAG, "ThreeActivity-onStart");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.v(TAG, "ThreeActivity-onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.v(TAG, "ThreeActivity-onPause");
		super.onPause();
	}
	
	@Override
	protected void onRestart() {
		Log.v(TAG, "ThreeActivity-onRestart");
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		Log.v(TAG, "ThreeActivity-onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.v(TAG, "ThreeActivity-onDestroy");
		super.onDestroy();
	}

}
