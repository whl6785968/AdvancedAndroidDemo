package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.sandalen.advanceddemo.Adapter.ExpandListViewAdapter;
import com.sandalen.advanceddemo.biz.ChapterBiz;
import com.sandalen.advanceddemo.model.Chapter;
import com.sandalen.advanceddemo.utils.ChapterLab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpandedListViewActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ExpandListViewAdapter adapter;
    private List<Chapter> datas = new ArrayList<>();
    private ChapterBiz chapterBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_list_view);
        initView();

        loadDatas(true);
    }

    private void loadDatas(boolean useCache) {
        chapterBiz.loadDatas(this, new ChapterBiz.CallBack() {
            @Override
            public void loadSuccess(List<Chapter> chapters) {
                datas.clear();
                datas.addAll(chapters);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void loadFail(Exception ex) {
                ex.printStackTrace();
            }
        },useCache);
    }

    private void initView() {
        chapterBiz = new ChapterBiz();
        expandableListView = findViewById(R.id.expanded_list_view);
//        datas.clear();
//        datas.addAll(ChapterLab.generateDatas());
//        Log.e("error",datas.get(0).getChildren().get(0).getName());
        adapter = new ExpandListViewAdapter(this,datas);
        expandableListView.setAdapter(adapter);

    }
}