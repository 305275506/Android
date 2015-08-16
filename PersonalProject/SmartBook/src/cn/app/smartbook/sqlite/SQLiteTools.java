/**
 * 实现数据库的基本操作
 */
package cn.app.smartbook.sqlite;

import java.sql.SQLClientInfoException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	
	/**
	 * 增加表的参数项
	 * @param sql  数据库
	 * @param table  表名
	 * @param para  需要添加的参数项
	 * @param paraType  参数项的类型（integer、text）
	 * @return  返回参数项
	 */
	public String addPara(SQLiteDatabase sql,String table,String para,String paraType) {
		String sqlStr = "alter table "+table+" add column "+para+" "+paraType;
		sql.execSQL(sqlStr);
		sql.close();
		return para;
	}
	
	//插入操作
	public int insertValue(SQLiteDatabase sql,String table,ContentValues values) {
		/**插入记录
		 * table ：表明
		 * nullColumnHack：空字段回填 
		 * values:ContentValues:内容值，好比HashMap ，可以存放一条要插入的记录的数据    
		 *     id  ：1        key :id   value  1
		 *     name  :anroid  key  :name  value  android
		 *     price :20
		 */
		Long id=sql.insert(table, null, values);
		sql.close();
		return id.intValue();  //返回一个int类型的id值
	}
	
	//删除操作
	public int deleteValue(SQLiteDatabase sql,String table,String key,String value){
		/**删除操作
		 * table：表名
		 * whereClause:条件 
		 * whereArgs：条件参数  ：占位符  
			 */
		int effectNum=sql.delete(table, key+"=?", new String[]{value});
		sql.close();
		return effectNum;
	}
	
	//更新操作 ,更新某条记录
	public int update(SQLiteDatabase sql,String table,ContentValues values,String key,String value){
		// ContentValues  : key  id   name  price 		
		int effectNum=sql.update(table, values, key+"=?", new String[]{value});
		return effectNum;
	}
	
	//查询记录,返回的是游标
	public Cursor searchByCursor(SQLiteDatabase sql,String table){
		return sql.query(table, null, null, null, null, null, null);
//	    db.close();//返回游标，则不能关闭数据库 ，否则获取不到数据
			
	}
	
	

}
