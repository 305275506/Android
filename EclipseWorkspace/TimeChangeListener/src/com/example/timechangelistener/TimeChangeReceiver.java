/**
 * 自定义广播接收器
 */
package com.example.timechangelistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeChangeReceiver extends BroadcastReceiver {
	// 当该广播接收器实例化后，回调该方法
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("0000000000000");
		if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED)) {
			Toast.makeText(context, "时间改变+++++++", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, "时区改变-------", Toast.LENGTH_LONG).show();
		}
		

	}

}
