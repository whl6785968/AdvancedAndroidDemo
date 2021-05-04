package com.sandalen.advanceddemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sandalen.advanceddemo.R;
import com.sandalen.advanceddemo.model.Msg;

import java.util.List;

public class ListCardAdapter extends BaseAdapter {
    private Context context;
    private List<Msg> datas;
    private LayoutInflater inflater;

    public ListCardAdapter(Context context, List<Msg> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_card,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.card_icon);
            viewHolder.titleView = convertView.findViewById(R.id.card_title);
            viewHolder.contentView = convertView.findViewById(R.id.card_content);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Msg msg = datas.get(position);
        viewHolder.imageView.setImageResource(msg.getImgResId());
        viewHolder.titleView.setText(msg.getTitle());
        viewHolder.contentView.setText(msg.getContent());

        return convertView;
    }

    public class ViewHolder{
        ImageView imageView;
        TextView titleView;
        TextView contentView;
    }
}
