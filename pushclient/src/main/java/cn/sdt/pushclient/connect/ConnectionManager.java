package cn.sdt.pushclient.connect;

import android.content.Context;
import android.text.TextUtils;

import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.PasswordCallback;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;

import cn.sdt.pushclient.FLog;
import cn.sdt.pushclient.util.SharedPrefUtil;

/**
 * Created by Administrator on 2018/5/8.
 */

public class ConnectionManager {

    private XMPPConnection xmppConnection;

    private String xmppHost;
    private int xmppPort;
    private Context mContext;

    public static ConnectionManager getInstance() {
        return ConnectionHolder.instance;
    }

    public void init(String xmppHost, int xmppPort) {
        this.xmppHost = xmppHost;
        this.xmppPort = xmppPort;
    }

    public void initContext(Context context) {
        mContext = context;
    }

    public ConnectionManager() {
        FLog.d("ConnectionManager");
    }

    public void connect() {
        if (xmppConnection != null) {
            if (xmppConnection.isConnected()) {
                if (xmppConnection.isAuthenticated()) {
                    FLog.d("already connected");
                } else {
                    register();
                }
            } else {
                realConnect();
            }
        } else {
            realConnect();
        }
    }

    /**
     * 客户端和服务器双向安全SSL验证
     */
    private void realConnect() {
        ConnectionConfiguration connConfig = new ConnectionConfiguration(xmppHost, xmppPort);
        connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.required);
        connConfig.setSASLAuthenticationEnabled(true);
        connConfig.setCompressionEnabled(false);
        connConfig.setTruststoreType("PKCS12");
        connConfig.setTruststorePassword("wubuandroid123");
        connConfig.setTruststorePath(new File(mContext.getFilesDir(), "client.p12").getAbsolutePath());

        connConfig.setKeystoreType("BKS");
        connConfig.setKeystorePath(new File(mContext.getFilesDir(), "client.truststore").getAbsolutePath());
        XMPPConnection connection = new XMPPConnection(connConfig, null);

        xmppConnection = connection;
        TaskExecutor.executeTask(new ConnectTask(xmppConnection));
    }

    public void register() {
        String userName = SharedPrefUtil.getUserName(mContext);
        String passwrod = SharedPrefUtil.getPassword(mContext);
        FLog.d("userName:" + userName);
        FLog.d("password:" + passwrod);
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passwrod)) {
            if (xmppConnection != null && xmppConnection.isConnected()) {
                TaskExecutor.executeTask(new RegisterTask(xmppConnection, mContext));
            } else {
                FLog.d("please connect first");
            }
        } else {
            FLog.d("already registered,please login");
            TaskExecutor.executeTask(new LoginTask(xmppConnection, userName, passwrod, mContext));
        }
    }

    public void login() {
        String userName = SharedPrefUtil.getUserName(mContext);
        String passwrod = SharedPrefUtil.getPassword(mContext);
        FLog.d("userName:" + userName);
        FLog.d("password:" + passwrod);
        if (xmppConnection != null && xmppConnection.isConnected()) {
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passwrod)) {
                FLog.d("please register first");
            } else {
                TaskExecutor.executeTask(new LoginTask(xmppConnection, userName, passwrod, mContext));
            }
        } else {
            FLog.d("please connect first");
        }
    }

    public void disconnect() {
        if (xmppConnection != null && xmppConnection.isConnected()) {
            xmppConnection.disconnect();
        }
        xmppConnection = null;
    }


    private static final class ConnectionHolder {
        private static final ConnectionManager instance = new ConnectionManager();
    }

}
