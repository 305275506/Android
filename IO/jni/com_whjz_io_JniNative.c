#include <termios.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <jni.h>

#include <stdlib.h>
#include <sys/mman.h>
#include <termios.h>
#include <signal.h>
#include <unistd.h>
#include <ctype.h>
#include <errno.h>
#include <com_whjz_io_JniNative.h>


#include <android/log.h>
static const char *TAG = "WHJZIO";


static int UART_FD=-1;

#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)

/*
 * Method:    jstringTostring
 * Signature: ( )c jstring 转char数组类型
 */
char* jstringTostring(JNIEnv* env, jstring jstr)
{
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode =(*env)->NewStringUTF(env,"utf-8");
	jmethodID mid =(*env)->GetMethodID(env,clsstring, "getBytes", "(Ljava/lang/String;)[B");
	jbyteArray barr= (jbyteArray)(*env)->CallObjectMethod(env,jstr, mid, strencode);
	jsize alen = (*env)->GetArrayLength(env,barr);
	jbyte* ba = (*env)->GetByteArrayElements(env,barr, JNI_FALSE);
	if (alen > 0)
	{
		rtn = (char*)malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env,barr, ba, 0);
	return rtn;
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    GPIO_SETDIRECTION
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_GPIO_1SETDIRECTION
(JNIEnv *env, jobject class, jint direction, jint port)
{
	int gpio_fd;
	char buf[100];
	sprintf(buf,"/sys/class/gpio/gpio%d/direction",port);
	if ((gpio_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device /sys/class/gpio/gpio%d/direction  %s",port,strerror(errno));
		return;
	}
	if(direction==0)
		write(gpio_fd, "in", 2);
	else
		write(gpio_fd, "out", 3);
	close(gpio_fd);
}
/*
 * Class:     com_whjz_io_JniNative
 * Method:    GPIO_OUT_BATTRY
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY
(JNIEnv *env, jobject class, jint port, jint val)
{
	int gpio_fd;
	char buf[100];
	sprintf(buf,"/sys/class/gpio/gpio%d/value",port);
	if ((gpio_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device /sys/class/gpio/gpio%d/value %s",port,strerror(errno));
		return;
	}
	if(val==0)
		write(gpio_fd, "0", 1);
	else
		write(gpio_fd, "1", 1);
	close(gpio_fd);
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    GPIO_IN_BATTRY
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_whjz_io_JniNative_GPIO_1IN_1BATTRY
(JNIEnv *env, jobject class, jint port)
{
	int gpio_fd;
	char buf[100];
	sprintf(buf,"/sys/class/gpio/gpio%d/value",port);
	if ((gpio_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device /sys/class/gpio/gpio%d/value %s",port,strerror(errno));
		return;
	}
	char val[2];
	read(gpio_fd,val,sizeof(val));
	close(gpio_fd);
	return (jint)atoi(val);
}
/*
 * Class:     com_whjz_io_JniNative
 * Method:    UART_SEL
 * Signature: (I)Ljava/io/FileDescriptor;
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_UART_1SEL
  (JNIEnv *env, jclass class, jint uartport){
	switch(uartport){
	case 0:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,0);
		break;
	case 1:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,0);
		break;
	case 2:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,0);
		break;
	case 3:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,0);
		break;
	case 4:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,1);
		break;
	case 5:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,1);
		break;
	case 6:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,0);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,1);
		break;
	case 7:
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,114,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,115,1);
		Java_com_whjz_io_JniNative_GPIO_1OUT_1BATTRY(env,class,116,1);
		break;
	default:
		break;
	}
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    UART_OPEN
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jobject JNICALL Java_com_whjz_io_JniNative_UART_1OPEN
  (JNIEnv *env, jclass class, jstring path, jint flag){
	jobject mFileDescriptor;
	const char *path_utf =jstringTostring(env,path);
	UART_FD =open(path_utf, O_RDWR | O_NONBLOCK);
	if (UART_FD<=0)
	{
		LOGE("fial to open device  %s %s",path_utf,strerror(errno));
		return NULL;
	}
	jclass cFileDescriptor = (*env)->FindClass(env, "java/io/FileDescriptor");
	jmethodID iFileDescriptor = (*env)->GetMethodID(env, cFileDescriptor, "<init>", "()V");
	jfieldID descriptorID = (*env)->GetFieldID(env, cFileDescriptor, "descriptor", "I");
	mFileDescriptor = (*env)->NewObject(env, cFileDescriptor, iFileDescriptor);
	(*env)->SetIntField(env, mFileDescriptor, descriptorID, (jint)UART_FD);
	(*env)->ReleaseStringUTFChars(env, path, path_utf);
	return mFileDescriptor;
}
/*
 * Class:     com_whjz_io_JniNative
 * Method:    UART_CLOSE
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_UART_1CLOSE
  (JNIEnv *env, jclass class ){
	close(UART_FD);
}


/*
 * Class:     com_whjz_io_JniNative
 * Method:    UART_SET_OPT
 * Signature: (IICI)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_UART_1SET_1OPT
  (JNIEnv *env, jobject class,jint nSpeed,jint nBit,jchar nEvent,jint nStop){
	struct termios newtio,oldtio;
	if(tcgetattr(UART_FD,&oldtio) != 0){
		LOGE("fail to Setup Serial  1");
		return;
	}
	bzero(&newtio,sizeof(newtio));
	newtio.c_cflag |= CLOCAL | CREAD;
	newtio.c_cflag &= ~CSIZE;
	switch(nBit){
	case 5:
		newtio.c_cflag |= CS5;//数据位
		break;
	case 6:
		newtio.c_cflag |= CS6;//数据位
		break;
	case 7:
		newtio.c_cflag |= CS7;//数据位
		break;
	case 8:
		newtio.c_cflag |= CS8;//数据位
		break;

	}

	switch(nEvent){
	case 'O':
		newtio.c_cflag |= PARENB;//奇偶校验
		newtio.c_cflag |= PARODD;
		newtio.c_iflag |= (INPCK | ISTRIP);
		break;
	case 'E':
		newtio.c_iflag |= (INPCK | ISTRIP);
		newtio.c_cflag |= PARENB;
		newtio.c_cflag &= ~PARODD;
		break;
	case 'N':
		newtio.c_cflag &= ~PARENB;
		break;
	}

	switch(nSpeed)
	{
	case 1200:
		cfsetispeed(&newtio,B1200);//波特率
		cfsetospeed(&newtio,B1200);
		break;
	case 2400:
		cfsetispeed(&newtio,B2400);//波特率
		cfsetospeed(&newtio,B2400);
		break;
	case 4800:
		cfsetispeed(&newtio,B4800);//波特率
		cfsetospeed(&newtio,B4800);
		break;
	case 9600:
		cfsetispeed(&newtio,B9600);//波特率
		cfsetospeed(&newtio,B9600);
		break;

	case 19200:
		cfsetispeed(&newtio,B19200);//波特率
		cfsetospeed(&newtio,B19200);
		break;
	case 38400:
		cfsetispeed(&newtio,B38400);//波特率
		cfsetospeed(&newtio,B38400);
		break;
	case 57600:
		cfsetispeed(&newtio,B57600);//波特率
		cfsetospeed(&newtio,B57600);
		break;
	case 115200:
		cfsetispeed(&newtio,B115200);//波特率
		cfsetospeed(&newtio,B115200);
		break;
	}

	if(nStop == 1){
		newtio.c_cflag &= ~ CSTOPB;//停止位
	}else if(nSpeed == 2){
		newtio.c_cflag |= CSTOPB;//停止位
	}

	newtio.c_cc[VTIME] = 0;
	newtio.c_cc[VMIN] = 0;
	tcflush(UART_FD,TCIFLUSH);
	if((tcsetattr(UART_FD,TCSANOW,&newtio)) != 0){
		return;
	}
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    PWMBUZ_APP_PWM_OUT
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_PWMBUZ_1APP_1PWM_1OUT
  (JNIEnv *env, jobject class, jstring pwm){
	int pwm_fd;
	char *buf=jstringTostring(env,pwm);
	if ((pwm_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device  %s %s",buf,strerror(errno));
		return;
	}
	write(pwm_fd, "1", 1);
	close(pwm_fd);
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    PWMBUZ_SET_FRE
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_PWMBUZ_1SET_1FRE
  (JNIEnv *env, jobject class, jstring pwm, jstring speed){
	int pwm_fd;
	char *buf=jstringTostring(env,pwm);
	if ((pwm_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device  %s %s",buf,strerror(errno));
		return;
	}

	char *spd=jstringTostring(env,speed);
	write(pwm_fd, spd, 3);
	close(pwm_fd);
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    PWMBUZ_SET_DUTY
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_PWMBUZ_1SET_1DUTY
  (JNIEnv *env, jobject class, jstring pwm, jstring duty){
	int pwm_fd;
	char *buf=jstringTostring(env,pwm);
	if ((pwm_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device  %s %s",buf,strerror(errno));
		return;
	}
	char *dy=jstringTostring(env,duty);
	write(pwm_fd, dy, 2);
	close(pwm_fd);
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    PWMBUZ_RUNORCLOSE
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_PWMBUZ_1RUNORCLOSE
  (JNIEnv *env, jobject class, jstring pwm, jboolean flag){
	int pwm_fd;
	char *buf=jstringTostring(env,pwm);
	if ((pwm_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device  %s %s",buf,strerror(errno));
		return;
	}
	if(flag)
	    write(pwm_fd, "1", 1);
	else
		write(pwm_fd, "0", 1);
	close(pwm_fd);
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    TEMP_START
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_whjz_io_JniNative_TEMP_1START
  (JNIEnv *env, jobject class){
	int temp_fd;
	if ((temp_fd = open("/sys/devices/virtual/input/input2/enable", O_RDWR)) < 0) {
		LOGE("fial to open device  /sys/devices/virtual/input/input2/enable %s",strerror(errno));
		return;
	}
	write(temp_fd, "1", 1);
	close(temp_fd);
}
/*
 * Class:     com_whjz_io_JniNative
 * Method:    TEMP_CATTEMP
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_whjz_io_JniNative_TEMP_1CATTEMP
  (JNIEnv *env, jobject class){
	int temp_fd;
	if ((temp_fd = open("/sys/class/i2c-dev/i2c-2/device/2-0048/temp1_input", O_RDWR)) < 0) {
		LOGE("fial to open device  /sys/class/i2c-dev/i2c-2/device/2-0048/temp1_input %s",strerror(errno));
		return;
	}
	char buf[20];
	read(temp_fd,buf,sizeof(buf));
	close(temp_fd);
	return (jint)atoi(buf);;
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    TEMP_CATSPEED
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_whjz_io_JniNative_TEMP_1CATSPEED
  (JNIEnv *env, jobject class){
	int temp_fd;
	if ((temp_fd = open("/sys/devices/virtual/input/input2/poll_delay", O_RDWR)) < 0) {
		LOGE("fial to open device /sys/devices/virtual/input/input2/poll_delay %s",strerror(errno));
		return;
	}
	char buf[20];
	read(temp_fd,buf,sizeof(buf));
	close(temp_fd);
	return (jint)atoi(buf);;
}

/*
 * Class:     com_whjz_io_JniNative
 * Method:    ADREAD
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_whjz_io_JniNative_ADREAD
  (JNIEnv *env, jclass class, jint adport){
	int ad_fd;
	char buf[100];
	sprintf(buf,"/sys/bus/iio/devices/iio\:device0/in_voltage%d_raw",adport);
	if ((ad_fd = open(buf, O_RDWR)) < 0) {
		LOGE("fial to open device /sys/bus/iio/devices/iio\:device0/in_voltage%d_raw %s",adport, strerror(errno));
		return;
	}
	char val[4];
	read(ad_fd,val,sizeof(val));
	close(ad_fd);
	return (jint)atoi(val);

}
