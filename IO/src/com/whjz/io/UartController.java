package com.whjz.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

/**
 * @Package: com.whjz.io
 * @ClassName: UartController
 * @Description: TODO(Uart控制器)
 * @author caichaoxun
 * @date 2013-12-5 下午1:41:13
 */
public class UartController {

	private static UartController uart = new UartController();
	private static FileDescriptor mFd;
	private static FileInputStream mFileInputStream;
	private static FileOutputStream mFileOutputStream;
	private static String TAG = "UartController";
	public synchronized static UartController getInstance() {
		return uart;
	}
	/**
	 * @Method: OpenUart 
	 * @Description: TODO(打开对应序列的串口) 
	 * @param  @param param 串口参数
	 * @param  @param ComPort 需要打开的串口号
	 * @param  @param call 回调函数
	 * @return void
	 */
	public synchronized static void OpenUart(UartParam param,int ComPort, CallBack call) {
		ComPort=Tools.ComChange(ComPort);
		try {
			//设置需要开启的串口号
			JniNative.UART_SEL(ComPort);
		} catch (Exception e) {
			Log.e(TAG, "Set Start UartCom is erro! "+e);
			return;
		}
		try {
			mFd=JniNative.UART_OPEN("/dev/ttyO1", 1);
		} catch (Exception e) {
			Log.e(TAG, "open uart is erro! "+e);
			return;
		}
		mFileInputStream=new FileInputStream(mFd);
		mFileOutputStream=new FileOutputStream(mFd);
		
		/**
		 * 设置串口波特率
		 */
		JniNative.UART_SET_OPT(param.getSpeed(),param.getnBit(),param.getnEve(),param.getnStop());
		call.execute(uart);
		//关闭串口
		JniNative.UART_CLOSE();
	}
	/**
	 * @Method: writeVal
	 * @Description: TODO(往串口中写入数据)
	 * @param @param sendData 需要写入串口的二进制数据码
	 * @param @return true 写入成功 false 写入失败
	 * @return boolean
	 */
	public boolean writeVal(byte[] sendData) {
		if (mFileOutputStream == null)
			return false;
		try {
			mFileOutputStream.write(sendData, 0, sendData.length);
			mFileOutputStream.flush();
		} catch (Exception e) {
			Log.e(TAG, "write value  faild");
			return false;
		}
		return true;
	}

	/**
	 * @Method: readVal
	 * @Description: TODO(读取串口数据)
	 * @param @param reciveData 接收读取到的串口数据的缓冲区
	 * @param @return true 读取成功 false 读取失败
	 * @return boolean
	 */
	public boolean readVal(byte[] reciveData) {
		if (mFileInputStream == null)
			return false;
		try {
			mFileInputStream.read(reciveData, 0, reciveData.length);
		} catch (IOException e) {
			Log.e(TAG, "recive data  faild");
			return false;
		}
		return true;
	}

}
