package com.whjz.io;

import java.io.FileDescriptor;


/**
 * @Package: com.whjz.io
 * @ClassName: JniNative
 * @Description: TODO()
 * @author caichaoxun
 * @date 2013-12-10 下午3:05:28
 */
public class JniNative {
	
	static{
		System.loadLibrary("WHJZIO");
	}
	//gpio
	/**
	 * @Method: GPIO_SETDIRECTION 
	 * @Description: TODO(设置GPIO方向) 
	 * @param  @param DIRECTION
	 * @param  @param PORT
	 * @return void
	 */
	public static native void GPIO_SETDIRECTION(int DIRECTION,int PORT);
	/**
	 * @Method: GPIO_OUT_BATTRY 
	 * @Description: TODO(GPIO输出高低电压) 
	 * @param  @param PORT  需要输出的GPIO端口号
	 * @param  @param VAL   0高电压  1低电压
	 * @return void 
	 */
	public static native void GPIO_OUT_BATTRY(int PORT,int VAL);
	/**
	 * @Method: GPIO_IN_BATTRY 
	 * @Description: TODO(获取GPIO输入的高低) 
	 * @param  @param PORT
	 * @param  @return
	 * @return int
	 */
	public static native int GPIO_IN_BATTRY(int PORT);
	
	//uart
	
	/**
	 * @Method: UART_SEL 
	 * @Description: TODO(多路串口设置) 
	 * @param  @param UARTPORT 需要启用的串口号
	 * @return void
	 */
	public static native void UART_SEL(int UARTPORT);
	/**
	 * @Method: UART_OPEN 
	 * @Description: TODO(打开对应的串口) 
	 * @param  @param PATH  对应的串口号
	 * @param  @param FLAGS  
	 * @param  @return
	 * @return FileDescriptor
	 */
	public static native FileDescriptor UART_OPEN(String PATH,int FLAGS);
	public static native void UART_CLOSE( );
	/**
	 * @Method: UART_SET_OPT 
	 * @Description: TODO(设置对应串口的参数) 
	 * @param  @param nSpeed  波特率
	 * @param  @param nBit    数据位
	 * @param  @param nEvent  校验位
	 * @param  @param nStop   停止位
	 * @return void
	 */
	public static native void UART_SET_OPT(int nSpeed,int nBit,char nEvent,int nStop);
	
	
	//蜂鸣器和PWM
	/**
	 * @Method: PWMBUZ_APP_PWM_OUT 
	 * @Description: TODO(申请PWM输出) 
	 * @param  @param PWM 需要输出的PWM号
	 * @return void
	 */
	public static native void PWMBUZ_APP_PWM_OUT(String PWM);
	/**
	 * @Method: PWMBUZ_SET_FRE 
	 * @Description: TODO(设置PWM输出频率) 
	 * @param  @param PWM  需要输出的PWM号
	 * @param  @param SPEED  需要输出的频率
	 * @return void
	 */
	public static native void PWMBUZ_SET_FRE(String PWM,String SPEED);
	/**
	 * @Method: PWMBUZ_SET_DUTY 
	 * @Description: TODO(设置PWM输出的占空比) 
	 * @param  @param PWM  需要输出的PWM号
	 * @param  @param duty  占空比
	 * @return void
	 */
	public static native void PWMBUZ_SET_DUTY(String PWM,String DUTY);
	
	/**
	 * @Method: PWMBUZ_RUNORCLOSE 
	 * @Description: TODO(开启或者关闭一个PWM输出信号) 
	 * @param  @param PWM  需要开启的PWM号
	 * @param  @param flag  0关闭 1开启
	 * @return void
	 */
	public static native void PWMBUZ_RUNORCLOSE(String PWM,boolean FLAG);
	//温度传感器
	/**
	 * @Method: TEMP_START 
	 * @Description: TODO(启动温度传感器) 
	 * @param  
	 * @return void
	 */
	public static native void TEMP_START();
	/**
	 * @Method: TEMP_CATTEMP 
	 * @Description: TODO(读取当前温度值) 
	 * @param  @return
	 * @return int
	 */
	public static native int TEMP_CATTEMP();
	/**
	 * @Method: TEMP_CATSPEED 
	 * @Description: TODO(查看当前温度传感器频率) 
	 * @param  @return
	 * @return int
	 */
	public static native int TEMP_CATSPEED();
 
	/**
	 * @Method: ADREAD 
	 * @Description: TODO(AD模数输出) 
	 * @param  @param ADPORT  AD端口号
	 * @param  @return
	 * @return int        模拟量所转换的数字量的值
	 */
	public static native int ADREAD(int ADPORT);
}
