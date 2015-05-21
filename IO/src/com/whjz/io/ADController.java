package com.whjz.io;

import android.util.Log;


/**
 * @Package: com.whjz.io
 * @ClassName: ADController
 * @Description: TODO(ADģ�����ɼ�������)
 * @author caichaoxun
 * @date 2013-12-11 ����10:38:14
 */
public class ADController {

	private static String TAG="ADController";
	private ADController() {
		super();
	}
	
	public synchronized static int read_ad(int adport){
		int ad=0;
		try {
			ad=JniNative.ADREAD(adport);
		} catch (Exception e) {
			Log.e(TAG, " read_ad  erro.  erro is:"+e);
		}
		return ad;
	}
	
	
	
}
