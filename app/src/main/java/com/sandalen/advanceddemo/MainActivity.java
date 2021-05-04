package com.sandalen.advanceddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        
        //创建handler
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e("Handler",""+msg.what);

                if(msg.what == 1001){
                    textView.setText("imooc");
                }
            }
        };

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1001);
                        Message message = Message.obtain();
                        message.what = 1001;
                        message.arg1 = 1002;
                        message.arg2 = 1003;
                        message.obj = MainActivity.this;

                        //在某个时间点发送数据
//                        handler.sendMessageAtTime(message, SystemClock.uptimeMillis() + 2000);
                        //延迟发送消息
//                        handler.sendMessageDelayed(message,2000);

                        //在子线程中通知主线程更新UI
//                        Runnable runnable = new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        };
//
//                        handler.post(runnable);
//                        runnable.run();
                    }
                }).start();
            }
        });
    }
}