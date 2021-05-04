package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.sandalen.advanceddemo.Adapter.ListCardAdapter;
import com.sandalen.advanceddemo.model.Msg;
import com.sandalen.advanceddemo.utils.MsgLab;

import java.util.List;

public class CardViewDemo2Activity extends AppCompatActivity {

    private ListView listView;
    private ListCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_demo2);

        listView = findViewById(R.id.card_list_view);
        List<Msg> msgs = MsgLab.generateMockList();
        adapter = new ListCardAdapter(this,msgs);

        listView.setAdapter(adapter);
    }
}