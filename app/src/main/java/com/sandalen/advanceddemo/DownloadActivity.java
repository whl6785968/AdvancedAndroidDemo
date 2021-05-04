package com.sandalen.advanceddemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sandalen.advanceddemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by suibianbei on 2017/4/6.
 */

public class DownloadActivity extends Activity {

    private Handler mHandler;
    public static final int DOWNLOAD_MESSAGE_CODE = 100001;
    public static final int DOWNLOAD_MESSAGE_FAIL_CODE = 100002;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }
        }


    }
    private void download(String appUrl){
        try {
            URL url = new URL(appUrl);
            URLConnection urlConnection = url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            /**
             * 获取文件的总长度
             */
            int contentLength = urlConnection.getContentLength();

            String downloadFolderName = Environment.getExternalStorageDirectory()
                    + File.separator+"imooc"+File.separator;

            File file = new File(downloadFolderName);
            if (!file.exists()){
                file.mkdir();
            }

            String fileName = downloadFolderName + "imooc.apk";

            File apkFile = new File(fileName);

            if (apkFile.exists()){
                apkFile.delete();
            }

            int downloadSize = 0;
            byte[] bytes = new  byte[1024];

            int length = 0;

            OutputStream outputStream = new FileOutputStream(fileName);
            while ((length = inputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,length);
                downloadSize += length;
                /**
                 * update UI
                 */

                Message message = Message.obtain();
                message.obj = downloadSize * 100 / contentLength;
                message.what = DOWNLOAD_MESSAGE_CODE;
                mHandler .sendMessage(message);
            }

            Log.d("下载完毕",System.currentTimeMillis() + "");
            inputStream.close();
            outputStream.close();

        }catch (MalformedURLException e){
            notifyDownloadFaild();
            e.printStackTrace();
        }catch (IOException e){
            notifyDownloadFaild();
            e.printStackTrace();
        }
    }

    private  void  notifyDownloadFaild(){
        Message message = Message.obtain();
        message.what = DOWNLOAD_MESSAGE_FAIL_CODE;
        mHandler .sendMessage(message);
    }

    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
//                alertBuilder.setTitle(getString(R.string.permission_necessary));
//                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        Log.d("permission","权限开启完毕");

        //add your further process after giving permission or to download images from remote server.
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        System.out.println(ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE));
        /**
         * 主线程 -->start
         * 点击按钮 |
         * 发起下载 |
         * 开启子线程做下载 |
         * 下载完成后通知主线程 | -->主线程更新进度条
         */

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download("http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk");
                    }
                }).start();

            }
        });

        mHandler =  new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case DOWNLOAD_MESSAGE_CODE:
                        if(msg.obj!=null)
                            progressBar.setProgress((Integer) msg.obj);
                        break;
                    case DOWNLOAD_MESSAGE_FAIL_CODE:


                }
            }
        };

    }
}
