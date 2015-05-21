package com.whjz.io;

import android.util.Log;



/**
  * @ClassName: GpioController 
  * @Description: TODO(GPIO控制管理器) 
  * @author caichaoxun 
  * @date 2014-2-12 上午10:02:19
 */
public class GpioController {

	private static GpioController controller=null;
	private static String TAG="GpioController";
	
	/**
	  * @Title: getInstance 
	  * @Description: TODO(这里用一句话描述这个方法的作用) 
	  * @param @return    设定文件 
	  * @return GpioController    返回类型 
	  * @throws
	 */
	public static GpioController getInstance(){
		if(controller==null)
			controller=new GpioController();
		return controller;
	}
	/**
	  * @Title: Set_GPIO_OUT 
	  * @Description: TODO(控制对应GPIO端口的电压值) 
	  * @param @param port  GPIO端口号
	  * @param @param val   输出电压值 0 ：gpio输出低电压  1 ：gpio输出高电压
	  * @param @return    设定文件 
	  * @return boolean    返回类型   true设置成功  false设置失败
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
	  * @Description: TODO(获取GPIO输入的电压值) 
	  * @param @param port  
	  * @param @return    设定文件 
	  * @return int    返回类型 
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
