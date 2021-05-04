package com.sandalen.advanceddemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sandalen.advanceddemo.R;

public class AppDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_item);

        Log.e("app-demo","onCreate"+getApplication());
    }
}
