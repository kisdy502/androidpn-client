package cn.sdt.pushclient.connect;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import cn.sdt.pushclient.Constants;
import cn.sdt.pushclient.FLog;
import cn.sdt.pushclient.NotificationIQ;

/**
 * Created by Administrator on 2018/5/14.
 */

public class LoginTask implements Runnable {

    final XMPPConnection connection;
    private String userName;
    private String password;
    private Context mContext;

    public LoginTask(XMPPConnection connection, String userName, String password,Context context) {
        this.connection = connection;
        this.userName = userName;
        this.password = password;
        this.mContext=context;
    }

    @Override
    public void run() {
        try {
            connection.login(userName, password, "AndroidpnClient");
            FLog.d("login successed::" + connection.isAuthenticated());
            PacketFilter packetFilter = new PacketTypeFilter(NotificationIQ.class);
            connection.addPacketListener(new PacketListener() {
                @Override
                public void processPacket(Packet packet) {
                    FLog.w("packet::" + packet.toXML());
                    if (packet instanceof NotificationIQ) {
                        NotificationIQ notification = (NotificationIQ) packet;
                        if (notification.getChildElementXML().contains("androidpn:iq:notification")) {

                            String notificationId = notification.getId();
                            String notificationApiKey = notification.getApiKey();
                            String notificationTitle = notification.getTitle();
                            String notificationMessage = notification.getMessage();
                            String notificationUri = notification.getUri();
                            String notificationFrom = notification.getFrom();
                            String packetId = notification.getPacketID();

                            Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
                            intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
                            intent.putExtra(Constants.NOTIFICATION_API_KEY, notificationApiKey);
                            intent.putExtra(Constants.NOTIFICATION_TITLE, notificationTitle);
                            intent.putExtra(Constants.NOTIFICATION_MESSAGE, notificationMessage);
                            intent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
                            intent.putExtra(Constants.NOTIFICATION_FROM, notificationFrom);
                            intent.putExtra(Constants.PACKET_ID, packetId);
                            //TODO FIXME 发送收到通知回执
                            IQ result = NotificationIQ.createResultIQ(notification);
                            connection.sendPacket(result);

                            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                        }
                    }
                }
            }, packetFilter);
            connection.addConnectionListener(new ConnectionListener() {
                @Override
                public void connectionClosed() {
                    FLog.d("connectionClosed");
                }

                @Override
                public void connectionClosedOnError(Exception e) {
                    FLog.d("connectionClosedOnError:" + e.getMessage());
                }

                @Override
                public void reconnectingIn(int seconds) {
                    FLog.d("reconnectingIn:" + seconds);
                }

                @Override
                public void reconnectionSuccessful() {
                    FLog.d("reconnectionSuccessful:");
                }

                @Override
                public void reconnectionFailed(Exception e) {
                    FLog.d("reconnectionFailed:" + e.getMessage());
                }
            });
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }


}
