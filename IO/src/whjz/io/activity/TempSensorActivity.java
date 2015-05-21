package whjz.io.activity;

import com.whjz.io.JniNative;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TempSensorActivity  extends Activity{

	private boolean flag=false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.temp);
		
		findViewById(R.id.tem_start).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flag=true;
				JniNative.TEMP_START();
			}
		});
		
		findViewById(R.id.tem_read).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!flag)
					Toast.makeText(TempSensorActivity.this, "���ȿ����¶ȴ�������",  Toast.LENGTH_SHORT).show();
				else{
					int val=JniNative.TEMP_CATTEMP();
					Toast.makeText(TempSensorActivity.this, "��ǰ�¶���:"+val*0.001+" ��", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		findViewById(R.id.tem_speed).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!flag)
					Toast.makeText(TempSensorActivity.this, "���ȿ����¶ȴ�������",  Toast.LENGTH_SHORT).show();
				else{
					int val=JniNative.TEMP_CATSPEED();
					Toast.makeText(TempSensorActivity.this, "�¶ȴ�����Ƶ��:"+val, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
