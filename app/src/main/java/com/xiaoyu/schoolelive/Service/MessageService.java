package com.xiaoyu.schoolelive.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.xiaoyu.schoolelive.R;
import com.xiaoyu.schoolelive.activities.MainActivity;

/**
 * Created by Administrator on 2017/8/4.
 */

public class MessageService extends Service{
    //获取消息线程
    private messagethread messagethread = null;

    //点击查看
    private Intent messageintent = null;
    private PendingIntent messagependingintent = null;
    //通知栏消息
    private int messagenotificationid = 1000;
    private Notification messagenotification = null;
    private NotificationManager messagenotificatiomanager = null;
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startid) {
        //初始化
        messagenotification.defaults = Notification.DEFAULT_SOUND;
        messagenotificatiomanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        messageintent = new Intent(this, MainActivity.class);
        messagependingintent = messagependingintent.getActivity(this,0,messageintent,0);

        messagenotification = new Notification.Builder(getApplicationContext())
                .setContentTitle("New mail from ???")
                .setContentIntent(messagependingintent)
                .setContentText("新消息")
                .setSmallIcon(R.drawable.like_icon)
                .setWhen(System.currentTimeMillis())
                .build();
        //开启线程
        messagethread = new messagethread();
        messagethread.isrunning = true;
        messagethread.start();

        return super.onStartCommand(intent, flags, startid);
    }

    /**
     * 从服务器端获取消息
     *
     */
    class messagethread extends Thread{
        //运行状态，www.3ppt.com
        //下一步骤有大用
        public boolean isrunning = true;
        public void run() {
            while(isrunning){
                try {
                    //休息10分钟
                    Thread.sleep(600000);
                    //获取服务器消息
                    String servermessage = getservermessage();
                    if(servermessage!=null&&!"".equals(servermessage)){
                        //更新通知栏
                        messagenotification = new Notification.Builder(getApplicationContext())
                                .setContentTitle("New mail from ???")
                                .setContentIntent(messagependingintent)
                                .setContentText("新消息")
                                .setSmallIcon(R.drawable.like_icon)
                                .setWhen(System.currentTimeMillis())
                                .build();
                        messagenotificatiomanager.notify(messagenotificationid, messagenotification);
                        //每次通知完，通知id递增一下，避免消息覆盖掉
                        messagenotificationid++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 这里以此方法为服务器demo，仅作示例
     * @return 返回服务器要推送的消息，否则如果为空的话，不推送
     */
    public String getservermessage(){
        return "yes!";
    }
    @Override
    public void onDestroy() {
        System.exit(0);
        super.onDestroy();
    }
}
