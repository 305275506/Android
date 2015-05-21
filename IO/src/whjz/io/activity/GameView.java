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
 * Copyright (c) 2012 All rights reserved ���ƣ�GameView.java
 * �������̳��Ը����viewֻ��Ҫ������ô���ƻ��� ��Ҫʵ��draw(Canvas canvas)���� ע�⣺ʵ�ֵ�draw�����в�Ҫ��������������
 * ��Ҫ�����쳣����
 * 
 * @author zhaoqp
 * @date��2012-10-30 ����4:42:32
 * @version v1.0
 */
@SuppressLint("WrongCall")
public abstract class GameView extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = "GameView";
	public ViewThread thread; // ˢ֡���߳�
	// ����SurfaceHolder����
	private SurfaceHolder mSurfaceHolder = null;
	public String fps = "FPS:N/A"; // ������ʾ֡���ʵ��ַ���������ʹ��
	private boolean loop = true;
	private boolean pause = true;
	// ˯�ߵĺ�����
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
		// ʵ����SurfaceHolder
		mSurfaceHolder = this.getHolder();
		// ��ӻص�
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);
		thread = new ViewThread(mSurfaceHolder, this);
		thread.start();
	}

	/**
	 * ����ˢ�µ�sleep���ʱ��
	 */
	public void setSleep(int time) {
		this.sleepSpan = time;
	}

	/**
	 * ����ѭ�����λ
	 * 
	 * @param loop
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * ����ѭ����ͣ���λ
	 * 
	 * @param pause
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * ��ͼ
	 */
	public abstract void onDraw(Canvas canvas);

	/**
	 * ��surface����ʱ��������չ����
	 */
	public void expandSurfaceCreated() {
	}

	/**
	 * ��surface����ʱ��������չ����
	 */
	public void expandSurfaceDestroyed() {
	}

	/**
	 * ��surface�Ĵ�С�����ı�ʱ����
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * ��surface����ʱ����
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// �����̨�ػ��߳�û����,��������
		if (!this.thread.isAlive()) {
			try {
				// ����ˢ֡�߳�
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
	 * ��surface����ʱ����
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		releaseViewThread();
		Log.d(TAG, "--surfaceDestroyed--");
	}

	/**
	 * �ͷ�view���߳�
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
	 * �������¼�
	 * 
	 * @param event
	 */
	public void onMyTouchEvent(MotionEvent event) {
	}

	/**
	 * �����¼�����
	 * 
	 * @param keyCode
	 * @param event
	 */
	public void onMyKeyDown(int keyCode, KeyEvent event) {
	}

	/**
	 * �����¼�̧��
	 * 
	 * @param keyCode
	 * @param event
	 */
	public void onMyKeyUp(int keyCode, KeyEvent event) {
	}

	/**
	 * �����¼�
	 * 
	 * @param event
	 */
	public void onMyTrackballEvent(MotionEvent event) {
	}

	/**
	 * ˢ֡�߳�
	 */
	class ViewThread extends Thread {

		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		private int count = 0; // ��¼֡�����ñ������ڼ���֡����
		private long start = System.nanoTime(); // ��¼��ʼʱ�䣬�ñ������ڼ���֡����

		/**
		 * ���췽��
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
							// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
							canvas = this.surfaceHolder.lockCanvas();
							synchronized (this.surfaceHolder) {
								gameView.onDraw(canvas);// ����
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						Thread.currentThread().interrupt();
						loop = false;
					} finally {
						if (canvas != null) {
							// ������Ļ��ʾ����
							this.surfaceHolder.unlockCanvasAndPost(canvas);
						}
					}
					this.count++;
					if (count == 20) { // �������20֡
						count = 0; // ��ռ�����
						long tempStamp = System.nanoTime(); // ��ȡ��ǰʱ��
						long span = tempStamp - start; // ��ȡʱ����
						start = tempStamp; // Ϊstart���¸�ֵ
						double fps = Math.round(100000000000.0 / span * 20) / 100.0;// ����֡����
						gameView.fps = "FPS:" + fps;// ���������֡�������õ�gameView����Ӧ�ַ���������
					}
					try {
						Thread.sleep(sleepSpan); // ˯��ָ��������
					} catch (Exception e) {
						e.printStackTrace(); // ��ӡ��ջ��Ϣ
					}
				}
			}
		}
	}
}