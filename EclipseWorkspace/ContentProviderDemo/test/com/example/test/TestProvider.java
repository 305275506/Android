package com.example.test;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.contentproviderdemo.contentprovider.*;
import com.example.contentproviderdemo.sqlite.*;
public class TestProvider extends AndroidTestCase {
	//定义标签，后面方法中会用到
	private static final String TABLE = "book_table";
	private static final String ID = "_id";
	private static final String NAME = "name";
	private static final String PRICE = "price";
	
	private static final String URI_PATH = "content://com.example.contentproviderdemo.bookdataprovider/book";
	
	
	//增加测试
	public void testAdd() {
		DbTools dbTools = new DbTools(getContext());
		ContentValues values = new ContentValues();//相当于HashMap
		
		int actual = 0;;
		for (int i = 0; i < 6; i++) {
			values.put(NAME, "第"+(i+1)+"本书");
			values.put(PRICE, (int)(100*(Math.random())));

			actual = dbTools.add(values);
		}
		assertEquals(true, actual>0);
		System.out.println("value:"+actual);
	}
	
	//测试查询
	public void testSearch() {
		ContentResolver resolver = getContext().getContentResolver();  //获得内容访问对象
		Uri uri = Uri.parse(URI_PATH);  //通过路径解析Uri ，注意URI是java的类，Uri是android的类
		Cursor cursor = resolver.query(uri, null, null, null, "price desc");
		
		if(cursor==null){
			System.out.println("00000000");
		}
	
		while(cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex(NAME));
			int price = cursor.getInt(cursor.getColumnIndex(PRICE));
			System.out.println("name"+name+" price"+price);
		}
	}
	
	//测试增加,在目录中增加，选用MULTI
	public void testInsert() {
		ContentResolver resolver = getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(NAME, "北京的太阳");
		values.put(PRICE, 778);
		Uri uri = Uri.parse(URI_PATH);
		Uri uri2 = resolver.insert(uri, values);
		System.out.println("增加的id是"+ContentUris.parseId(uri2));
	}
	
	//修改测试
	public void testUpdate() {
		int id = 3;
		ContentResolver resolver = getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put(NAME,"测试");
		values.put(PRICE, 101);
		Uri uri = Uri.parse(URI_PATH+"/"+id);
		resolver.update(uri, values, "_id="+id, null);
		
	}
	
	//测试 获取系统提供的内容提供者  ，通话记录  ，第三方 
	public void testCallLog(){
		Uri uri = Uri.parse("content://call_log/calls");
		Cursor cursor=getContext().getContentResolver().query(uri, null, null, null, null);
	   while(cursor.moveToNext()){
		   System.out.println(cursor.getString(cursor.getColumnIndex("number")));
		   System.out.println(cursor.getString(cursor.getColumnIndex("date")));
		   System.out.println(cursor.getString(cursor.getColumnIndex("duration")));
	   }
	}

}
