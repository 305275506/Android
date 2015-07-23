package com.example.filemanagerdemo;

import java.io.IOException;

import com.example.filetools.FileTools;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	EditText filename ;
	EditText filebody;
	FileTools filetools;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		filename = (EditText)findViewById(R.id.filename);
		filebody = (EditText)findViewById(R.id.filebody);
		filetools = new FileTools(this);
		
	}
	
	//写内存
	public void writeToPhone(View v) {
		String name = filename.getText().toString().trim();
		String body = filebody.getText().toString();
		
		if(name != null) {
			try {
				filetools.saveDataToPhone(name, body);
				Toast.makeText(this, "写入手机成功", Toast.LENGTH_SHORT).show();
				System.out.println("eeeeeee");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "请输入文件名", Toast.LENGTH_SHORT).show();
		}
	}
	
	//读内存
	public void readFromPhone(View v) {
		String name = filename.getText().toString().trim();
		String body;
		try {
			body = filetools.readDataFrom(name);
			filebody.setText(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//写sd卡
	public void writeToSD(View v) {
		String name = filename.getText().toString().trim();
		String body = filebody.getText().toString();
		try {
			filetools.saveDataToSD(name, body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//读sd卡
	public void readFromSD(View v) {
		String name = filename.getText().toString().trim();
		String body;
		try {
			if ((body = filetools.readDataFromSD(name))!=null) {
				filebody.setText(body);
				filebody.clearFocus();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
