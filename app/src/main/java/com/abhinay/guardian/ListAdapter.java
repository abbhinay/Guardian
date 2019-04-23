package com.abhinay.guardian;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            TextView appName = (TextView) convertView.findViewById(R.id.app_name);
            TextView packageName = (TextView) convertView.findViewById(R.id.app_pkg);
            ImageView iconView = (ImageView) convertView.findViewById(R.id.app_icon);

            appName.setText(data.loadLabel(packageManager));
            packageName.setText(data.packageName);
            iconView.setImageDrawable(data.loadIcon(packageManager));
        }

        return convertView;
    }
}
