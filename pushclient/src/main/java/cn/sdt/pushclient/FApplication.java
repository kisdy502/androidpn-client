package cn.sdt.pushclient;

import android.app.Application;

import cn.sdt.pushclient.connect.ConnectionManager;
import cn.sdt.pushclient.util.FileUtil;

/**
 * Created by Administrator on 2018/5/8.
 */

public class FApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FLog.enableLog2Console(true);
        ConnectionManager.getInstance().initContext(this);
        ConnectionManager.getInstance().init("192.168.66.77", 5222);

        long start = System.currentTimeMillis();
        FileUtil.copyFile("client.p12", this);
        FileUtil.copyFile("client.truststore", this);
        long offset = System.currentTimeMillis() - start;
        FLog.d("coust:" + offset);
    }
}
