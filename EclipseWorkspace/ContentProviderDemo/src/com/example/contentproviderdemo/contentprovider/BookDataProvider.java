/**
 * 功能:内容提供者类，从sqlite中提供数据给应用，通过URI传递数据
 * 
 */
package com.example.contentproviderdemo.contentprovider;

import com.example.contentproviderdemo.sqlite.DbHelper;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class BookDataProvider extends ContentProvider {
	//定义标签，后面方法中会用到
	private static final String TABLE = "book_table";
	private static final String ID = "_id";
	private static final String NAME = "name";
	private static final String PRICE = "price";
	private static final int MULTI = 1;
	private static final int SINGLE = 2;
	private static final String AUTHORITY = "com.example.contentproviderdemo.bookdataprovider";

	private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private DbHelper dbHelper;
	
	//构建URI匹配对象，注册合法URI
	static {
		/**添加合法的Uri，注册Uri
		 * public void addURI (String authority, String path, int code) 
		 * authority:主机名、认证
		 * path ：路径
		 * code:返回码
		 */
		uriMatcher.addURI(AUTHORITY, "book", MULTI);  //多行查询
		uriMatcher.addURI(AUTHORITY, "book/#", SINGLE);  //单行查询，#是配对输入的数字
	}
	
	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return false;
	}
	
	/**
	 * 查询操作：
	 * Uri: 统一资源标示符
     * projection：字段列
     * selection：查询条件
     * selectionArgs：条件参数：占位符
     * sortOrder：排序
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//创建只读数据库
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//匹配URI,返回匹配码
		int code = uriMatcher.match(uri);
		switch (code) {
		case MULTI:
			return db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
		case SINGLE:
			//解析URI中的id
			long id = ContentUris.parseId(uri);
			if (selection==null) {  //当筛选条件为空
				selection = ID + "=" +id;
			} else {     //当筛选条件不为空
				selection = selection + "and" + ID + "=" +id;
			}
			return db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);

		default:
			db.close();
			throw new IllegalArgumentException("Uri非法");
			
		}
		
	}
	
	//删除记录
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int code = uriMatcher.match(uri);
		switch (code) {
			case SINGLE:
				long id = ContentUris.parseId(uri);  //把uri中的id解析出来
				//删除成功，返回删除了多少条记录
				int effectNum = db.delete(TABLE, ID+"=?", new String[]{String.valueOf(id)});
				db.close();
				return effectNum;
			default:
				db.close();
				throw new IllegalArgumentException("输入非法uri异常");
		}
	}
	
	//返回类型
	@Override
	public String getType(Uri uri) {
		int code=uriMatcher.match(uri);
		switch (code) {
			case MULTI:
				return "vnd.android.cursor.dir";
			case SINGLE:
				return "vnd.android.cursor.item";
	
			default:
				throw new IllegalArgumentException("Uri非法");
		}
	}

	//插入记录
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db  = dbHelper.getWritableDatabase();
		int code = uriMatcher.match(uri);
		switch(code) {
			case MULTI:
				long id = db.insert(TABLE, null, values);  //返回成功插入的id
				/**
				 * public static Uri withAppendedId (Uri contentUri, long id) 
				 * 在uri末尾追加指定的id，返回新生产的uri
				 */
				db.close();
				return ContentUris.withAppendedId(uri, id);  //成功插入后，返回插入的uri
			default:
				db.close();
				throw new IllegalArgumentException("输入非法uri异常");

		}

	}





	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db  = dbHelper.getWritableDatabase();
		int effect = db.update(TABLE, values, selection, selectionArgs);
		return effect;
	}

}
