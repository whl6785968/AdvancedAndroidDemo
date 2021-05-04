package com.sandalen.advanceddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

public class DiglettActivitiy extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    public static final int CODE = 123;
    public static final int TIME_SEED = 500;
    public ImageView imageView;
    public TextView textView;
    public Button startButton;
    public int totalCount;
    public int successCount;
    public static final int MAX_COUNT = 10;
    private DiglettHandler diglettHandler = new DiglettHandler(this);
    public int[][] postion = {
            {342,180},{432,880},
            {521,256},{429,780},
            {456,976},{145,665},
            {123,678},{564,567},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diglett_activtiy);

        initView();
        setTitle("打地鼠");
    }

    private void initView() {
        startButton = findViewById(R.id.start_button);
        textView = findViewById(R.id.textView2);
        imageView = findViewById(R.id.diglett);

        startButton.setOnClickListener(this::onClick);
        imageView.setOnTouchListener(this::onTouch);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:
                start();
                break;
        }
    }

    private void start() {
        textView.setText("开始啦");
        startButton.setText("游戏中");
        startButton.setEnabled(false);
        next(0);
    }

    private void next(int delayTime) {
        int curPosition = new Random().nextInt(postion.length);

        Message message = Message.obtain();
        message.what = CODE;
        message.arg1 = curPosition;

        diglettHandler.sendMessageDelayed(message,delayTime);
        totalCount++;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setVisibility(View.GONE);
        successCount++;
        textView.setText("打到了"+ successCount +"只，共" + MAX_COUNT +"只.");
        return false;
    }

    public static class DiglettHandler extends Handler{
        WeakReference<DiglettActivitiy> diglettActivitiyWeakReference;

        public DiglettHandler(DiglettActivitiy activitiy) {
            this.diglettActivitiyWeakReference = new WeakReference<>(activitiy);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            DiglettActivitiy activity = diglettActivitiyWeakReference.get();

            switch (msg.what){
                case CODE:
                    if(activity.totalCount > activity.MAX_COUNT){
                        activity.clear();
                        Toast.makeText(activity,"地鼠打完了！",Toast.LENGTH_LONG).show();
                        return;
                    }

                    int curPosition = msg.arg1;
                    activity.imageView.setX(activity.postion[curPosition][0]);
                    activity.imageView.setY(activity.postion[curPosition][1]);
                    activity.imageView.setVisibility(View.VISIBLE);

                    int randomTime = new Random().nextInt(TIME_SEED) + TIME_SEED;
                    activity.next(randomTime);
                    break;
            }
        }
    }

    private  void clear(){
        totalCount = 0;
        successCount = 0;
        imageView.setVisibility(View.GONE);
        startButton.setText("点击开始");
        startButton.setEnabled(true);
    }
}