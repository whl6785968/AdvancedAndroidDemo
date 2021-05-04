package com.sandalen.advanceddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CountdownActivity extends AppCompatActivity {

    public static final int COUNTDOWN_TIME_CODE = 10001;
    public static final int MAX_TIME = 10;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        textView = findViewById(R.id.countdown_text);

        //Handler只有在发送延迟消息才会导致内存泄露，泄露的原因是因为用了MainLooper，
        // 这个looper在整个程序的生命周期都存在，内部的MessageQueue持有了延迟发送的Message，
        // Message持有了Handler导致Activity无法回收。
//      Handler normalHandler = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//            }
//        };

        CountdownTimeHandler handler = new CountdownTimeHandler(this){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };

        Message message = Message.obtain();
        message.what = COUNTDOWN_TIME_CODE;
        message.arg1 = MAX_TIME;

        handler.sendMessageDelayed(message,1000);
    }

    //静态内部类不持有外部引用，避免activity的泄露
    public static class CountdownTimeHandler extends Handler {
        //采用弱引用避免内存泄漏问题
        WeakReference<CountdownActivity> weakReference;

        public CountdownTimeHandler(CountdownActivity weakReference) {
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            CountdownActivity countdownActivity = weakReference.get();

            switch (msg.what){
                case COUNTDOWN_TIME_CODE:
                    int value = msg.arg1;
                    countdownActivity.textView.setText(String.valueOf(value--));

                    if(value > 0){
                        Message message = Message.obtain();
                        message.what = COUNTDOWN_TIME_CODE;
                        message.arg1 = value;
                        sendMessageDelayed(message,1000);
                    }
                    break;
            }
        }
    }
}