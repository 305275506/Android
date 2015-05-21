package whjz.io.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * Copyright (c) 2012 All rights reserved 名称：GameView.java
 * 描述：继承自该类的view只需要考虑怎么绘制画面 需要实现draw(Canvas canvas)方法 注意：实现的draw方法中不要锁定、解锁画布
 * 不要进行异常处理
 * 
 * @author zhaoqp
 * @date：2012-10-30 下午4:42:32
 * @version v1.0
 */
@SuppressLint("WrongCall")
public abstract class GameView extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = "GameView";
	public ViewThread thread; // 刷帧的线程
	// 定义SurfaceHolder对象
	private SurfaceHolder mSurfaceHolder = null;
	public String fps = "FPS:N/A"; // 用于显示帧速率的字符串，调试使用
	private boolean loop = true;
	private boolean pause = true;
	// 睡眠的毫秒数
	private int sleepSpan = 100;

	public GameView(Context context) {
		super(context);
		init();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		// Log.d(TAG, "--GameView Created--");
		// 实例化SurfaceHolder
		mSurfaceHolder = this.getHolder();
		// 添加回调
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);
		thread = new ViewThread(mSurfaceHolder, this);
		thread.start();
	}

	/**
	 * 设置刷新的sleep间隔时间
	 */
	public void setSleep(int time) {
		this.sleepSpan = time;
	}

	/**
	 * 设置循环标记位
	 * 
	 * @param loop
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * 设置循环暂停标记位
	 * 
	 * @param pause
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * 绘图
	 */
	public abstract void onDraw(Canvas canvas);

	/**
	 * 在surface创建时激发的扩展方法
	 */
	public void expandSurfaceCreated() {
	}

	/**
	 * 在surface创建时激发的扩展方法
	 */
	public void expandSurfaceDestroyed() {
	}

	/**
	 * 在surface的大小发生改变时激发
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * 在surface销毁时激发
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 如果后台重绘线程没起来,就启动它
		if (!this.thread.isAlive()) {
			try {
				// 启动刷帧线程
				this.setLoop(true);
				this.setPause(true);
				this.thread.start();
				expandSurfaceCreated();
			} catch (Exception e) {
				e.printStackTrace();
				this.setLoop(false);
				this.setPause(false);
			}
		}
		Log.d(TAG, "--surfaceCreated--");
	}

	/**
	 * 在surface销毁时激发
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		releaseViewThread();
		Log.d(TAG, "--surfaceDestroyed--");
	}

	/**
	 * 释放view类线程
	 */
	public void releaseViewThread() {
		if (thread != null && thread.isAlive()) {
			this.setPause(false);
			this.setLoop(false);
			try {
				thread.interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		thread = null;
	}

	/**
	 * 触摸屏事件
	 * 
	 * @param event
	 */
	public void onMyTouchEvent(MotionEvent event) {
	}

	/**
	 * 按键事件按下
	 * 
	 * @param keyCode
	 * @param event
	 */
	public void onMyKeyDown(int keyCode, KeyEvent event) {
	}

	/**
	 * 按键事件抬起
	 * 
	 * @param keyCode
	 * @param event
	 */
	public void onMyKeyUp(int keyCode, KeyEvent event) {
	}

	/**
	 * 滚动事件
	 * 
	 * @param event
	 */
	public void onMyTrackballEvent(MotionEvent event) {
	}

	/**
	 * 刷帧线程
	 */
	class ViewThread extends Thread {

		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		private int count = 0; // 记录帧数，该变量用于计算帧速率
		private long start = System.nanoTime(); // 记录起始时间，该变量用于计算帧速率

		/**
		 * 构造方法
		 * 
		 * @param surfaceHolder
		 * @param gameView
		 */
		public ViewThread(SurfaceHolder surfaceHolder, GameView gameView) {
			this.surfaceHolder = surfaceHolder;
			this.gameView = gameView;
		}

		@Override
		public void run() {
			Canvas canvas;
			while (loop) {
				while (pause) {
					canvas = null;
					try {
						if (!Thread.currentThread().isInterrupted()) {
							// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
							canvas = this.surfaceHolder.lockCanvas();
							synchronized (this.surfaceHolder) {
								gameView.onDraw(canvas);// 绘制
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						Thread.currentThread().interrupt();
						loop = false;
					} finally {
						if (canvas != null) {
							// 更新屏幕显示内容
							this.surfaceHolder.unlockCanvasAndPost(canvas);
						}
					}
					this.count++;
					if (count == 20) { // 如果计满20帧
						count = 0; // 清空计数器
						long tempStamp = System.nanoTime(); // 获取当前时间
						long span = tempStamp - start; // 获取时间间隔
						start = tempStamp; // 为start重新赋值
						double fps = Math.round(100000000000.0 / span * 20) / 100.0;// 计算帧速率
						gameView.fps = "FPS:" + fps;// 将计算出的帧速率设置到gameView的相应字符串对象中
					}
					try {
						Thread.sleep(sleepSpan); // 睡眠指定毫秒数
					} catch (Exception e) {
						e.printStackTrace(); // 打印堆栈信息
					}
				}
			}
		}
	}
}