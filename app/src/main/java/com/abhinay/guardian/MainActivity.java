package com.abhinay.guardian;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;
import static android.provider.CalendarContract.Instances.BEGIN;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mListView;
    private ListAdapter mAdapter;
    private List<ApplicationInfo> packages;

    PackageManager pm;
    UsageStatsManager usageStatsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ///////////////////////////////////////////////////////////////////////////////////////
//        Intent intent = new Intent(this, MyIntentService.class);
//        startService(intent);

        Intent intent = new Intent(this, MyAccessibilityService.class);
        startService(intent);

        //removes the app from the recent app (drawer)
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        if(am != null) {
            List<ActivityManager.AppTask> tasks = am.getAppTasks();
            if (tasks != null && tasks.size() > 0) {
                tasks.get(0).setExcludeFromRecents(true);
            }
        }

//        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//        List l = am.getRecentTasks(1, ActivityManager.RECENT_WITH_EXCLUDED);
//        Iterator i = l.iterator();
//        PackageManager pm = this.getPackageManager();
//        while (i.hasNext()) {
//            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
//            try {
//                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
//                Log.w("LABEL", c.toString());
//            } catch (Exception e) {
//                // Name Not FOund Exception
//            }
//        }

        //startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        //usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        //getStats(this);
 ///////////////////////////////////////////////////////////////////////////////////////////////////

        mListView = (ListView) findViewById(R.id.list);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        pm = getPackageManager();

        packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

//        for (ApplicationInfo packageInfo : packages) {
//            Log.d("guardian", "Installed package :" + packageInfo.packageName);
//            Log.d("guardian", "Source dir : " + packageInfo.sourceDir);
//            Log.d("guardian", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
//        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

//    public static void getStats(Context context){
//        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
//        int interval = UsageStatsManager.INTERVAL_YEARLY;
//        Calendar calendar = Calendar.getInstance();
//        long endTime = calendar.getTimeInMillis();
//        calendar.add(Calendar.YEAR, -1);
//        long startTime = calendar.getTimeInMillis();
//
////        Log.d("abhinay", "Range start:" + dateFormat.format(startTime) );
////        Log.d("abhinay", "Range end:" + dateFormat.format(endTime));
//
//        UsageEvents uEvents = usm.queryEvents(startTime,endTime);
//        while (uEvents.hasNextEvent()){
//            UsageEvents.Event e = new UsageEvents.Event();
//            uEvents.getNextEvent(e);
//
//            if (e != null){
//                Log.d("abhinay", "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
//            }
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent intent = new Intent(this, MyIntentService.class);
//        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new ListAdapter(this,this, packages);
        mListView.setAdapter(mAdapter);

//        Intent intent = new Intent(this, MyIntentService.class);
//        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Intent intent = new Intent(this, MyIntentService.class);
//        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
