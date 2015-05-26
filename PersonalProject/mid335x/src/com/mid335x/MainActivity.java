package com.mid335x;


import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	final static long HIGH_SPEED = 500;
	final static long MED_SPEED = 1000;
	final static long LOW_SPEED = 1500;
	
	
	Button fanSpeedHighButton;
	Button fanSpeedMedButton;
	Button fanSpeedLowButton;
	Button fanSpeedAutoButton;
	ImageView fanImage;
	GameView gameView;
	
    private Animation	mAnimationRotate	= null;
	
	/* 定义Bitmap对象 */
	Bitmap				mBitQQ				= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//设置横屏
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		
		//设置全盘
		requestWindowFeature(Window.FEATURE_NO_TITLE);//禁止显示标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏	
		setContentView(R.layout.activity_main);
		
		fanSpeedHighButton = (Button)findViewById(R.id.button1);
		fanSpeedHighButton.setOnClickListener(new FanSpeedClickListener());
		
		fanSpeedMedButton = (Button)findViewById(R.id.button2);
		fanSpeedMedButton.setOnClickListener(new FanSpeedClickListener());
		
		fanSpeedLowButton = (Button)findViewById(R.id.button3);
		fanSpeedLowButton.setOnClickListener(new FanSpeedClickListener());
		
		fanSpeedAutoButton = (Button)findViewById(R.id.button4);
		fanSpeedAutoButton.setOnClickListener(new FanSpeedClickListener());
		
		fanImage = (ImageView)findViewById(R.id.imageView1);
		
		/* 装载资源 */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.small_fan0)).getBitmap();
		/* 装载动画布局 */
//		mAnimationRotate = AnimationUtils.loadAnimation(mContext,R.anim.rotate_animation);
//		mAnimationRotate = new RotateAnimation(0.0f, 359.0f, Animation.ABSOLUTE, mBitQQ.getWidth()/2, Animation.ABSOLUTE, mBitQQ.getHeight()/2);
		mAnimationRotate = new RotateAnimation(0.0f, 359.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

		mAnimationRotate.setDuration(HIGH_SPEED);
		mAnimationRotate.setRepeatCount(Animation.INFINITE);
		//设置匀速
		LinearInterpolator lip = new LinearInterpolator();
		mAnimationRotate.setInterpolator(lip);
		fanImage = (ImageView)findViewById(R.id.imageView1);
		
		/* 开始播放动画 */
		fanImage.startAnimation(mAnimationRotate);
		
		
//		MainActivity.this.runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while (true) {
//					// TODO Auto-generated method stub
//					fanImage.setImageResource(R.drawable.fan_small_3);
////					try {
////						Thread.sleep(500);
////						fanImage.setImageResource(R.drawable.fan_small_1);
////					} catch (InterruptedException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}
//				}
//				
//			}
//		});
		
		

	}

//	public void fanSpeedHigh(View v) {
//		fanSpeedHighButton = (Button)findViewById(R.id.button1);
//		Toast.makeText(MainActivity.this, "Fan Speed High", Toast.LENGTH_SHORT).show();
//		fanSpeedHighButton.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.fan_speed_high_off));
 // 	}
//	public void fanSpeedMed(View v) {
//		Toast.makeText(MainActivity.this, "Fan Speed Med", Toast.LENGTH_SHORT).show();
//	}
//	public void fanSpeedLow(View v) {
//		Toast.makeText(MainActivity.this, "Fan Speed Low", Toast.LENGTH_SHORT).show();
//	}
//	public void fanSpeedAuto(View v) {
//		Toast.makeText(MainActivity.this, "Fan Speed Auto", Toast.LENGTH_SHORT).show();
//	}
	
	 private class FanSpeedClickListener implements View.OnClickListener {
		Button button;
		int temp;
		public void onClick(View v) {
//			temp = R.id.button1;
//			button = (Button)findViewById(temp+1);
//			button.setBackground(MainActivity.this.getResources()
//					.getDrawable(R.drawable.fan_speed_b4_auto_on));
//			System.out.println("R_id:"+(R.id.button1 + 1));
//			System.out.println("get_id:"+v.getId());
			
			for(int i=0;i<4;i++) {
				button = (Button)findViewById(R.id.button1+i);
				if (v.getId()!=(R.id.button1 + i)) {
					button.setBackground(MainActivity.this.getResources()
							.getDrawable(R.drawable.fan_speed_b1_high_off + 2*i));
				} else {
					button.setBackground(MainActivity.this.getResources()
							.getDrawable(R.drawable.fan_speed_b1_high_on + 2*i));
				}
			}
			
			switch(v.getId()) {
			case R.id.button1:
				mAnimationRotate.setDuration(HIGH_SPEED);
				Toast.makeText(MainActivity.this, "Fan Speed High", Toast.LENGTH_SHORT).show();
				break;
			case R.id.button2:
				mAnimationRotate.setDuration(MED_SPEED);
				Toast.makeText(MainActivity.this, "Fan Speed High", Toast.LENGTH_SHORT).show();
				break;
			case R.id.button3:
				mAnimationRotate.setDuration(LOW_SPEED);
				Toast.makeText(MainActivity.this, "Fan Speed High", Toast.LENGTH_SHORT).show();
				break;
			case R.id.button4:
				Toast.makeText(MainActivity.this, "Fan Speed High", Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
//		private void changeButton() {
//			button.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.fan_speed_high_off));
//		}
		
	}
	
}


