package cn.sdt.pushclient.connect;

import android.content.Context;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Registration;

import java.util.UUID;

import cn.sdt.pushclient.FLog;
import cn.sdt.pushclient.util.SharedPrefUtil;

/**
 * Created by Administrator on 2018/5/14.
 */

public class RegisterTask implements Runnable {
    private Context mContext;

    final XMPPConnection connection;

    public RegisterTask(XMPPConnection connection, Context context) {
        this.connection = connection;
        this.mContext = context;
    }

    @Override
    public void run() {
        final String newUsername = newRandomUUID();
        final String newPassword = newRandomUUID();

        Registration registration = new Registration();

        PacketFilter packetFilter = new AndFilter(new PacketIDFilter(
                registration.getPacketID()), new PacketTypeFilter(IQ.class));
        registration.setType(IQ.Type.SET);
        PacketListener packetListener = new PacketListener() {
            @Override
            public void processPacket(Packet packet) {
                FLog.d(packet.toXML());
                if (packet instanceof IQ) {
                    IQ response = (IQ) packet;
                    if (response.getType() == IQ.Type.ERROR) {
                        FLog.e("Unknown error while registering XMPP account! "
                                + response.getError().getCondition());
                    } else if (response.getType() == IQ.Type.RESULT) {
                        SharedPrefUtil.setUserName(mContext, newUsername);
                        SharedPrefUtil.setPassword(mContext, newPassword);

                        TaskExecutor.executeTask(new LoginTask(connection, newUsername, newPassword,mContext));
                    }
                }
            }
        };
        connection.addPacketListener(packetListener, packetFilter);
        registration.addAttribute("username", newUsername);
        registration.addAttribute("password", newPassword);
        connection.sendPacket(registration);
    }


    private String newRandomUUID() {
        String uuidRaw = UUID.randomUUID().toString();
        return uuidRaw.replaceAll("-", "");
    }
}
