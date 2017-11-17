package com.eagle.adapter.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.eagle.adapter.BuildConfig;


/**
 * Start
 * <p/>
 * User:Rocky(email:1247106107@qq.com)
 * Created by Rocky on 2017/11/18  14:00
 * PACKAGE_NAME com.eagle.adapter.util
 * PROJECT_NAME SuperAdapter
 * TODO:
 * Description:
 * <p/>
 * Done
 */
public class LogUtils {
    public static final boolean LOG_SWITCH = BuildConfig.DEBUG;

    public static final String LOG_TAG = "LogUtils";

    public static void i(String msg) {
        i(LOG_TAG, msg);
    }

    public static void e(String msg) {
        e(LOG_TAG, msg);
    }

    public static void d(String msg) {
        Log.d(LOG_TAG, msg);
    }

    public static void w(String msg) {
        Log.w(LOG_TAG, msg);
    }

    public static void v(String msg) {
        Log.v(LOG_TAG, msg);
    }


    public static void i(String tag, String msg) {
        if (LOG_SWITCH)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (LOG_SWITCH)
            Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (LOG_SWITCH)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (LOG_SWITCH)
            Log.w(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (LOG_SWITCH)
            Log.v(tag, msg);
    }

    public static void toast(Context context, String msg) {
        if (LOG_SWITCH) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

}
