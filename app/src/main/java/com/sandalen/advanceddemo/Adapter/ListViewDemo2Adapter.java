package com.sandalen.advanceddemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandalen.advanceddemo.R;
import com.sandalen.advanceddemo.model.Lesson;

import java.util.List;

public class ListViewDemo2Adapter extends BaseAdapter {

    private List<Lesson> datas;
    private Context context;

    public ListViewDemo2Adapter(Context context,List<Lesson> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == datas ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item,null);

            viewHolder.imageView = convertView.findViewById(R.id.list_view_icon);
            viewHolder.textView = convertView.findViewById(R.id.list_view_txt);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(datas.get(position).getName());
        viewHolder.imageView.setVisibility(View.GONE);
        return convertView;
    }

    public class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
