package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//excute只能在主线程执行
//task的实例必须在UI Thread中创建
//注意防止内存泄漏
public class AsynTaskDemoActivity extends AppCompatActivity {

    public static final String APK_URL = "http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
    public static final String FILE_NAME = "imooc.apk";
    private ProgressBar progressBar;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn_task_demo);

        initView();
    }

    private void initView() {
        progressBar = findViewById(R.id.downloadProgressBar);
        button = findViewById(R.id.downloadButton);
        textView = findViewById(R.id.downloadTextView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskDemo asyncTaskDemo = new AsyncTaskDemo();
                asyncTaskDemo.execute(APK_URL);
            }
        });
    }

    //泛型1：参数
    //泛型2：进度
    //泛型3：结果
    public class AsyncTaskDemo extends AsyncTask<String,Integer,Boolean>{

        public static final int INIT_PROGRESS = 0;
        private String downloadUrl;

        //在执行前，主线程中，主要用于更新UI
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setText(R.string.downloading);
            textView.setText(R.string.downloading);
            progressBar.setProgress(INIT_PROGRESS);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(strings != null && strings.length > 0){
                try {
                    URL url = new URL(strings[0]);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();

                    int fileLength = connection.getContentLength();
                    int length = 0;
                    byte[] bytes = new byte[1024];
                    int curLength = 0;

                    downloadUrl = Environment.getExternalStorageDirectory() +
                            File.separator + FILE_NAME;

                    File file = new File(downloadUrl);

                    if(file.exists()){
                        boolean result = file.delete();
                        if(!result) return false;
                    }

                    OutputStream outputStream = new FileOutputStream(file);

                    while ((length = inputStream.read(bytes)) != -1){
                        outputStream.write(bytes,0,length);
                        curLength += length;
                        //发送进度
                        publishProgress(curLength * 100 / fileLength);
                    }

                    inputStream.close();
                    outputStream.close();

                }  catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            else{
                return false;
            }

            return true;
        }

        //执行后，主线程中
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            button.setText(aBoolean ? getString(R.string.download_finish) : getString(R.string.download_fail));
            textView.setText(aBoolean ? getString(R.string.download_finish) + ":" + downloadUrl : getString(R.string.download_fail));
        }

        //用于更新进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values != null && values.length > 0){
                progressBar.setProgress(values[0]);
            }
        }
    }
}