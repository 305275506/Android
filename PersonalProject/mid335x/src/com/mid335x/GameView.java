package com.mid335x;

import android.R.anim;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class GameView extends View
{
	ImageView fanImage;
	/* 定义Rotate动画 */
	private Animation	mAnimationRotate	= null;
	
	/* 定义Bitmap对象 */
	Bitmap				mBitQQ				= null;
	
	Context mContext = null;
	public GameView(Context context)
	{
		super(context);
		
		
		/* 装载资源 */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.fan_small_0)).getBitmap();
		/* 装载动画布局 */
//		mAnimationRotate = AnimationUtils.loadAnimation(mContext,R.anim.rotate_animation);
		mAnimationRotate = new RotateAnimation(0.0f, 361.0f, Animation.ABSOLUTE, mBitQQ.getWidth()/2, Animation.ABSOLUTE, mBitQQ.getHeight()/2);
		mAnimationRotate.setDuration(500);
		mAnimationRotate.setRepeatCount(Animation.INFINITE);
		//设置匀速
		//LinearInterpolator lip = new LinearInterpolator();
		//mAnimationRotate.setInterpolator(lip);
//		fanImage = (ImageView)findViewById(R.id.imageView1);
		
		/* 开始播放动画 */
		this.startAnimation(mAnimationRotate);
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 绘制图片 */
		canvas.drawBitmap(mBitQQ, 0, 0, null);

	}
	

}

