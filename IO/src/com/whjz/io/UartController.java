package com.whjz.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

/**
 * @Package: com.whjz.io
 * @ClassName: UartController
 * @Description: TODO(Uart������)
 * @author caichaoxun
 * @date 2013-12-5 ����1:41:13
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
	 * @Description: TODO(�򿪶�Ӧ���еĴ���) 
	 * @param  @param param ���ڲ���
	 * @param  @param ComPort ��Ҫ�򿪵Ĵ��ں�
	 * @param  @param call �ص�����
	 * @return void
	 */
	public synchronized static void OpenUart(UartParam param,int ComPort, CallBack call) {
		ComPort=Tools.ComChange(ComPort);
		try {
			//������Ҫ�����Ĵ��ں�
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
		 * ���ô��ڲ�����
		 */
		JniNative.UART_SET_OPT(param.getSpeed(),param.getnBit(),param.getnEve(),param.getnStop());
		call.execute(uart);
		//�رմ���
		JniNative.UART_CLOSE();
	}
	/**
	 * @Method: writeVal
	 * @Description: TODO(��������д������)
	 * @param @param sendData ��Ҫд�봮�ڵĶ�����������
	 * @param @return true д��ɹ� false д��ʧ��
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
	 * @Description: TODO(��ȡ��������)
	 * @param @param reciveData ���ն�ȡ���Ĵ������ݵĻ�����
	 * @param @return true ��ȡ�ɹ� false ��ȡʧ��
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
