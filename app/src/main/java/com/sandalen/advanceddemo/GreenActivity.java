package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sandalen.advanceddemo.Adapter.GreenAdapter;
import com.sandalen.advanceddemo.daoManager.GreenDaoManager;
import com.sandalen.advanceddemo.model.GoodsModel;
import com.sandalen.advanceddemo.utils.GreenDataUtils;

import java.util.List;

public class GreenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GreenAdapter adapter;
    private GreenDaoManager greenDaoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setDatas(greenDaoManager.queryAll());
    }

    private void init() {
        this.greenDaoManager = new GreenDaoManager(this);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.green_recycler_view);
        adapter = new GreenAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void onAddGoodClick(View view){
        greenDaoManager.insertGoods();
    }

    public void onQueryAllClick(View view){
        List<GoodsModel> goodsModels = greenDaoManager.queryAll();
        adapter.setDatas(goodsModels);
    }

    public void onQueryFruitsClick(View view){
        List<GoodsModel> goodsModels = greenDaoManager.queryFruits();
        adapter.setDatas(goodsModels);
    }

    public void onAddSnackClick(View view){
        List<GoodsModel> goodsModels = greenDaoManager.querySnacks();
        adapter.setDatas(goodsModels);
    }


}