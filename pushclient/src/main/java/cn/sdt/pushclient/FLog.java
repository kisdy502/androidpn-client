package cn.sdt.pushclient;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/2.
 */

public class FLog {
    public static String customTagPrefix = "FLog";

    private static boolean DEBUG = false;

    public static void enableLog2Console(boolean debug) {
        DEBUG = debug;
    }

    public static void e(String tag, String desc) {
        if (DEBUG) {
            Log.e(tag, desc);
        }
    }

    public static void e(String desc) {
        if (DEBUG) {
            String tag = generateTag();
            Log.e(tag, desc);
        }
    }

    public static void w(String tag, String desc) {
        if (DEBUG) {
            Log.w(tag, desc);
        }
    }

    public static void w(String desc) {
        if (DEBUG) {
            String tag = generateTag();
            Log.w(tag, desc);
        }
    }

    public static void i(String tag, String desc) {
        if (DEBUG) {
            Log.i(tag, desc);
        }
    }

    public static void i(String desc) {
        if (DEBUG) {
            String tag = generateTag();
            Log.i(tag, desc);
        }
    }

    public static void d(String tag, String desc) {
        if (DEBUG) {
            Log.d(tag, desc);
        }
    }

    public static void d(String desc) {
        if (DEBUG) {
            String tag = generateTag();
            Log.d(tag, desc);
        }
    }

    public static void v(String tag, String desc) {
        if (DEBUG) {
            Log.v(tag, desc);
        }
    }

    public static void v(String desc) {
        if (DEBUG) {
            String tag = generateTag();
            Log.v(tag, desc);
        }
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }
}
