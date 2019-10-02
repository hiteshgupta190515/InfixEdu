package com.infix.edu.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infix.edu.R;
import com.infix.edu.activity.HomeActivity;
import com.infix.edu.receiver.SensorRestarterBroadcastReceiver;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class FcmMessagingService extends FirebaseMessagingService {


    String type = "";
    public int counter=0;

    public FcmMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

//        if(remoteMessage.getData().size() > 0){
//
//            type = "json";
//            sendNotification(remoteMessage.getData().toString(),null);
//
//        }else if(remoteMessage.getNotification() != null){
//
//            type = "message";
//            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
//
//        }

        startTimer();


        Intent intent = new Intent(this, HomeActivity.class);


        showNotificationMessage(this,remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),null,intent);



    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);

        sendBroadcast(broadcastIntent);
        stoptimertask();

    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //

    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent(getApplicationContext(), SensorRestarterBroadcastReceiver.class);

        stopService(broadcastIntent);
        sendBroadcast(broadcastIntent);
        stoptimertask();

//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable(){
//            @Override
//            public void run(){
//                Toast.makeText(FcmMessagingService.this,counter+"call",Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.infix_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

    }


//    private void sendNotification(final String message_body,String message_title){
//
//        String id = "",message="",title="";
//
//        if(type.equals("json")){
//
//            try{
//
//                JSONObject jsonObject = new JSONObject(message_body);
//                id = jsonObject.getString("id");
//                message = jsonObject.getString("body");
//                title = jsonObject.getString("title");
//
//
//            }catch (Exception e){
//
//            }
//
//        }else if(type.equals("message")){
//
//            message = message_body;
//            title = message_title;
//
//        }
//
//
//        // The id of the channel.
//        final String CHANNEL_ID = "default";
//        // The user-visible name of the channel.
//        final String CHANNEL_NAME = "Default";
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//
//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//        Notification.Builder notiBuilder = new Notification.Builder(this);
//        notiBuilder.setContentTitle(getString(R.string.app_name));
//        notiBuilder.setContentText(message);
//        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notiBuilder.setSound(soundUri);
//        notiBuilder.setSmallIcon(R.mipmap.infix_launcher);
//        notiBuilder.setAutoCancel(true);
//        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
//        v.vibrate(1000);
//        notiBuilder.setContentIntent(pendingIntent);
//
//        notificationManager.notify(0,notiBuilder.build());
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel defaultChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//            notiBuilder.setChannelId(CHANNEL_ID);
//            notificationManager.createNotificationChannel(defaultChannel);
//        }
//
//    }

}
