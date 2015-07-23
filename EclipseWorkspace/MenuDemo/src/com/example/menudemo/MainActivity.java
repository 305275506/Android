/**
 * 实现菜单设计的两种方式
 * 选择菜单
 * 1、通过代码
 * 2、通过配置xml文件
 * 上下文菜单
 */
package com.example.menudemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser.BookmarkColumns;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int GROUP_ID = 1;
	List<Map<String, Object>> bookList;
	Map<String, Object> bookData;
	ListView  bookListView;
	//来自第三方软件的数据库的URI
	private static final String URI_PATH = "content://com.example.contentproviderdemo.bookdataprovider/book";
	//定义对话框布局
	View dialogView;
	SimpleAdapter simpleAdapter;
	EditText nameEdit;
	EditText priceEdit;
	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bookListView = (ListView)findViewById(R.id.listView);
		bookList = new ArrayList<Map<String,Object>>();
		//创建数据访问类
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse(URI_PATH);
		//查询数据,返回查询到的游标
		Cursor cursor=resolver.query(uri, null, null, null, null);  
		//遍历游标,给bookList赋值
		while(cursor.moveToNext()) {
			System.out.println("000000000000000");
			bookData = new HashMap<String, Object>();
			bookData.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
			bookData.put("name", cursor.getString(cursor.getColumnIndex("name")));
			bookData.put("price", cursor.getInt(cursor.getColumnIndex("price")));
			bookList.add(bookData);
		}
		//创建适配器，把bookList
		simpleAdapter = new SimpleAdapter(getApplicationContext(), bookList, R.layout.book_item, new String[]{"name","price"},new int[]{R.id.name_txt,R.id.price_txt});
		//添加适配器到bookListView
		bookListView.setAdapter(simpleAdapter);
		
		/**
		 * 注册上下文菜单,有两种写法
		 * registerForContextMenu(View view);
 		 * bookList.setOnCreateContextMenuListener(this);
		 */
//		registerForContextMenu(bookListView);
		bookListView.setOnCreateContextMenuListener(this);
	}
	
	/**
	 * 创建上下文菜单
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		//通过适配器来获取上下文菜单被选中bookListView项的信息
		AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo)menuInfo;
		//获取选中的bookListView的位置，来确定被选中的item
		int position = adapterContextMenuInfo.position;
		//根据position来配对booklist的项
		bookData = bookList.get(position);
		// 创建上下文菜单属性
		menu.setHeaderIcon(R.drawable.f110);  //设置上下文菜单头部图标
		menu.setHeaderTitle("上下文菜单"+bookData.get("name"));  //设置上下文菜单头部标题
		menu.add(2, R.id.menu_edit_id, 1, "编辑");  //添加菜单项
		menu.add(2,R.id.menu_delete_id,2,"删除");    //添加菜单项
		System.out.println("1111111111111111111111");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	/**
	 * 监听上下文 菜单
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//获取选中项的信息
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = menuInfo.position;
		id = bookList.get(position).get("_id").toString();
		String name = bookList.get(position).get("name").toString();
		String price = bookList.get(position).get("price").toString().trim();
		// 监听MenuItem的id来判断那个菜单项被点击
		switch (item.getItemId()) {
		case R.id.menu_edit_id:
			//把对话框的布局转换成View对象
			dialogView = getLayoutInflater().inflate(R.layout.dialog_item, null);
			nameEdit = (EditText)dialogView.findViewById(R.id.dialog_name_txt);
			priceEdit = (EditText)dialogView.findViewById(R.id.dialog_price_txt);
			nameEdit.setText(name);
			priceEdit.setText(price);
//			Toast.makeText(getApplicationContext(), "编辑当前内容", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.f115)
			.setTitle("编辑对话框")
			.setView(dialogView)
			.setPositiveButton("确定", new AlertDialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//
					edit(id,nameEdit.getText().toString(),priceEdit.getText().toString().trim());
					Toast.makeText(getApplicationContext(), "确定对话框", Toast.LENGTH_SHORT).show();
					
				}
			})
			.setNegativeButton("取消", new AlertDialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "取消对话框", Toast.LENGTH_SHORT).show();
				}
			});
			AlertDialog dialog = builder.create();
		    dialog.show();
			break;
		case R.id.menu_delete_id:
			delete(id);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	

	/**
	 * 创建选择菜单，每个activity只创建一次，通过onCreateOptionsMenu(Menu menu)来创建
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		/**
//		 * 通过代码来实现菜单
//		 * add方法用于添加菜单项
//		 * public abstract MenuItem add (int groupId, int itemId, int order, int titleRes) 
//		 * groupId:菜单组id，用来标志同组，
//		 * itemId:菜单项id，在同组菜单项中的唯一标识，id值定义存放在ids.xml文件中
//		 * order:菜单项的排序，越小排在越前
//		 * titleRes:菜单项标题id，id值存放在string.xml
//		 * 返回值为新增的菜单项
//		 * 
//		 * setIcon方法设置图标
//		 * public abstract MenuItem setIcon (int iconRes)
//		 * iconRes:图片资源id 
//		 * 
//		 * setShowAsAction方法设置是否显示，默认一般都是隐藏,此方法需要更高版本支持，android 3.0后，
//		 * 可在Androidmanifest.xml中改最低版本为level 11
//		 * public abstract void setShowAsAction (int actionEnum)
//		 * actionEnum: MenuItem的显示方式
//		 *  SHOW_AS_ACTION_ALWAYS   总是显示
//		 *  SHOW_AS_ACTION_IF_ROOM   有空间就显示
//		 *  SHOW_AS_ACTION_NEVER     从不显示，这个是默认值
//		 *  SHOW_AS_ACTION_WITH_TEXT  带文字显示  
//		 *  
//		 *  分离操作栏
//		 *  修改Androidmanifest.xml文件中的activity属性
//		 *  uiOptions=“splitActionBarWhenNarrow”  窄屏分离操作栏
//		 *  
//		 */
//
//		menu.add(GROUP_ID, R.id.menu_edit_id, 1, R.string.menu_edit_title)
//		.setIcon(R.drawable.ppsgame_edit_selected)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);  //添加编辑菜单项
//		
//		menu.add(GROUP_ID, R.id.menu_delete_id, 2, R.string.menu_delete_tile)
//		.setIcon(R.drawable.exercise_option_f)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); //添加删除菜单项
		
		/**
		 * 通过设置main.xml文件来创建选择菜单
		 * public void inflate (int menuRes, Menu menu) 把菜单布局xml文件实例化为菜单项对象，并添加到menu中
		 * public MenuInflater getMenuInflater ()     Returns a MenuInflater with this context. 
		 * 
		 */
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	/**
	 * 监听选择菜单项被选中的状态
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//把对话框的布局转换成View对象
		dialogView = getLayoutInflater().inflate(R.layout.dialog_item, null);
		//获取item的id，判断选中哪个菜单项
		switch(item.getItemId()) {
		case R.id.menu_add_id:
//			Toast.makeText(getApplicationContext(), "编辑菜单项", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.f115)
			.setTitle("添加对话框")
			.setView(dialogView)
			.setPositiveButton("确定", new AlertDialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//
					EditText nameView = (EditText)dialogView.findViewById(R.id.dialog_name_txt);
					EditText priceView = (EditText)dialogView.findViewById(R.id.dialog_price_txt);
					add(nameView.getText().toString().trim(),Integer.parseInt(priceView.getText().toString().trim()));
					Toast.makeText(getApplicationContext(), "确定对话框", Toast.LENGTH_SHORT).show();
					
				}
			})
			.setNegativeButton("取消", new AlertDialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "取消对话框", Toast.LENGTH_SHORT).show();
				}
			});
			AlertDialog dialog = builder.create();
		    dialog.show();
			break;
		case R.id.menu_exit_id:
			finish();   //销毁当前Activity
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 菜单操作方法：增删改查
	 */
	private void add(String name,int price){	   //增加书籍
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("price", price);
		//创建数据访问类
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse(URI_PATH);
		resolver.insert(uri, values);
		reflush();
	}
	
	private void delete(String id) {     //删除书籍
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse(URI_PATH+"/"+id);
//		resolver.delete(uri, "_id="+id, null);
		resolver.delete(uri, null, new String[]{id});
		reflush();
		
	}
	
	private void edit(String id,String name,String price) {    //编辑书籍
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse(URI_PATH+"/"+id);
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("price", price);
		resolver.update(uri, values, "_id="+id, null);
		reflush();
	}
	
	private void reflush() {
		//创建数据访问类
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse(URI_PATH);
		//查询数据,返回查询到的游标
		Cursor cursor=resolver.query(uri, null, null, null, null);  
		//遍历游标,给bookList赋值
		bookList = new ArrayList<Map<String,Object>>();
		while(cursor.moveToNext()) {
			System.out.println("000000000000000");
			bookData = new HashMap<String, Object>();
			bookData.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
			bookData.put("name", cursor.getString(cursor.getColumnIndex("name")));
			bookData.put("price", cursor.getInt(cursor.getColumnIndex("price")));
			bookList.add(bookData);
		}
		//创建适配器，把bookList
		simpleAdapter = new SimpleAdapter(getApplicationContext(), bookList, R.layout.book_item, new String[]{"name","price"},new int[]{R.id.name_txt,R.id.price_txt});
		//添加适配器到bookListView
		bookListView.setAdapter(simpleAdapter);
	}

}
