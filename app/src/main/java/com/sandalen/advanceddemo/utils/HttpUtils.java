package com.sandalen.advanceddemo.utils;

import com.sandalen.advanceddemo.biz.ChapterBiz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {

    public static final int CONNECT_TIMEOUT = 5000;

    public interface CallBack{
        void onRequestComplete(String result);
    }

    public static void doGetAsyn(String urlStr, CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = doGet(urlStr);
                if(result != null){
                    callBack.onRequestComplete(result);
                }
            }
        }).start();
    }

    public static String doGet(String urlStr) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
//            connection.setRequestProperty("accept","/");
//            connection.setRequestProperty("connection","Keep-Alive");

            if(connection.getResponseCode() == 200){
                is = connection.getInputStream();
                bos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];

                int len = -1;

                while ((len = is.read(bytes)) != -1){
                    bos.write(bytes,0,len);
                }
                bos.flush();
                return bos.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(bos != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(connection != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
