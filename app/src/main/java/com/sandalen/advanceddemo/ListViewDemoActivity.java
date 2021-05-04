package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);

        ListView listView = findViewById(R.id.list_view_demo);

        List<ResolveInfo> appInfos = getAppInfos();
        listView.setAdapter(new ListViewDemoAdapter(appInfos));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packageName = appInfos.get(position).activityInfo.packageName;
                String className = appInfos.get(position).activityInfo.name;

                ComponentName componentName = new ComponentName(packageName,className);
                Intent intent = new Intent();
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_header,null);
        listView.addHeaderView(view);
    }

    private List<ResolveInfo> getAppInfos(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        return getPackageManager().queryIntentActivities(mainIntent,0);
    }

    public class ListViewDemoAdapter extends BaseAdapter {
        private List<ResolveInfo> appInfos;

        public ListViewDemoAdapter(List<ResolveInfo> appNames) {
            this.appInfos = appNames;
        }

        @Override
        public int getCount() {
            return null == appInfos ? 0 : appInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return appInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //通过viewHolder缓存convertView
            ViewHolder viewHolder = new ViewHolder();

            if(convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_view_item,null);

                viewHolder.imageView = convertView.findViewById(R.id.list_view_icon);
                viewHolder.textView = convertView.findViewById(R.id.list_view_txt);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(appInfos.get(position).activityInfo.loadLabel(getPackageManager()));
            viewHolder.imageView.setImageDrawable(appInfos.get(position).activityInfo.loadIcon(getPackageManager()));

            return convertView;
        }

        public class ViewHolder{
            public ImageView imageView;
            public TextView textView;
        }
    }
}