package com.whjz.io;

/**
 * @Package: com.whjz.io
 * @ClassName: Tools
 * @Description: TODO(工具类)
 * @author caichaoxun
 * @date 2013-4-1 上午9:39:54
 */
public class Tools {
	/**
	 * @Method: getSerialSpeed 
	 * @Description: TODO(获取串口波特率) 
	 * @param  @param Speed
	 * @param  @return
	 * @return int
	 */
	public static int getSerialSpeed(String Speed){
		try {
			String[] speeds=Speed.split("bps");
			Integer spee=Integer.valueOf(speeds[0]);
			switch(spee){
			case 1200:
				return 1200;
			case 2400:
				return 2400;
			case 4800:
				return 4800;
			case 9600:
				return 9600;
			case 19200:
				return 19200;
			case 38400:
				return 38400;
			case 57600:
				return 57600;
			case 115200:
				return 115200;
			default:
				return 9600;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 9600;
		}
	}
	
	/**
	 * @Method: ComChange 
	 * @Description: TODO(描述) 
	 * @param  @param com
	 * @param  @return
	 * @return int
	 */
	public static int ComChange(int com){
		switch (com) {
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 6;
		case 8:
			return 7;
		default:
			break;
		}
		return -1;
	}
}
