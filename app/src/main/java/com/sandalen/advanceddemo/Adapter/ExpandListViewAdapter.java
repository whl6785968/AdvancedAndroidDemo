package com.sandalen.advanceddemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandalen.advanceddemo.R;
import com.sandalen.advanceddemo.model.Chapter;

import java.util.List;

public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Chapter> datas;
    private LayoutInflater layoutInflater;

    public ExpandListViewAdapter(Context context, List<Chapter> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return datas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datas.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return datas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datas.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_parent_chapter,parent,false);
            parentViewHolder = new ParentViewHolder();
            parentViewHolder.textView = convertView.findViewById(R.id.id_tv_name);
            parentViewHolder.imageView = convertView.findViewById(R.id.id_indicator_group);
            convertView.setTag(parentViewHolder);
        }
        else{
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }

        parentViewHolder.textView.setText(datas.get(groupPosition).getName());
        parentViewHolder.imageView.setSelected(isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_child_chapter,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.textView = convertView.findViewById(R.id.id_tv_name);
            convertView.setTag(childViewHolder);
        }
        else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.textView.setText(datas.get(groupPosition).getChildren().get(childPosition).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ParentViewHolder{
        TextView textView;
        ImageView imageView;
    }

    public class ChildViewHolder{
        TextView textView;
    }
}
