package com.example.anr;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText moodEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("onCreated");
		moodEdit = (EditText)findViewById(R.id.editText1);
		
	}
	
	//上传数据，访问网络，耗时操作
//	public void upLoad(View v) {
//		System.out.println("upLoad");
//		try {
//			Thread.sleep(8000);  //休眠8秒
//		} catch (InterruptedException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 上传数据，访问网络，耗时操作
	 * 对于耗时的操作，应该开辟子线程
	 */
	public void upLoad(View v) {
		new Thread() {
			public void run() {
				System.out.println("upload+run");
				try {
					Thread.sleep(8000);  // 休眠8秒
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}.start();
		System.out.println("upload");
		
	}


}
