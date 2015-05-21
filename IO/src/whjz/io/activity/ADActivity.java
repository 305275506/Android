package whjz.io.activity;

import com.whjz.io.CallBack;
import com.whjz.io.JniNative;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class ADActivity  extends Activity implements  Runnable{
	
	public static boolean RUNFLAG=false;
	private TextView ad_val01=null;
	private TextView ad_val02=null;
	private TextView ad_val03=null;
	private TextView ad_val04=null;
	private TextView ad_val05=null;
	private TextView ad_val06=null;
	private TextView ad_val07=null;
	private TextView ad_val08=null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad);
		
		ad_val01=(TextView) findViewById(R.id.ad_val01);
		ad_val02=(TextView) findViewById(R.id.ad_val02);
		ad_val03=(TextView) findViewById(R.id.ad_val03);
		ad_val04=(TextView) findViewById(R.id.ad_val04);
		ad_val05=(TextView) findViewById(R.id.ad_val05);
		ad_val06=(TextView) findViewById(R.id.ad_val06);
		ad_val07=(TextView) findViewById(R.id.ad_val07);
		ad_val08=(TextView) findViewById(R.id.ad_val08);
		
		Thread thread=new Thread(this);
		thread.start();
		
		findViewById(R.id.ad_read).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 
				RUNFLAG=true;
			}
		});
		
		findViewById(R.id.ad_read_stop).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RUNFLAG=false;
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		RUNFLAG=false;
	}
	
	@Override
	public void run() {
		while (true) {
			if (RUNFLAG) {
				BaseHandler handler = new BaseHandler(this.getMainLooper(),
						new CallBack() {
							@Override
							public void execute(Object arg0) {
							}
							@Override
							public void execute() {
								ad_val01.setText("第1路AD值："+JniNative.ADREAD(0));
								ad_val02.setText("第2路AD值："+JniNative.ADREAD(1));
								ad_val03.setText("第3路AD值："+JniNative.ADREAD(2));
								ad_val04.setText("第4路AD值："+JniNative.ADREAD(3));
								ad_val05.setText("第5路AD值："+JniNative.ADREAD(4));
								ad_val06.setText("第6路AD值："+JniNative.ADREAD(5));
								ad_val07.setText("第7路AD值："+JniNative.ADREAD(6));
								ad_val08.setText("第8路AD值："+JniNative.ADREAD(7));
								/*
								ad_val01.setText("第1路AD值："+System.currentTimeMillis());
								ad_val02.setText("第2路AD值："+System.currentTimeMillis());
								ad_val03.setText("第3路AD值："+System.currentTimeMillis());
								ad_val04.setText("第4路AD值："+System.currentTimeMillis());
								ad_val05.setText("第5路AD值："+System.currentTimeMillis());
								ad_val06.setText("第6路AD值："+System.currentTimeMillis());
								ad_val07.setText("第7路AD值："+System.currentTimeMillis());
								ad_val08.setText("第8路AD值："+System.currentTimeMillis());*/
								
							}
						});
				Message msg = new Message();
				handler.sendMessage(msg);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
