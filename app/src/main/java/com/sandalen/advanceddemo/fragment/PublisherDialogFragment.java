package com.sandalen.advanceddemo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sandalen.advanceddemo.EventBusDemo2Activity;
import com.sandalen.advanceddemo.event.FailureEvent;
import com.sandalen.advanceddemo.event.PostingEvent;
import com.sandalen.advanceddemo.event.SuccessEvent;

import org.greenrobot.eventbus.EventBus;

public class PublisherDialogFragment extends DialogFragment {

    public interface OnEventListener{
        void onSuccess();
        void onFailure();
    }

    private OnEventListener eventListener;

    public void setEventListener(OnEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        String[] items = {"success","fail","posting"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        //success
//                        Intent intent = new Intent();
//                        intent.setAction(EventBusDemo2Activity.HANDLER_EVENT_ACTION);
//                        intent.putExtra(EventBusDemo2Activity.STATUS_KEY,true);
                        //注册监听
//                        eventListener.onSuccess();
                        //eventbus
                        EventBus.getDefault().post(new SuccessEvent());
                        break;
                    case 1:
                        //fail
//                        eventListener.onFailure();
                        EventBus.getDefault().post(new FailureEvent());
                        break;
                    case 2:
                        new Thread("posting"){
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new PostingEvent(Thread.currentThread().toString()));
                                super.run();
                            }
                        }.start();

                }
            }
        });
        return builder.create();
    }
}
