package whjz.io.activity;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import com.whjz.io.CallBack;
import com.whjz.io.JniNative;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UartActivity  extends Activity implements OnClickListener,Runnable{

	
	private TextView uart_recive_val;
	private TextView uart_send_val;

	public static boolean RUNFLAG=false;
	
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;
	
	private Spinner com_port;
	private Spinner com_speed;
	private Spinner com_bit;
	private Spinner com_event;
	private Spinner com_stop;
	
	public static final String[] COMS = {"串口1", "串口2", "串口3", "串口4", "串口5", "串口6", "串口7", "串口8" };
	public static final String[] SPEED = { "1200bps", "2400bps", "4800bps", "9600bps", "19200bps", "38400bps", "57600bps", "115200bps" };
	// 串口数字位
	public static final String[] BIT = { "8", "7" };
	// 串口校验位
	public static final String[] EVENT = { "N", "O", "E" };
	// 串口停止位
	public static final String[] STOP = { "1", "2" };
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uart);
		
		uart_recive_val=(TextView)findViewById(R.id.uart_recive_val);
		uart_send_val=(TextView)findViewById(R.id.uart_send_val);
		
		findViewById(R.id.uart_send).setOnClickListener(this);
		findViewById(R.id.uart_clear).setOnClickListener(this);
		findViewById(R.id.uart_run).setOnClickListener(this);
		findViewById(R.id.uart_stop).setOnClickListener(this);
		
		com_port=(Spinner)findViewById(R.id.uart_com_port);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,COMS);   
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		com_port.setAdapter(adapter2);
		com_port.setVisibility(View.VISIBLE); 
		
		
		com_speed=(Spinner)findViewById(R.id.uart_com_speed);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SPEED);   
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		com_speed.setAdapter(adapter3);
		com_speed.setVisibility(View.VISIBLE); 
		
		com_bit=(Spinner)findViewById(R.id.uart_com_nbit);
		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,BIT);   
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		com_bit.setAdapter(adapter4);   
		
		com_event=(Spinner)findViewById(R.id.uart_com_nevent);
		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,EVENT);   
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		com_event.setAdapter(adapter5);   
		
		com_stop=(Spinner)findViewById(R.id.uart_com_top);
		ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,STOP);   
		adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);   
		com_stop.setAdapter(adapter6);   
		
		
		
		Thread thread=new Thread(this);
		thread.start();
	}

	
	/**
	 * 开启线程
	 */
	@Override
	protected void onResume() {
		super.onResume();
		RUNFLAG=true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		RUNFLAG=false;
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.uart_stop:
			RUNFLAG=false;
			JniNative.UART_CLOSE();
			break;
		case R.id.uart_clear:
			uart_recive_val.setText("");
			break;
		case R.id.uart_send:
			String send_val=uart_send_val.getText().toString();
			try {
				byte[] sendData=send_val.getBytes();
				Log.e("sendData", "sendData="+sendData);
				mFileOutputStream.write(sendData, 0, sendData.length);
				mFileOutputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.uart_run:
			int port=com_port.getSelectedItemPosition();
			String speed=com_speed.getSelectedItem().toString();
			speed=speed.replace("bps", "");
			String nbit=com_bit.getSelectedItem().toString();
			String event=com_event.getSelectedItem().toString();
			String stop=com_stop.getSelectedItem().toString();
			
			int sp=Integer.valueOf(speed);
			int bit=Integer.valueOf(nbit);
			int st=Integer.valueOf(stop);
			char ev=event.charAt(0);
			
			mFd=JniNative.UART_OPEN("/dev/ttyO1", 1);
			JniNative.UART_SEL(port);
			JniNative.UART_SET_OPT(sp, bit, ev, st);
			if(mFd!=null){
				mFileInputStream = new FileInputStream(mFd);
				mFileOutputStream = new FileOutputStream(mFd);
			}else
				Toast.makeText(this, "开启串口失败", Toast.LENGTH_SHORT).show();
			
			
			RUNFLAG=true;
			break;
		}
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
							@SuppressLint("SimpleDateFormat")
							@Override
							public void execute() {
								 if(mFileInputStream!=null){
										byte[] reciveData=new byte[1024];
										try {
											mFileInputStream.read(reciveData, 0,reciveData.length);
										} catch (IOException e) {
											e.printStackTrace();
										}
										String data=new String(reciveData);
										if(data!=null&&!data.trim().equals("")){
											String txt=uart_recive_val.getText().toString();
											txt+=data;
											uart_recive_val.setText(txt);
										}
								 }
								
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
