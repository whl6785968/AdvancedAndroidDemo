package com.sandalen.advanceddemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sandalen.advanceddemo.GoodsDetailActivity;
import com.sandalen.advanceddemo.R;
import com.sandalen.advanceddemo.model.GoodsModel;

import java.util.List;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.VH>{
    private List<GoodsModel> datas;
    private Context context;

    public GreenAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<GoodsModel> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        GoodsModel goodsModel = datas.get(position);
        holder.imageView.setImageResource(context.getResources()
                .getIdentifier(goodsModel.getIcon(),"mipmap",context.getPackageName()));
        holder.name.setText(goodsModel.getName());
        holder.info.setText(goodsModel.getInfo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("goodsModel",goodsModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 :datas.size();
    }

    public static class VH extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView name;
        private TextView info;

        public VH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.green_iv);
            name = itemView.findViewById(R.id.green_name);
            info = itemView.findViewById(R.id.green_info);
        }
    }
}
