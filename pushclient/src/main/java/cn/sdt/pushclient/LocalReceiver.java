package cn.sdt.pushclient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2018/5/15.
 */

public class LocalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.ACTION_SHOW_NOTIFICATION)) {
            String notificationId = intent
                    .getStringExtra(Constants.NOTIFICATION_ID);
            String notificationApiKey = intent
                    .getStringExtra(Constants.NOTIFICATION_API_KEY);
            String notificationTitle = intent
                    .getStringExtra(Constants.NOTIFICATION_TITLE);
            String notificationMessage = intent
                    .getStringExtra(Constants.NOTIFICATION_MESSAGE);
            String notificationUri = intent
                    .getStringExtra(Constants.NOTIFICATION_URI);
            String notificationFrom = intent
                    .getStringExtra(Constants.NOTIFICATION_FROM);
            String packetId = intent
                    .getStringExtra(Constants.PACKET_ID);

            FLog.d("notificationId=" + notificationId);
            FLog.d("notificationApiKey=" + notificationApiKey);
            FLog.d("notificationTitle=" + notificationTitle);
            FLog.d("notificationMessage=" + notificationMessage);
            FLog.d("notificationUri=" + notificationUri);

            Intent startIntent = new Intent(context, MainActivity.class);
            startIntent.putExtra(Constants.NOTIFICATION_ID, notificationId);
            startIntent.putExtra(Constants.NOTIFICATION_API_KEY, notificationApiKey);
            startIntent.putExtra(Constants.NOTIFICATION_TITLE, notificationTitle);
            startIntent.putExtra(Constants.NOTIFICATION_MESSAGE, notificationMessage);
            startIntent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
            startIntent.putExtra(Constants.NOTIFICATION_FROM, notificationFrom);
            startIntent.putExtra(Constants.PACKET_ID, packetId);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Random random=new Random();
            PendingIntent contentIntent = PendingIntent.getActivity(context,random.nextInt(),
                    startIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"AndroidPnClient");
            mBuilder.setContentTitle(notificationTitle)//设置通知栏标题
                    .setContentText(notificationMessage)
                    .setContentIntent(contentIntent) //设置通知栏点击意图
                    .setTicker(notificationTitle)                            //通知首次出现在通知栏，带上升动画效果的
                    .setWhen(System.currentTimeMillis())                //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setPriority(Notification.PRIORITY_DEFAULT)         //设置该通知优先级
                    .setAutoCancel(true)                                //设置这个标志当用户单击面板就可以让通知将自动取消
                    .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                    .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                    .setSmallIcon(R.mipmap.ic_launcher_round);                      //设置通知小ICON

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mNotificationManager.notify(random.nextInt(), mBuilder.build());


        }
    }


}
