package com.example.activitydemo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ImageView faceImage;
	public static final String TAG = "myActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "MainActivity-onCreate");
		setContentView(R.layout.activity_main);
		faceImage = (ImageView)findViewById(R.id.imageView1);
	}
	
	//设置头像
	public void setImage(View v) {
		//隐式调用
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("image/*");
		/**
		 * public void startActivityForResult (Intent intent, int requestCode) 
		 * 激活目标Activity，当目标Activity关闭后，返回结果给调用者
		 * intent：意图
		 * requestCode：请求码
		 */
		startActivityForResult(intent, 5);
	}
	
	//进入Other
	public void enterOther(View v) {
		Intent intent = new Intent();
		intent.setClass(this, OtherActivity.class);
		startActivity(intent);
		
	}
	
	//进入Other,并返回数据
	public void enterOtherForData(View v) {
		//显式调用
		Intent intent = new Intent();
		intent.setClass(this, OtherActivity.class);
		startActivityForResult(intent, 6);
	}
	
	/**
	 * 当目标Activity关闭后，Android框架再回调该方法，来处理返回值
	 * requestCode:请求码: 区分 业务请求方法
	 * resultCode：结果码 ：返回数据的状态 ：Activity.result_Ok 
	 * data ：返回的数据，封装在Intent中
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==5&&resultCode==RESULT_OK) {
			Uri uri = data.getData();
			faceImage.setImageURI(uri);
		}
		if (requestCode==6&&resultCode==RESULT_OK) {
			Toast.makeText(getApplicationContext(), data.getStringExtra("myData"), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onStart() {
		Log.v(TAG, "MainActivity-onStart");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.v(TAG, "MainActivity-onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.v(TAG, "MainActivity-onPause");
		super.onPause();
	}
	
	@Override
	protected void onRestart() {
		Log.v(TAG, "MainActivity-onRestart");
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		Log.v(TAG, "MainActivity-onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.v(TAG, "MainActivity-onDestroy");
		super.onDestroy();
	}


}
