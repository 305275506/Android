package whjz.io.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	// 获取存储位置地址
	@SuppressLint("SdCardPath")
	 public static final String DATABASE_PATH = "/mnt/sdcard/test/";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.uart_activity).setOnClickListener(this);
		findViewById(R.id.rs485_activity).setOnClickListener(this);
		findViewById(R.id.gpio_activity).setOnClickListener(this);
		findViewById(R.id.ad_activity).setOnClickListener(this);
		findViewById(R.id.blutooth_activity).setOnClickListener(this);
		findViewById(R.id.zigbee_activity).setOnClickListener(this);
		findViewById(R.id.buzzer_activity).setOnClickListener(this);
		findViewById(R.id.temsensor_activity).setOnClickListener(this);
		findViewById(R.id.gprs_activity).setOnClickListener(this);
		findViewById(R.id.pwm_activity).setOnClickListener(this);
		findViewById(R.id.test).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.uart_activity:
			startActivity(new Intent(MainActivity.this, UartActivity.class));
			break;
		case R.id.rs485_activity:
			startActivity(new Intent(MainActivity.this, Rs485Activity.class));
			break;
		case R.id.gpio_activity:
			startActivity(new Intent(MainActivity.this, GpioActivity.class));
			break;
		case R.id.ad_activity:
			startActivity(new Intent(MainActivity.this, ADActivity.class));
			break;
		case R.id.zigbee_activity:
			startActivity(new Intent(MainActivity.this, ZigbeeActivity.class));
			break;
		case R.id.buzzer_activity:
			startActivity(new Intent(MainActivity.this, BuzzerActivity.class));
			break;
		case R.id.temsensor_activity:
			startActivity(new Intent(MainActivity.this,
					TempSensorActivity.class));
			break;
		case R.id.gprs_activity:
			startActivity(new Intent(MainActivity.this, GprsActivity.class));
			break;
		case R.id.pwm_activity:
			break;
		default:
			break;
		}
	}

}
