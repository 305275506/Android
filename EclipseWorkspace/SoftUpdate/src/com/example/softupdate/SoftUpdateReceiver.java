/**
 * 广播接收器
 */
package com.example.softupdate;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Xml;

public class SoftUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		int newVersion = 0;
		int currentVersion = 0;//当前版本
		System.out.println("rrrrrrrrrrrrrrrrrrrrr");
		/**
		 * 发一个软件更新的通知
		 * 实现步骤：
		 * 1、准备一个广播接收器
		 * 订阅网络连接状态事件，
		 * 2、假如有联网，则访问服务端，解析version.xml
		 * 假如有新版本，则发通知
		 */
		
		//检测当前系统是否联网
		//获取网络连接状态的管理器
		ConnectivityManager connectivityManager = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//获取当前的网络信息
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo!=null) {
			if (networkInfo.isAvailable()) {
				//2、访问服务器，获取服务端的版本信息，先在assets中读取
				InputStream is;
				try {
					is = context.getAssets().open("version.xml");
					newVersion = parseVersion(is);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//3、获取当前应用版本的信息
				//取得包管理器
				PackageManager packageManage = context.getPackageManager();
				try {
					/**
					 * 取得当前的包信息
					 * packagename：包名
					 * flag：标志，取哪一个组件的信息
					 */
					PackageInfo packageInfo = packageManage.getPackageInfo(context.getPackageName(),
							PackageManager.GET_ACTIVITIES);
					currentVersion = packageInfo.versionCode; //取得当前版本号
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//4、假如有新版本，则发通知
				System.out.println("新版本"+newVersion+"旧版本"+currentVersion);
				if(newVersion>currentVersion) {
					//取得通知管理器
					NotificationManager notificationManager = (NotificationManager)
							context.getSystemService(Context.NOTIFICATION_SERVICE);
					//准备一个通知
					/**
					 * 准备一个通知
					 * icon：通知图标
					 * tickerText：滚动的文本
					 * when：通知发送的时间
					 * 
					 */
					Notification notification = new Notification(R.drawable.emoji_080, "有新版本···", System.currentTimeMillis());
					notification.defaults = Notification.DEFAULT_SOUND;//默认是声音提示
					notification.flags = Notification.FLAG_AUTO_CANCEL;//当用户点击了通知内容框后，该通知自动消失
					Intent intents = new Intent();
					//显式调用第三方的应用组件
					intent.setClassName("com.example.softupdate", "com.example.diyadapter.MainActivity");
					
					/**
					 * 满足某种条件触发的意图，当点击通知内容框后执行的意图
					 * context
					 * requestCode
					 * intents
					 * flags
					 */
					PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
					/**
					 * 设置稍后的事件信息，当下拉了通知状态栏后，进入通知内容框
					 * context：上下文
					 * contextTitle：内容框的标题
					 * contextText：内容框信息
					 * contextIntent：内容意图
					 */
					
					notification.setLatestEventInfo(context, "软件更新", "软件正在更新", contentIntent);
					
					/**
					 * 发通知
					 * id：表示该通知在通知管理器中的序列号
					 * notiication:通知
					 */
					notificationManager.notify(1,notification);
				}
				
			}
		}
	}

	//解析服务端或者本地xml文件，获取版本号
	private int parseVersion(InputStream is) {
		int versionCode =0;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is,"UTF-8");
			int eventType = parser.getEventType();
			while (eventType!=XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("VersionCode")) {
						try {
							versionCode = Integer.parseInt(parser.nextText());
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;

				default:
					break;
				}
				//指向下一个节点
			    try {
					eventType = parser.next();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
