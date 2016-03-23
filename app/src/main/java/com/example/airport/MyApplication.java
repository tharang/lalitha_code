package com.example.airport;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

import java.util.Calendar;

import android.util.Log;



public class MyApplication extends Application {

    public int counter_Beacon1_entering=0;
    public int counter_Beacon2_entering=0;
    public int counter_Beacon1_exiting=0;
    public int counter_Beacon2_exiting=0;

    TextView beaconName;
    private BeaconManager beaconManager;
    Context cont;

    Calendar calendar = Calendar.getInstance();
    java.util.Date now;
    java.sql.Timestamp currentTimestamp;


//    public MyApplication( ){
//
//    }
//
//    public MyApplication(Context a){
//        this.cont = a;
//    }


    @Override
    public void onCreate() {

        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
            //               beaconName = (TextView)context.findViewById(R.id.beacon_name);
//               beaconName = (TextView) ((Activity)cont).findViewById(R.id.beacon_name);;
//                beaconName.setText(region.getIdentifier());
//                cont.displayText(
//                        region.getIdentifier(),
//                        "Entering");
                showNotification(
                        region.getIdentifier(),
                        "Entering");

                if(region.getIdentifier() == "vishy_mint")
                {
                    counter_Beacon1_entering++;
                    now = calendar.getTime();
                    currentTimestamp = new java.sql.Timestamp(now.getTime());
                    Log.e("*******************", "*******************" );
                    Log.e("*******************", "counter_Beacon1: " + counter_Beacon1_entering );
                    Log.e("Beacon1", "Connected at " + currentTimestamp);
                    Log.e("*******************", "*******************" );

                }
                else if(region.getIdentifier() == "bed")
                {
                    counter_Beacon2_entering++;
                    now = calendar.getTime();
                    currentTimestamp = new java.sql.Timestamp(now.getTime());
                    Log.e("*******************", "*******************" );
                    Log.e("*******************", "counter_Beacon2: " + counter_Beacon2_entering );
                    Log.e("Beacon2", "Connected at " + currentTimestamp);
                    Log.e("*******************", "*******************" );
                }
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
                showNotification(
                        region.getIdentifier(),  "Exiting" );
                if(region.getIdentifier() == "vishy_mint")
                {
                    counter_Beacon1_exiting++;
                    now = calendar.getTime();
                    currentTimestamp = new java.sql.Timestamp(now.getTime());
                    Log.e("*******************", "*******************" );
                    Log.e("*******************", "counter_Beacon1: " + counter_Beacon1_exiting );
                    Log.e("Beacon1", "Disonnected at " + currentTimestamp);
                    Log.e("*******************", "*******************" );

                }
                else if(region.getIdentifier() == "bed")
                {
                    counter_Beacon2_exiting++;
                    now = calendar.getTime();
                    currentTimestamp = new java.sql.Timestamp(now.getTime());
                    Log.e("*******************", "*******************" );
                    Log.e("*******************", "counter_Beacon2: " + counter_Beacon2_exiting );
                    Log.e("Beacon2", "Disconnected at " + currentTimestamp);
                    Log.e("*******************", "*******************" );
                }
//                displayText(
//                        region.getIdentifier(),
//                        "Exiting");
            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("vishy_mint",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 9666, 7268));
                beaconManager.startMonitoring(new Region("bed",
                        UUID.fromString("D0D3FA86-CA76-45EC-9BD9-6AF4A6A94337"), 20605, 63895));
            }
        });
    }


    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }



}
