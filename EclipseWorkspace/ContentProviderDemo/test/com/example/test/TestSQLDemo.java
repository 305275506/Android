/**
 * 测试DbTools数据库工具类
 */
package com.example.test;

import java.util.List;
import java.util.Random;

import com.example.contentproviderdemo.sqlite.*;


import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

public class TestSQLDemo extends AndroidTestCase {
	//定义标签
	private static final String TABLE = "book_table";
	private static final String ID = "_id";
	private static final String NAME = "name";
	private static final String PRICE = "price";
	
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
	
	//删除测试
	public void testDele(){
		DbTools dbTools = new DbTools(getContext());
		int actual=dbTools.delete(4);
		assertEquals(1, actual);
	}
	
	//修改测试
	public void testReplace() {
		DbTools dbTools = new DbTools(getContext());
		ContentValues values = new ContentValues();
		values.put(ID, 3);
		values.put(NAME, "修改");
		values.put(PRICE, 1000);
		int actual=dbTools.replace(values);
		assertEquals(1, actual);
	}
	
	//查询测试
	public void testSearch() {
		DbTools dbTools = new DbTools(getContext());
		List<Book> books;
		books = dbTools.searchList();
		for(Book book:books) {
			System.out.println("id="+book.id);
			System.out.println("name="+book.name);
			System.out.println("price="+book.price);
		}
	}
	
	//查询测试
	public void testSearchCursor() {
		DbTools dbTools = new DbTools(getContext());
		Cursor cursor = dbTools.searchCursor();
		while(cursor.moveToNext()) {
			int temp = cursor.getInt(cursor.getColumnIndex(ID));
			System.out.println("=====id====:"+temp);
		}
	}
	
}
