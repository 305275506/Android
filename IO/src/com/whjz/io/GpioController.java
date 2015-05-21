package com.whjz.io;

import android.util.Log;



/**
  * @ClassName: GpioController 
  * @Description: TODO(GPIO���ƹ�����) 
  * @author caichaoxun 
  * @date 2014-2-12 ����10:02:19
 */
public class GpioController {

	private static GpioController controller=null;
	private static String TAG="GpioController";
	
	/**
	  * @Title: getInstance 
	  * @Description: TODO(������һ�仰�����������������) 
	  * @param @return    �趨�ļ� 
	  * @return GpioController    �������� 
	  * @throws
	 */
	public static GpioController getInstance(){
		if(controller==null)
			controller=new GpioController();
		return controller;
	}
	/**
	  * @Title: Set_GPIO_OUT 
	  * @Description: TODO(���ƶ�ӦGPIO�˿ڵĵ�ѹֵ) 
	  * @param @param port  GPIO�˿ں�
	  * @param @param val   �����ѹֵ 0 ��gpio����͵�ѹ  1 ��gpio����ߵ�ѹ
	  * @param @return    �趨�ļ� 
	  * @return boolean    ��������   true���óɹ�  false����ʧ��
	  * @throws
	 */
	public boolean Set_GPIO_OUT(int port,int val){
		try {
			JniNative.GPIO_SETDIRECTION(0, port);
			JniNative.GPIO_OUT_BATTRY(port, val);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Set_GPIO_OUT  Erro is:"+e);
		}
		return false;
	}
	
	/**
	  * @Title: Get_GPIO_IN 
	  * @Description: TODO(��ȡGPIO����ĵ�ѹֵ) 
	  * @param @param port  
	  * @param @return    �趨�ļ� 
	  * @return int    �������� 
	  * @throws
	 */
	public int Get_GPIO_IN(int port){
		try {
			return JniNative.GPIO_IN_BATTRY(port);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Get_GPIO_IN  Erro is:"+e);
		}
		return 0;
	}
}
