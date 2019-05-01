package com.abhinay.guardian;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;


public class ListAdapter extends BaseAdapter {

    private List<ApplicationInfo> packages;
    private Activity mActivity;
    private PackageManager packageManager;

    public ListAdapter(Context context, Activity activity, List<ApplicationInfo> packages){
        this.packages = packages;
        mActivity = activity;
        packageManager = context.getPackageManager();
    }

    static class ViewHolder{
        ImageView appIcon;
        TextView appName;
        TextView pkgName;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return packages.size();
    }

    @Override
    public Object getItem(int position) {
        return packages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_list, parent, false);
//            final ViewHolder holder = new ViewHolder();
//            holder.appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
//            holder.appName = (TextView) convertView.findViewById(R.id.app_name);
//            holder.pkgName = (TextView) convertView.findViewById(R.id.app_pkg);
//            holder.params = (LinearLayout.LayoutParams) holder.appIcon.getLayoutParams();
//            convertView.setTag(holder);
        }

        ApplicationInfo data = packages.get(position);

        if(null!=data){
            final TextView appName = (TextView) convertView.findViewById(R.id.app_name);
            final TextView packageName = (TextView) convertView.findViewById(R.id.app_pkg);
            ImageView iconView = (ImageView) convertView.findViewById(R.id.app_icon);
            final Switch appSwitch = (Switch) convertView.findViewById(R.id.switch_for_app);

            appSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        appSwitch.setChecked(true);
//                        pkg_names += packageName.toString() + ", ";
                    }
                }
            });

            appName.setText(data.loadLabel(packageManager));
            packageName.setText(data.packageName);
            iconView.setImageDrawable(data.loadIcon(packageManager));

            appName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ApplicationInfo clr = packages.get(position);
                    Log.d("avadakedavara", clr.packageName+position);
//                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                    alert.setTitle("Do you want to restrict this app");
//                    alert.setCancelable(true);
//                    alert.setMessage(clr.loadLabel(packageManager));
//                    alert.setPositiveButton("Restrict this app", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialog, int which){
//
//                        }
//                    });
//                    alert.show();

                    Intent myIntent = new Intent(v.getContext(), AppRestrictionActivity.class);
                    myIntent.putExtra("packName", clr.packageName);
                    myIntent.putExtra("appName", clr.loadLabel(packageManager));
                    //myIntent.putExtra("appImage", clr.loadIcon(packageManager));
                    v.getContext().startActivity(myIntent);
                }
            });
        }

        return convertView;
    }

}
