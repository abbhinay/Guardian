package com.abhinay.guardian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class AppRestrictionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView appIcon;
    TextView appName;
    TextView pkgName;
    Switch appSwitch;
    private Spinner dropdown;

    static String PREFS = "prefs";
    static String PACKAGE_NAMES_KEY = "packages";

    String appNameString;
    String pkgNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_restriction);

        appIcon = (ImageView) findViewById(R.id.app_icon);
        appName = (TextView) findViewById(R.id.app_name);
        pkgName = (TextView) findViewById(R.id.app_pkg);
        appSwitch = (Switch) findViewById(R.id.switch_for_app);

        Intent myIntent = getIntent();
        //String appIconString = myIntent.getStringExtra("appImage");
        appNameString = myIntent.getStringExtra("appName");
        pkgNameString = myIntent.getStringExtra("packName");

        appName.setText(appNameString);
        pkgName.setText(pkgNameString);
        //appIcon.setImageDrawable(appNameString);

        SharedPreferences prefs = getSharedPreferences(AppRestrictionActivity.PREFS, MODE_PRIVATE);
        final String savedPackageNames = prefs.getString(AppRestrictionActivity.PACKAGE_NAMES_KEY, null);

        try{
            if(savedPackageNames.contains(pkgNameString)){
                appSwitch.setChecked(true);
            }else{
                appSwitch.setChecked(false);
            }
        }catch (Exception e){
            Log.d("avadakedavara", e.toString()+ " at line 62 in AppRestrictionActivity.java");
        }


        appSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("avadakedavara", "true is here");
                    SharedPreferences prefs = getSharedPreferences(PREFS, 0);
                    prefs.edit().putString(PACKAGE_NAMES_KEY, savedPackageNames+pkgNameString+" ").apply();
                }else{
                    Log.d("avadakedavara", "false is here");
                    SharedPreferences prefs = getSharedPreferences(PREFS, 0);
                    savedPackageNames.replace(pkgNameString, "");
                    prefs.edit().putString(PACKAGE_NAMES_KEY, savedPackageNames).apply();
                }
            }
        });

        //implementing dropdown
        dropdown = (Spinner) findViewById(R.id.branch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.count, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setNumberOfOpen no = new setNumberOfOpen(position+1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class setNumberOfOpen{

            public setNumberOfOpen(int i) {
//                SharedPreferences prefs = getSharedPreferences(PREFS, 0);
//                prefs.edit().putString(pkgNameString, Integer.toString(i)).apply();
            }

    }

}
