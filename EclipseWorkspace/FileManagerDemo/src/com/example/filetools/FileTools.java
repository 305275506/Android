package com.example.filetools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileTools {
	private Context context;
	public FileTools(Context context) {
		this.context = context;
	}
	
	public void saveDataToPhone(String filename,String filebody) throws Exception {
		FileOutputStream fileOutStream = context.openFileOutput(filename, context.MODE_APPEND);
		fileOutStream.write(filebody.getBytes());
		fileOutStream.close();	
	}
	
	public String readDataFrom(String filename) throws IOException {
		FileInputStream fileInStream = context.openFileInput(filename);
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while(fileInStream.read(buffer)!=-1) {
			baos.write(buffer);
		}
		baos.close();
		fileInStream.close();
		return baos.toString();
	}
	
	public void saveDataToSD(String filename,String filebody) throws Exception {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File rootPath = Environment.getExternalStorageDirectory();
			File file = new File(rootPath,filename);
			FileOutputStream fileOutStream = new FileOutputStream(file);
			fileOutStream.write(filebody.getBytes());
			fileOutStream.close();
		    Toast.makeText(context, "写入数据到sd卡成功", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "找不到sd卡，数据写入手机", Toast.LENGTH_SHORT).show();
			saveDataToPhone(filename, filebody);
		}
	}
	
	public String readDataFromSD(String filename) throws Exception {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File rootPath = Environment.getExternalStorageDirectory();
			File file = new File(rootPath,filename);
			FileInputStream fileInStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while(fileInStream.read(buffer)!=-1) {
				baos.write(buffer);
			}
			baos.close();
			fileInStream.close();
			return baos.toString();
		} else {
			Toast.makeText(context, "找不到sd卡，请确认文件是否输入正确", Toast.LENGTH_SHORT).show();
			return null;
		}
	}

}
