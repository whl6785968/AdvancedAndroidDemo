package com.sandalen.advanceddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StaticBroadcast extends BroadcastReceiver {
    private TextView textView;

    public StaticBroadcast(){}

    public StaticBroadcast(TextView textView){
        this.textView = textView;
    }

    //该方法在主线程中执行，故不能执行耗时操作
    //如果执行超过10s会出现ANR
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            Log.e("broadcast",action);
            System.out.println("=================");
            if(TextUtils.equals(action,DynamicBroadcastActivity.MY_ACTION)){
                if(textView != null){
                    textView.setText(intent.getStringExtra("broadcast_content"));
                }
            }
        }
    }
}
