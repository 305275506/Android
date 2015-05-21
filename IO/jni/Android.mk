LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
TARGET_PLATFORM := android-14
LOCAL_MODULE    := WHJZIO
LOCAL_SRC_FILES := com_whjz_io_JniNative.c
LOCAL_LDLIBS    := -llog
include $(BUILD_SHARED_LIBRARY) 