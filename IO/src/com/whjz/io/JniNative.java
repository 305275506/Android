package com.whjz.io;

import java.io.FileDescriptor;


/**
 * @Package: com.whjz.io
 * @ClassName: JniNative
 * @Description: TODO()
 * @author caichaoxun
 * @date 2013-12-10 ����3:05:28
 */
public class JniNative {
	
	static{
		System.loadLibrary("WHJZIO");
	}
	//gpio
	/**
	 * @Method: GPIO_SETDIRECTION 
	 * @Description: TODO(����GPIO����) 
	 * @param  @param DIRECTION
	 * @param  @param PORT
	 * @return void
	 */
	public static native void GPIO_SETDIRECTION(int DIRECTION,int PORT);
	/**
	 * @Method: GPIO_OUT_BATTRY 
	 * @Description: TODO(GPIO����ߵ͵�ѹ) 
	 * @param  @param PORT  ��Ҫ�����GPIO�˿ں�
	 * @param  @param VAL   0�ߵ�ѹ  1�͵�ѹ
	 * @return void 
	 */
	public static native void GPIO_OUT_BATTRY(int PORT,int VAL);
	/**
	 * @Method: GPIO_IN_BATTRY 
	 * @Description: TODO(��ȡGPIO����ĸߵ�) 
	 * @param  @param PORT
	 * @param  @return
	 * @return int
	 */
	public static native int GPIO_IN_BATTRY(int PORT);
	
	//uart
	
	/**
	 * @Method: UART_SEL 
	 * @Description: TODO(��·��������) 
	 * @param  @param UARTPORT ��Ҫ���õĴ��ں�
	 * @return void
	 */
	public static native void UART_SEL(int UARTPORT);
	/**
	 * @Method: UART_OPEN 
	 * @Description: TODO(�򿪶�Ӧ�Ĵ���) 
	 * @param  @param PATH  ��Ӧ�Ĵ��ں�
	 * @param  @param FLAGS  
	 * @param  @return
	 * @return FileDescriptor
	 */
	public static native FileDescriptor UART_OPEN(String PATH,int FLAGS);
	public static native void UART_CLOSE( );
	/**
	 * @Method: UART_SET_OPT 
	 * @Description: TODO(���ö�Ӧ���ڵĲ���) 
	 * @param  @param nSpeed  ������
	 * @param  @param nBit    ����λ
	 * @param  @param nEvent  У��λ
	 * @param  @param nStop   ֹͣλ
	 * @return void
	 */
	public static native void UART_SET_OPT(int nSpeed,int nBit,char nEvent,int nStop);
	
	
	//��������PWM
	/**
	 * @Method: PWMBUZ_APP_PWM_OUT 
	 * @Description: TODO(����PWM���) 
	 * @param  @param PWM ��Ҫ�����PWM��
	 * @return void
	 */
	public static native void PWMBUZ_APP_PWM_OUT(String PWM);
	/**
	 * @Method: PWMBUZ_SET_FRE 
	 * @Description: TODO(����PWM���Ƶ��) 
	 * @param  @param PWM  ��Ҫ�����PWM��
	 * @param  @param SPEED  ��Ҫ�����Ƶ��
	 * @return void
	 */
	public static native void PWMBUZ_SET_FRE(String PWM,String SPEED);
	/**
	 * @Method: PWMBUZ_SET_DUTY 
	 * @Description: TODO(����PWM�����ռ�ձ�) 
	 * @param  @param PWM  ��Ҫ�����PWM��
	 * @param  @param duty  ռ�ձ�
	 * @return void
	 */
	public static native void PWMBUZ_SET_DUTY(String PWM,String DUTY);
	
	/**
	 * @Method: PWMBUZ_RUNORCLOSE 
	 * @Description: TODO(�������߹ر�һ��PWM����ź�) 
	 * @param  @param PWM  ��Ҫ������PWM��
	 * @param  @param flag  0�ر� 1����
	 * @return void
	 */
	public static native void PWMBUZ_RUNORCLOSE(String PWM,boolean FLAG);
	//�¶ȴ�����
	/**
	 * @Method: TEMP_START 
	 * @Description: TODO(�����¶ȴ�����) 
	 * @param  
	 * @return void
	 */
	public static native void TEMP_START();
	/**
	 * @Method: TEMP_CATTEMP 
	 * @Description: TODO(��ȡ��ǰ�¶�ֵ) 
	 * @param  @return
	 * @return int
	 */
	public static native int TEMP_CATTEMP();
	/**
	 * @Method: TEMP_CATSPEED 
	 * @Description: TODO(�鿴��ǰ�¶ȴ�����Ƶ��) 
	 * @param  @return
	 * @return int
	 */
	public static native int TEMP_CATSPEED();
 
	/**
	 * @Method: ADREAD 
	 * @Description: TODO(ADģ�����) 
	 * @param  @param ADPORT  AD�˿ں�
	 * @param  @return
	 * @return int        ģ������ת������������ֵ
	 */
	public static native int ADREAD(int ADPORT);
}
