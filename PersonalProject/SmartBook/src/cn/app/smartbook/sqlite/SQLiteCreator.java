package cn.app.smartbook.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteCreator extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	
	/**构造方法，在该方法中构建数据库
     * 
     * @param context ：上下文
     * @param name ：数据库名 ，有则打开，无则创建  books.db
     * @param factory :游标工厂 ，一般系统给予
     * @param version ：数据库的版本  ，大于0的整数  
     */
	public SQLiteCreator(Context context, String name) {
		super(context, name, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
