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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Rs485Activity  extends Activity implements Runnable{
	
	
	private TextView reciveVal;
	private EditText sendVal;

	
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rs485);
		
		reciveVal=(TextView) findViewById(R.id.rs485_recive);
		sendVal=(EditText) findViewById(R.id.rs485_send);
		
		
		
		findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String val=sendVal.getText().toString();
				
				try {
					mFileOutputStream.write(val.getBytes(), 0, val.getBytes().length);
					mFileOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		new Thread(this).start();
	}

	public Rs485Activity() {
		super();
		mFd=JniNative.UART_OPEN("/dev/ttyO2", 1);
		JniNative.UART_SET_OPT(9600, 8, 'N', 1);
		if(mFd!=null){
			mFileInputStream = new FileInputStream(mFd);
			mFileOutputStream = new FileOutputStream(mFd);
		}else
			Toast.makeText(this, "开启串口失败", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void run() {
		while (true) {
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
											String txt=reciveVal.getText().toString();
											if(txt.length()>=10000)
												txt="接受区：";
											txt+=data;
											reciveVal.setText(txt);
										}
								 }
								
							}
						});
				Message msg = new Message();
				handler.sendMessage(msg);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			 
	}
}
