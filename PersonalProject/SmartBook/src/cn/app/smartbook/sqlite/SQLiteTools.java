/**
 * 实现数据库的基本操作
 */
package cn.app.smartbook.sqlite;

import java.sql.SQLClientInfoException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteTools {
	
	/**
	 * 打开数据库
	 * @param name:数据库名字
	 * @return 返回一个数据库
	 */
	public SQLiteDatabase openSQL(Context context,String name){
		SQLiteDatabase sql = new SQLiteCreator(context, name).getWritableDatabase();
		return sql;
	}
	
	/**
	 * 增加表
	 * @param sql  数据库
	 * @param table  表名
	 * @param paraStr  参数，参照SQL语言
	 * @return  返回表名
	 */
	public String addTable(SQLiteDatabase sql,String table) {
		String sqlStr = "create table if not exists "+table+"(_id integer primary key autoincrement)";
		sql.execSQL(sqlStr);
		sql.close();
		return table;
	}
	
	public String addPara(SQLiteDatabase sql,String table,String para,String paraType) {
		String sqlStr = "alter table "+table+" add column "+para+" "+paraType;
		sql.execSQL(sqlStr);
		sql.close();
		return para;
	}
	
	

}
