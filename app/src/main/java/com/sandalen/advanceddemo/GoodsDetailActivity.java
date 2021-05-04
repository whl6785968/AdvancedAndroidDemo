package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sandalen.advanceddemo.daoManager.GreenDaoManager;
import com.sandalen.advanceddemo.model.GoodsModel;

public class GoodsDetailActivity extends AppCompatActivity {
    private EditText editText;
    private GreenDaoManager greenDaoManager;
    private GoodsModel goodsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        editText = findViewById(R.id.et_info);
        greenDaoManager = new GreenDaoManager(this);
        goodsModel = getIntent().getParcelableExtra("goodsModel");
        editText.setText(goodsModel.getInfo());
    }

    public void onEditClick(View view){
        String info = editText.getText().toString();
        goodsModel.setInfo(info);
        greenDaoManager.updateGoodsInfo(goodsModel);
        onBackPressed();
    }

    public void onDelClick(View view){
        String info = editText.getText().toString();
        goodsModel.setInfo(info);
        greenDaoManager.deleteGoodsInfo(goodsModel);
        onBackPressed();
    }
}