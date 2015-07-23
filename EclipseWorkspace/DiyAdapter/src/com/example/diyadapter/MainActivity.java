/**
 * 自定义适配器
 * 需要继承BaseAdapter
 */
package com.example.diyadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.diyadapter.adapter.MyAdapter;
import com.example.diyadapter.model.ChatMessage;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	List<ChatMessage> chatList;
	//来自第三方软件的数据库的URI
	private static final String URI_PATH = "content://com.example.contentproviderdemo.bookdataprovider/book";
	private static final String NAME = "name";
	private static final String ID = "_id";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView chatListView = (ListView)findViewById(R.id.chatListView);
		chatList = new ArrayList<ChatMessage>();
		/**
		 * 1、设计item布局
		 *  2、初始化数据 
		 *  3、配置adapter 
		 *  4、装载adapter
		 */
		//访问第三方数据
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse(URI_PATH);
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while(cursor.moveToNext()) {
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.id = cursor.getInt(cursor.getColumnIndex(ID));
			chatMessage.message = cursor.getString(cursor.getColumnIndex(NAME));
			chatMessage.isSelf = chatMessage.id%2==0?true:false;
			chatList.add(chatMessage);
		}
		MyAdapter myAdapter = new MyAdapter(chatList, MainActivity.this, new int[]{R.layout.chatting_item_left,R.layout.chatting_item_right},new int[]{R.id.message,R.id.message});
		chatListView.setAdapter(myAdapter);
		
	}
}

