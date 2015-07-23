/**
 * 功能：数据库辅助类，该类继承了SQLiteOpenHelper类，用于创建和更新数据库
 */
package com.example.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	//定义标签
	private static final String DB_NAME = "books.db";
	private static final int DB_VRERSION = 3;
	/**
	 * 构造方法:
	 * public SQLiteOpenHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) 
	 * contex：上下文
	 * name;数据库名字
	 * factory：游标工厂，用于产生游标对象，如果为null则才用默认值
	 * version：版本号，从1开始，当数据库比较旧时则调用onUpgrade方法，比较新的时候则调用downgrade方法
	 * 重写构造方法后，只需要传入context参数
	 */
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VRERSION);
	}
	
	/**
	 * 当数据库被创建时，调用该函数，表的创建和初始化也放在该函数中
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * sql语句,实现了创建表格，共三个参数，id，name，price
		 * id  列的名称，参数名
		 * integer   参数类型，这个代表整型
		 * primary key   该列为表的主键，本列的值必须唯一，数据库会自动索引该列
		 * autoincrement 仅用于整数列，当插入数据时，该列为null，则系统自动插入一个更大的标识符值，
		 */
		String sqlStr = "create table book_table(_id integer primary key autoincrement,name text,price integer)";
		db.execSQL(sqlStr);  //执行sql语句
		

	}

	/**
	 * 需要更新数据库是调用该方法，增删改查操作会涉及
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("onUpgrade");
		db.execSQL("drop table if exists book_table");
		String sqlStr = "create table book_table(_id integer primary key autoincrement,name text,price integer)";
		db.execSQL(sqlStr);  //执行sql语句
		
	}

}
