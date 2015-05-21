package whjz.io.activity;

import com.whjz.io.JniNative;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GpioActivity  extends Activity{
	
	public static boolean Flag=false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gpio);
		new TestThread().start();
		findViewById(R.id.gpio_in_dval).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Flag)
					Flag=false;
				else
					Flag=true;
			}
		});
		
		findViewById(R.id.gpio_out_gval).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				JniNative.GPIO_SETDIRECTION(1, 193);
				JniNative.GPIO_SETDIRECTION(1, 194);
				JniNative.GPIO_SETDIRECTION(1, 195);
				JniNative.GPIO_SETDIRECTION(1, 196);
				JniNative.GPIO_SETDIRECTION(1, 197);
				JniNative.GPIO_SETDIRECTION(1, 198);
				JniNative.GPIO_SETDIRECTION(1, 199);
				JniNative.GPIO_SETDIRECTION(1, 200);
				JniNative.GPIO_SETDIRECTION(1, 201);
				JniNative.GPIO_SETDIRECTION(1, 202);
				JniNative.GPIO_SETDIRECTION(1, 204);
				JniNative.GPIO_SETDIRECTION(1, 205);
				JniNative.GPIO_SETDIRECTION(1, 206);
				JniNative.GPIO_SETDIRECTION(1, 207);
				JniNative.GPIO_SETDIRECTION(1, 208);
				
				JniNative.GPIO_OUT_BATTRY(193, 1);
				JniNative.GPIO_OUT_BATTRY(194, 1);
				JniNative.GPIO_OUT_BATTRY(195, 1);
				JniNative.GPIO_OUT_BATTRY(196, 1);
				JniNative.GPIO_OUT_BATTRY(197, 1);
				JniNative.GPIO_OUT_BATTRY(198, 1);
				JniNative.GPIO_OUT_BATTRY(199, 1);
				JniNative.GPIO_OUT_BATTRY(200, 1);
				JniNative.GPIO_OUT_BATTRY(201, 1);
				JniNative.GPIO_OUT_BATTRY(202, 1);
				JniNative.GPIO_OUT_BATTRY(204, 1);
				JniNative.GPIO_OUT_BATTRY(205, 1);
				JniNative.GPIO_OUT_BATTRY(206, 1);
				JniNative.GPIO_OUT_BATTRY(207, 1);
				JniNative.GPIO_OUT_BATTRY(208, 1);
				JniNative.GPIO_OUT_BATTRY(209, 1);
				JniNative.GPIO_OUT_BATTRY(210, 1);
				JniNative.GPIO_OUT_BATTRY(211, 1);
				JniNative.GPIO_OUT_BATTRY(212, 1);
				JniNative.GPIO_OUT_BATTRY(213, 1);
				JniNative.GPIO_OUT_BATTRY(214, 1);
			}
		});
		
		findViewById(R.id.gpio_out_dval).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				JniNative.GPIO_OUT_BATTRY(193, 0);
				JniNative.GPIO_OUT_BATTRY(194, 0);
				JniNative.GPIO_OUT_BATTRY(195, 0);
				JniNative.GPIO_OUT_BATTRY(196, 0);
				JniNative.GPIO_OUT_BATTRY(197, 0);
				JniNative.GPIO_OUT_BATTRY(198, 0);
				JniNative.GPIO_OUT_BATTRY(199, 0);
				JniNative.GPIO_OUT_BATTRY(200, 0);
				JniNative.GPIO_OUT_BATTRY(201, 0);
				JniNative.GPIO_OUT_BATTRY(202, 0);
				JniNative.GPIO_OUT_BATTRY(204, 0);
				JniNative.GPIO_OUT_BATTRY(205, 0);
				JniNative.GPIO_OUT_BATTRY(206, 0);
				JniNative.GPIO_OUT_BATTRY(207, 0);
				JniNative.GPIO_OUT_BATTRY(208, 0);
				JniNative.GPIO_OUT_BATTRY(209, 0);
				JniNative.GPIO_OUT_BATTRY(210, 0);
				JniNative.GPIO_OUT_BATTRY(211, 0);
				JniNative.GPIO_OUT_BATTRY(212, 0);
				JniNative.GPIO_OUT_BATTRY(213, 0);
				JniNative.GPIO_OUT_BATTRY(214, 0);
			}
		});
	}
}

class TestThread extends Thread{

	@Override
	public void run() {
		super.run();
		while(true){
			if(GpioActivity.Flag){
				try {
					int val=JniNative.GPIO_IN_BATTRY(215);
					Log.e("==", "1端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(216);
					Log.e("==", "2端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(217);
					Log.e("==", "3端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(218);
					Log.e("==", "4端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(219);
					Log.e("==", "5端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(220);
					Log.e("==", "6端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(221);
					Log.e("==", "7端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(222);
					Log.e("==", "8端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(223);
					Log.e("==", "9端口电压值为："+val);
					val=JniNative.GPIO_IN_BATTRY(224);
					Log.e("==", "10端口电压值为："+val);
				
					sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}