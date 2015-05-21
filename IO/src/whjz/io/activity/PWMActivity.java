package whjz.io.activity;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.whjz.io.JniNative;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PWMActivity  extends Activity{
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JniNative.UART_SEL(0);
		mFd=JniNative.UART_OPEN("/dev/ttyO1", 1);
		JniNative.UART_SET_OPT(9600, 8, 'N', 1);
		if(mFd!=null){
			mFileInputStream = new FileInputStream(mFd);
			mFileOutputStream = new FileOutputStream(mFd);
		}else
			Toast.makeText(this, "¿ªÆô´®¿ÚÊ§°Ü", Toast.LENGTH_SHORT).show();
		
		while(true){
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(mFileInputStream!=null){
				byte[] reciveData=new byte[1024];
				try {
					mFileInputStream.read(reciveData, 0,reciveData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String data=new String(reciveData);
				Log.e("-------------", "reciveData="+data.trim());
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JniNative.UART_CLOSE();
	}
	
	
	
}
