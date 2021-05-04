package com.sandalen.advanceddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sandalen.advanceddemo.event.FailureEvent;
import com.sandalen.advanceddemo.event.PostingEvent;
import com.sandalen.advanceddemo.event.StickyEvent;
import com.sandalen.advanceddemo.event.SuccessEvent;
import com.sandalen.advanceddemo.fragment.PublisherDialogFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

//EventBus：线程模式
//posting
//Main：订阅方（回调函数）运行在主线程，发布方会被订阅放阻塞
//Main order：发布方不会被订阅方回调函数阻塞
//background：若发布方在非ui线程，订阅方也执行在非ui线程，若发布方在主线程，则
//订阅方开启一个非ui线程执行
//async：事件处理方运行在一个新开独立的线程中，即和发布方始终不处于一个线程
//可以放心做耗时操作

//EventBus：混淆规则
//在build.gradle中的minifyEnable如果设置为true，则会把没有用到的方法删除
//则会导致事件回调函数失效，故需设置混淆规则以防止特定方法不会被删除

//EventBus：粘性事件
//eventBus一般遵循事件->订阅->发布过程，即订阅事件，当发布事件后，订阅得到消息执行操作
//而使用粘性事件可以事件->发布->订阅 ，即先发布事件，再订阅
public class EventBusDemo2Activity extends AppCompatActivity {
    private static final String TAG = "EventBusDemo2Activity";
    public static final String HANDLER_EVENT_ACTION = "handler_event_action";
    public static final String STATUS_KEY = "status_key";

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if(action.equals(HANDLER_EVENT_ACTION)){
//                boolean status = intent.getBooleanExtra(STATUS_KEY,false);
//                if (status) {
//                    setImageSrc(R.drawable.ic_happy);
//                }
//                else
//                {
//                    setImageSrc(R.drawable.ic_sad);
//                }
//            }
//        }
//    };
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(HANDLER_EVENT_ACTION);
//        registerReceiver(receiver,intentFilter);
    }

    @Subscribe
    public void onSuccessEvent(SuccessEvent successEvent){
        setImageSrc(R.drawable.ic_happy);
    }

    @Subscribe
    public void onFailureEvent(FailureEvent failureEvent){
        setImageSrc(R.drawable.ic_sad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.stick_event:
                Intent intent = new Intent(this, StikcyActivity.class);
                EventBus.getDefault().postSticky(new StickyEvent("sticky-message"));
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //Posting 发布消息在哪个线程则订阅方就在哪个线程
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(PostingEvent postingEvent){
        String s = Thread.currentThread().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPublisherThreadInfo(postingEvent.getThreadInfo());
                setSubscriberThreadInfo(s);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
//        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
    }

    private void setPublisherThreadInfo(String threadInfo){
        setTextView(R.id.publisherThreadTextView,threadInfo);
    }

    private void setSubscriberThreadInfo(String threadInfo){
        setTextView(R.id.subscriberThreadTextView,threadInfo);
    }

    private void setTextView(int resId,String text){
        TextView textView = findViewById(resId);
        textView.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_demo2);

        setTitle("Subscriber");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                PublisherDialogFragment publisherDialogFragment = new PublisherDialogFragment();
                publisherDialogFragment.setEventListener(new PublisherDialogFragment.OnEventListener() {
                    @Override
                    public void onSuccess() {
                        setImageSrc(R.drawable.ic_add);
                    }

                    @Override
                    public void onFailure() {
                        setImageSrc(R.drawable.ic_happy);
                    }
                });

                publisherDialogFragment.show(getSupportFragmentManager(),"publisher");
            }
        });
    }

    private void setImageSrc(int resId){
        ImageView imageView = findViewById(R.id.emotionImageView);
        if(imageView != null){
            imageView.setImageResource(resId);
        }
        else {
            Log.e(TAG, "setImageSrc: error");
        }
    }
}