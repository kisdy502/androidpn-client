package cn.sdt.pushclient.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SDT13411 on 2017/12/18.
 */

public class SharedPrefUtil {

    private final static String FILE_NAME = "setting";

    private static void setData(Context context, String key, String value) {
        //1、打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
        //2、让setting处于编辑状态
        SharedPreferences.Editor editor = settings.edit();
        //3、存放数据
        editor.putString(key, value);
        //4、完成提交
        editor.commit();
    }

    public static String getData(Context context, String key) {
        //1、获取Preferences
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
        //2、取出数据
        String name = settings.getString(key, "");
        return name;
    }

    private static void setIntData(Context context, String key, int value) {
        //1、打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
        //2、让setting处于编辑状态
        SharedPreferences.Editor editor = settings.edit();
        //3、存放数据
        editor.putInt(key, value);
        //4、完成提交
        editor.commit();
    }

    public static int getIntData(Context context, String key) {
        //1、获取Preferences
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
        //2、取出数据
        int value = settings.getInt(key, -1);
        return value;
    }

    private static void setLongData(Context context, String key, long value) {
        //1、打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
        //2、让setting处于编辑状态
        SharedPreferences.Editor editor = settings.edit();
        //3、存放数据
        editor.putLong(key, value);
        //4、完成提交
        editor.commit();
    }

    public static long getLongData(Context context, String key) {
        //1、获取Preferences
        SharedPreferences settings = context.getSharedPreferences(FILE_NAME, 0);
        //2、取出数据
        long value = settings.getLong(key, -1L);
        return value;
    }

    public static void setUserName(Context context, String username) {
        setData(context, "userName", username);
    }

    public static String getUserName(Context context) {
        return getData(context, "userName");
    }

    public static void setPassword(Context context, String password) {
        setData(context, "password", password);
    }

    public static String getPassword(Context context) {
        return getData(context, "password");
    }



}
