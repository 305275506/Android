package com.mid335x;


import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.*;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener{
	final static long HIGH_SPEED = 500;
	final static long MED_SPEED = 900;
	final static long LOW_SPEED = 1200;
	int[] big_black_num = new int[10];
	Button fanSpeedHighButton;
	Button fanSpeedMedButton;
	Button fanSpeedLowButton;
	Button fanSpeedAutoButton;
	ImageView fanImage,bigTemFir,bigTemSec;
	GameView gameView;
	FrameLayout f1,f2;
	VerticalSeekBar vSeekerBar;
	LinearLayout linearLayout1,linearLayout2;
	
	
	
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
		
		//初始化数字数组
		for(int i=0;i<10;i++) {
			big_black_num[i] = R.drawable.big_black_0 + i;
		}
		bigTemFir = (ImageView)findViewById(R.id.imageView2);
		bigTemSec = (ImageView)findViewById(R.id.imageView3);
		
		//设置温度拖动条
		vSeekerBar = new VerticalSeekBar(this);
		vSeekerBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
		vSeekerBar.setMax(24);
		vSeekerBar.setProgress(12);
		vSeekerBar.setOnSeekBarChangeListener(this);
		linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
		linearLayout1.addView(vSeekerBar);
		
		
		fanSpeedHighButton = (Button)findViewById(R.id.button1);
		fanSpeedHighButton.setOnClickListener(new FanSpeedClickListener());
		
		fanSpeedMedButton = (Button)findViewById(R.id.button2);
		fanSpeedMedButton.setOnClickListener(new FanSpeedClickListener());
		
		fanSpeedLowButton = (Button)findViewById(R.id.button3);
		fanSpeedLowButton.setOnClickListener(new FanSpeedClickListener());
		
		fanSpeedAutoButton = (Button)findViewById(R.id.button4);
		fanSpeedAutoButton.setOnClickListener(new FanSpeedClickListener());
		
//		fanImage = (ImageView)findViewById(R.id.imageView1);
		
		/* 装载资源 */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.fan_small_0)).getBitmap();
		/* 装载动画布局 */
//		mAnimationRotate = AnimationUtils.loadAnimation(mContext,R.anim.rotate_animation);
//		mAnimationRotate = new RotateAnimation(0.0f, 359.0f, Animation.ABSOLUTE, mBitQQ.getWidth()/2, Animation.ABSOLUTE, mBitQQ.getHeight()/2);
		mAnimationRotate = new RotateAnimation(0.0f, 361.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

		mAnimationRotate.setDuration(HIGH_SPEED);
		mAnimationRotate.setRepeatCount(Animation.INFINITE);
		//设置匀速
		LinearInterpolator lip = new LinearInterpolator();
		mAnimationRotate.setInterpolator(lip);
		
		fanImage = (ImageView)findViewById(R.id.imageView1);
		
		/* 开始播放动画 */
		fanImage.startAnimation(mAnimationRotate);
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
			f1 = (FrameLayout)findViewById(R.id.frameLayout1);
			f2 = (FrameLayout)findViewById(R.id.frameLayout2);
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
	//			mAnimationRotate.setDuration(LOW_SPEED);
				f2.setVisibility(View.VISIBLE);
				f1.setVisibility(View.GONE);
				Toast.makeText(MainActivity.this, "Fan Speed High", Toast.LENGTH_SHORT).show();
				break;
			case R.id.button4:
				f1.setVisibility(View.VISIBLE);
				f2.setVisibility(View.GONE);
				Toast.makeText(MainActivity.this, "Fan Speed High", Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
//		private void changeButton() {
//			button.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.fan_speed_high_off));
//		}
		
	}


	 
	 //实现SeekBar接口方法
@Override
public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	// TODO Auto-generated method stub
	
	
	bigTemFir.setImageResource(big_black_num[(progress+62)/10]);
	bigTemSec.setImageResource(big_black_num[(progress+62)%10]);
	System.out.println("当前值"+progress);
	
	
}



@Override
public void onStartTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	
}



@Override
public void onStopTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	
}

public void temPlus(View v) {
	if(vSeekerBar.getProgress()<24) {
		vSeekerBar.setProgress(vSeekerBar.getProgress()+1);
	}
	
}

public void temMinus(View v) {
	if(vSeekerBar.getProgress()>0) {
		vSeekerBar.setProgress(vSeekerBar.getProgress()-1);
	}
}
	
}


