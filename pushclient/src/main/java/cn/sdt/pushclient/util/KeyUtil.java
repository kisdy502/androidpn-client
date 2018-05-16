package cn.sdt.pushclient.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Administrator on 2018/5/16.
 */

public class KeyUtil {
    private static final String KEY_STORE_TYPE_BKS = "BKS";//证书类型 固定值
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型 固定值

    private static final String KEY_STORE_CLIENT_PATH = "client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_TRUST_PATH = "client.truststore";//客户端验证服务器端的证书库
    private static final String KEY_STORE_PASSWORD = "wubuandroid123";// 客户端证书密码
    private static final String KEY_STORE_TRUST_PASSWORD = "wubuandroid123";//客户端证书库密码


    public static SSLSocketFactory getSslSocketFactory(Context context) {
        try {
            //服务器端需要验证的客户端证书
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
            //客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);

            InputStream ksIn = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
            InputStream tsIn = context.getResources().getAssets().open(KEY_STORE_TRUST_PATH);
            try {
                keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
                trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());

                //初始化SSLContext
                SSLContext sslContext = SSLContext.getInstance("TLS");
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
                trustManagerFactory.init(trustStore);
                keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());

                sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

                SSLSocketFactory socketFactory = sslContext.getSocketFactory();
                return socketFactory;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    ksIn.close();
                } catch (Exception ignore) {
                }
                try {
                    tsIn.close();
                } catch (Exception ignore) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
