/**
 * Intent的用法
 */
package com.example.intentsdemo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

/**
 * 访问百度网页
 */
	public void accessBaidu(View v) {
//		Toast.makeText(this, "访问百度", Toast.LENGTH_SHORT).show();
		//创建意图
		Intent intent = new Intent();
		//设置意图的动作
		intent.setAction(Intent.ACTION_VIEW);
		//设置意图要传递的数据,数据为Uri类型
		intent.setData(Uri.parse("http://www.baidu.com"));
		//添加要访问目标组件的类型
		intent.addCategory(Intent.CATEGORY_BROWSABLE);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		//执行意图
		startActivity(intent);
	}
	
	/**
	 * 访问第三方应用
	 */
	public void accessOtherActivity(View v)
	{
		/**
		 * 显式调用
		 * public Intent setClassName (Context packageContext, String className) 
		 * packageContext : 上下文
		 * className : 类名
		 */
		Intent intent = new Intent();
		intent.setClass(this, OtherActivity.class);
		startActivity(intent);
		
		/**
		 * 隐式调用
		 */
	}
	
	/**
	 * 传输额外的数据
	 * 两种方法
	 */
	public void putExtraData(View v) {
		Intent intent = new Intent();
		//方法一
		intent.putExtra("name1", "方法一");
		intent.putExtra("value1", 1);
		
		//方法二  
		Bundle bundle = new Bundle();  //bundle相当于HashMap
		bundle.putString("name2", "方法二");
		bundle.putInt("value2", 2);
		intent.putExtras(bundle);
		
		intent.setClass(this, OtherActivity.class);
		startActivity(intent);
	    
		
	}
}
