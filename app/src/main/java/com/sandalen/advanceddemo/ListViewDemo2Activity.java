package com.sandalen.advanceddemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.sandalen.advanceddemo.Adapter.ListViewDemo2Adapter;
import com.sandalen.advanceddemo.model.Lesson;
import com.sandalen.advanceddemo.model.LessonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ListViewDemo2Activity extends AppCompatActivity {

    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo2);

        listView = findViewById(R.id.list_view_demo2);

        new RequestDataAysncTask().execute();

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_view_header, null);
        listView.addFooterView(view);
    }


    public class RequestDataAysncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return request("http://www.imooc.com/api/teacher?type=2&page=1");
        }

        private String request(String url) {
            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setConnectTimeout(30000);
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                Log.e("error",responseCode+"");
                String result = null;
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStreamReader is = new InputStreamReader(connection.getInputStream());
                    BufferedReader br = new BufferedReader(is);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = br.readLine()) != null){
                        sb.append(line);
                    }

                    result = sb.toString();
                }

                return result;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            LessonResult lessonResult = new LessonResult();
            try {
                JSONObject jsonObject = new JSONObject(result);
                lessonResult.setStatus(jsonObject.getInt("status"));
                lessonResult.setMessage(jsonObject.getString("msg"));

                List<Lesson> datas = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    Lesson lesson = new Lesson();
                    JSONObject o = (JSONObject) jsonArray.get(i);
                    lesson.setName(o.getString("name"));
                    lesson.setId(o.getInt("id"));
                    datas.add(lesson);
                }

                lessonResult.setDatas(datas);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listView.setAdapter(new ListViewDemo2Adapter(ListViewDemo2Activity.this,lessonResult.getDatas()));
        }
    }
}