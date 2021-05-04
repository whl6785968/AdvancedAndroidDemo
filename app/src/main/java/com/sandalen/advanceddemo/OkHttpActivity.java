package com.sandalen.advanceddemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    private static final MediaType MEDIA_TYPE = MediaType.parse("text/x-markdown; charset=utf-8");
    private OkHttpClient httpClient = new OkHttpClient();
    private TextView tv;
    private static final String POST_URL = "https://api.github.com/markdown/raw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        tv = findViewById(R.id.ok_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_get:
                get();
                break;
            case R.id.menu_post:
                post();
                break;
            case R.id.menu_response:
                responseDemo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void responseDemo() {
        Request.Builder builder = new Request.Builder();
        builder.url("https://blog.csdn.net/weixin_37734988/article/details/90410269");
        Request request = builder.build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Headers headers = response.headers();
                String content = response.body().string();
                final StringBuilder buf = new StringBuilder();
                buf.append("code: " + code);
                buf.append("\nHeaders: \n" + headers);
                buf.append("\nbody: \n" + content);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(buf.toString());
                    }
                });
            }
        });
    }

    private void post() {
        Request.Builder builder = new Request.Builder();
        builder.url(POST_URL);
        builder.post(RequestBody.create(MEDIA_TYPE, "Hello world github/linguist#1 **cool**, and #1!"));
        Request request = builder.build();
        Call call = httpClient.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String s = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(s);
                        }
                    });
                }
            }
        });
    }

    private void get() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder;
                builder = new Request.Builder();
                builder.url("https://github.com/whl6785968/FrontendOfWaterMonitor2/blob/main/README.md");
                Request request = builder.build();
                Call call = httpClient.newCall(request);

                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(result);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.shutdown();

    }
}