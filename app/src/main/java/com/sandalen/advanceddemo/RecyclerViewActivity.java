package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sandalen.advanceddemo.Adapter.RecyclerViewAdapter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RecyclerViewActivity";
    private RecyclerView recyclerView;
    private List<String> datas = new ArrayList<>();
    private Button button;
    private Button changeBtn;
    private RecyclerViewAdapter adapter;
    private Button insertBtn;
    private Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.recycler_view);
        button = findViewById(R.id.recycler_btn);
        button.setOnClickListener(this::onClick);

        changeBtn = findViewById(R.id.change_btn);
        changeBtn.setOnClickListener(this::onClick);

        insertBtn = findViewById(R.id.insert_btn);
        insertBtn.setOnClickListener(this::onClick);

        deleteBtn = findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(this::onClick);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(this,recyclerView);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(RecyclerViewActivity.this, "第" + position + "数据被点击", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recycler_btn:
                Log.e(TAG, "onClick: ");
                for(int i = 0;i < 5;i++){
                    datas.add("第" + (datas.size() + 1) + "条数据");
                }
                Log.e(TAG, "onClick: "+datas.size() );
                adapter.setNames(datas);
                break;
            case R.id.change_btn:
                if(recyclerView.getLayoutManager().getClass() == LinearLayoutManager.class){
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                }
                else{
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
                break;
            case R.id.insert_btn:
                datas.add(1,"新数据");
                adapter.notifyItemInserted(1);
                adapter.notifyItemRangeChanged(1,datas.size() - 1);
                break;
            case R.id.delete_btn:
                datas.remove(1);
                adapter.notifyItemRemoved(1);
                adapter.notifyItemRangeRemoved(1,datas.size() - 1);
                break;
        }

    }
}