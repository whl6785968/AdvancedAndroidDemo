package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DynamicBroadcastActivity extends AppCompatActivity {

    public static final String MY_ACTION = "com.sandalen.advanceddemo.sadasda";
    private StaticBroadcast broadcast;
    private EditText editText;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_broadcast);

        editText = findViewById(R.id.bd_edit);
        button = findViewById(R.id.bd_btn);
        textView = findViewById(R.id.bd_tv);

        broadcast = new StaticBroadcast(textView);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
//        intentFilter.addDataScheme("package");
        intentFilter.addAction(MY_ACTION);

        registerReceiver(broadcast,intentFilter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MY_ACTION);
                intent.putExtra("broadcast_content",editText.getText().toString());
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }
}