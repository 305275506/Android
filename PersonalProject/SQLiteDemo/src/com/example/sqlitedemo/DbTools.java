/**
 * 数据库工具类，实现增删改查工作
 */
package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbTools {
	//定义标签
	private static final String TABLE = "book_table";
	private static final String ID = "_id";
	private static final String NAME = "name";
	private static final String PRICE = "price";
	
	//创建一个数据库辅助类
	private DbHelper dbHelper;
	
	public DbTools(Context context) {
		dbHelper = new DbHelper(context);
	}
	
	/**
	 * 插入记录
	 * ContentValues values  类似与HaspMap，存储一条记录的数据
	 */
	public int add(ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(); //打开一个可以写入内容的数据
		/**插入记录
		 * public long insert (String table, String nullColumnHack, ContentValues values) 
		 * table ：表明
		 * nullColumnHack：空字段回填 
		 * values:ContentValues:内容值，好比HashMap ，可以存放一条要插入的记录的数据   
		 * 返回long类型的id,成功插入数据的id
		 */
		Long id = db.insert(TABLE, null, values);
		db.close();
		return id.intValue();
	}

	/**
	 * 删除记录
	 */
	public int delete(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(); //打开一个可以写入内容的数据
		/**删除操作
		 * public int delete (String table, String whereClause, String[] whereArgs)
		 * table：表名
		 * whereClause:条件      ?为占位符 ，下面是判断条件数组中的参数，是否等于ID 
		 * whereArgs：条件参数  ：
		 * 返回删除的id数
		 */
		int effectNum = db.delete(TABLE, ID+"=?", new String[]{String.valueOf(id)});
		db.close();
		return effectNum;
	}
	
	/**
	 * 更改记录
	 */
	public int replace(ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase(); //打开一个可以写入内容的数据
		/**
		 * 更新操作
		 * public int update (String table, ContentValues values, String whereClause, String[] whereArgs)
		 * values.getAsString(ID)  通过键值查找value，并且返回的是String值 
		 */
		int effectNum = db.update(TABLE, values, ID+"=?", new String[]{values.getAsString(ID)});
		db.close();
		return effectNum;
	}
	
	/**
	 * 查找记录
	 * public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) 
	 * "price desc"
	 * 返回游标
	 */
	public Cursor searchCursor() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, null, null, null, null, "price desc");
		return cursor;
	}
	
	/**
	 * 查找记录
	 * 返回List
	 */
	public List<Book> searchList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, null, null, null, null, "price desc");
		List<Book> books = null;
		//获取返回的游标数
		if(cursor.getCount()>0) {
			books = new ArrayList<Book>();
			//刚开始游标指向标题栏
			while(cursor.moveToNext()) {
				Book book = new Book();
				book.id = cursor.getInt(cursor.getColumnIndex(ID));
				book.name = cursor.getString(cursor.getColumnIndex(NAME));
				book.price = cursor.getInt(cursor.getColumnIndex(PRICE));
				books.add(book);
			}
		}
		return books;
	}
	
}
