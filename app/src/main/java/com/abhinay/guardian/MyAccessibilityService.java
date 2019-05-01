package com.abhinay.guardian;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {

    //when service starts
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("avadakedavara", "service started");
    }

    //when service is running
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        SharedPreferences prefs = getSharedPreferences(AppRestrictionActivity.PREFS, MODE_PRIVATE);
        String savedPackageNames = prefs.getString(AppRestrictionActivity.PACKAGE_NAMES_KEY, null);
        String currentOpenedPackage = (String) event.getPackageName();
        try {
            if(savedPackageNames.contains(currentOpenedPackage)){
                Intent intent = new Intent(this, LockActivity.class);
                intent.putExtra("abhinay_pkg_name", currentOpenedPackage);
                startActivity(intent);
                Log.d("avadakedavara", currentOpenedPackage+" started");
            }
        }catch (Exception e){
            Log.d("avadakedavara", e.toString());
        }



    }

    @Override
    public void onInterrupt() {

    }

    //when serice is shut down
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    //////////////////////////////////////////////////////////////////////
    static final int NOTIFICATION_ID = 543;

    public static boolean isServiceRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceWithNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null ) {
            startServiceWithNotification();
        }
        else stopMyService();
        return START_STICKY;
    }

    // In case the service is deleted or crashes some how
    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        // Used only in case of bound services.
//        return null;
//    }


    void startServiceWithNotification() {
        if (isServiceRunning) return;
        isServiceRunning = true;

        Intent notificationIntent = new Intent(getApplicationContext(), MyAccessibilityService.class);
        //notificationIntent.setAction(C.ACTION_MAIN);  // A string containing the action name
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
//                .setDeleteIntent(contentPendingIntent)  // if needed
                .build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
        startForeground(NOTIFICATION_ID, notification);
    }

    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }
}
