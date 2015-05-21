package whjz.io.activity;

import com.whjz.io.JniNative;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class BuzzerActivity extends Activity implements OnClickListener{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buzzer);
		
		findViewById(R.id.buzzer_pwm_out).setOnClickListener(this);
		findViewById(R.id.buzzer_pwm_sped).setOnClickListener(this);
		findViewById(R.id.buzzer_pwm_duty).setOnClickListener(this);
		findViewById(R.id.buzzer_pwm_run).setOnClickListener(this);
		findViewById(R.id.buzzer_pwm_stop).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buzzer_pwm_out:
			JniNative.PWMBUZ_APP_PWM_OUT("/sys/class/pwm/ecap.0/request");
			break;
		case R.id.buzzer_pwm_sped:
			JniNative.PWMBUZ_SET_FRE("/sys/class/pwm/ecap.0/period_freq", "100");
			break;
		case R.id.buzzer_pwm_duty:
			JniNative.PWMBUZ_SET_DUTY("/sys/class/pwm/ecap.0/duty_percent", "50");
			break;
		case R.id.buzzer_pwm_run:
			JniNative.PWMBUZ_RUNORCLOSE("/sys/class/pwm/ecap.0/run", true);
			break;
		case R.id.buzzer_pwm_stop:
			JniNative.PWMBUZ_RUNORCLOSE("/sys/class/pwm/ecap.0/run", false);
			break;
		default:
			break;
		}
	}
}
