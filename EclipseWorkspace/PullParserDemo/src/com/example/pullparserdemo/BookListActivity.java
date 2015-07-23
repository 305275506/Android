/**
 * 功能：显示book清单，包括书名和价格，界面用ListView来实现，数据从book.xml中读取，
 * 利用XmlPullParser来解释xml文件
 * 步骤：
 * 1、ListView界面布局，创建layout文件
 * 
 * pull 生产xml文件
 * 1、修改布局界面
 */
package com.example.pullparserdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.example.pullparserdemo.datamanager.DataPullParser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BookListActivity extends Activity {
	private List<Map<String,Object>> bookList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_list);
		/**
		 * 1、列表项布局创建。book_item.xml
		 * 2、初始化数据,List<Map<String,Object>>和来自xml的pull解析的数据
		 * 3、适配器，SimpleAdapter
		 */
		ListView bookListView = (ListView)findViewById(R.id.bookListView);
		
		try {
			//从assets中的xml获得数据输入流
			InputStream dataInputStream = getResources().getAssets().open("book.xml");
			
			try {
				//把数据流交给业务类处理，返回解析后的数据赋值给List
				bookList = DataPullParser.parserXml(dataInputStream);
				//创建适配器，完成数据在bookItem中的显示关系
				SimpleAdapter bookAdapter = new SimpleAdapter(this, bookList, R.layout.book_item,new String[]{"name","price"} , new int[]{R.id.bookName,R.id.bookPrice});
				//bookListView设置适配器，完成显示界面
				bookListView.setAdapter(bookAdapter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//导出XML文件,处理两个数据List<Map<String,Object>>和FileOutputStream
	public void exportXml(View v) {
		//获得手机内部缓存路径
		File rootPath = this.getCacheDir();
		File file = new File(rootPath, "myBookList.xml");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			//在业务类中构建处理方法
			try {
				DataPullParser.exportXmlData(bookList,fos);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
