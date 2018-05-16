package cn.sdt.pushclient.connect;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;

import cn.sdt.pushclient.FLog;
import cn.sdt.pushclient.NotificationIQProvider;

/**
 * Created by Administrator on 2018/5/8.
 */

public class ConnectTask implements Runnable {

    final XMPPConnection connection;

    public ConnectTask(XMPPConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            connection.connect();
            FLog.d("connect successed");
            ProviderManager.getInstance().addIQProvider("notification",
                    "androidpn:iq:notification",new NotificationIQProvider());
            ConnectionManager.getInstance().register();
        } catch (XMPPException e) {
            e.printStackTrace();
            FLog.e("connect failed");
        }

    }
}
