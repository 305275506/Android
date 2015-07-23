/**
 * 功能:通过SQL数据库来提供ListView的数据
 */
package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.media.MediaRouter.SimpleCallback;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BooksActivity extends Activity {
	private ListView booksView;
	private Cursor cursor;
	private DbTools dbTools;
	List<Map<String,Object>> data;
	Map<String,Object> book;
	SimpleCursorAdapter sca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_books);
		
		booksView = (ListView)findViewById(R.id.bookListView);
		dbTools = new DbTools(this);
		cursor = dbTools.searchCursor();
//		data = new ArrayList<Map<String, Object>>();
//		book = new HashMap<String, Object>();
//		book.put("name", "人人乐");
//		book.put("price", 30);
//		data.add(book);
//		int id = cursor.getInt(cursor.getColumnIndex("_id"));
//		System.out.println("======id=======:"+id);
//		SimpleAdapter sa = new SimpleAdapter(this, data, R.layout.book_item, new String[]{"name","price"},new int[]{R.id.bookName,R.id.bookPrice});
		sca = new SimpleCursorAdapter(this, R.layout.book_item, cursor, new String[]{"name","price"},new int[]{R.id.bookName,R.id.bookPrice},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		booksView.setAdapter(sca);
		booksView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long id) {
				//长按后删除该项
				dbTools = new DbTools(BooksActivity.this);
				dbTools.delete((int)id);
				cursor.requery();//重新查询数据库
				sca.notifyDataSetChanged();//通知所有View组件，数据已经更新
				return false;
			}
		});
		
	}
	

}
