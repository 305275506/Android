package whjz.io.activity;

import com.whjz.io.CallBack;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @Package: jzhb.android.common
 * @ClassName: BaseHandler
 * @Description: TODO(√Ë ˆ)
 * @author caichaoxun
 * @date 2013-7-29 …œŒÁ10:54:26
 */
public class BaseHandler extends Handler {

	private CallBack callBack;

	private static BaseHandler handler = null;

	/**
	 * @Method: getInstance
	 * @Description: TODO(≥ı ºªØ)
	 * @param @param looper
	 * @param @param callBack
	 * @param @return
	 * @return BaseHandler
	 */
	public static BaseHandler getInstance(Looper looper, CallBack callBack) {
		if (handler == null)
			handler = new BaseHandler(looper, callBack);
		return handler;
	}

	public BaseHandler(Looper looper, CallBack callBack) {
		super(looper);
		this.callBack = callBack;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		callBack.execute();
	}

}
