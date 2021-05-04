package com.sandalen.advanceddemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sandalen.advanceddemo.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private List<String> names;
    private Context context;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        this.names = new ArrayList<>();
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setNames(List<String> names) {
        this.names = names;
        System.out.println(names.size() + "===============");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = names.get(position);
        int icon = getIcon(position);
        Log.e(TAG, "onBindViewHolder: " );
        holder.iv.setImageResource(icon);
        holder.tv.setText(name);

        if(recyclerView.getLayoutManager().getClass() == StaggeredGridLayoutManager.class){
            Log.e(TAG, "onBindViewHolder: 瀑布布局" );
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getRandoms());
            holder.tv.setLayoutParams(layoutParams);
        }
        else{
            Log.e(TAG, "onBindViewHolder: 线性布局" );
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.tv.setLayoutParams(layoutParams);
        }

        holder.mitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    public int getRandoms(){
        return (int)(Math.random() * 1000);
    }

    private int getIcon(int position){
        switch (position % 5){
            case 0:
                return R.mipmap.a;
            case 1:
                return R.mipmap.b;
            case 2:
                return R.mipmap.c;
            case 3:
                return R.mipmap.d;
            case 4:
                return R.mipmap.e;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv;
        private View mitemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.recycler_iv);
            tv = itemView.findViewById(R.id.recycler_tv);
            mitemView = itemView;
        }
    }
}
