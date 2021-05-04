package com.sandalen.advanceddemo.biz;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sandalen.advanceddemo.dao.ChapterDao;
import com.sandalen.advanceddemo.model.Chapter;
import com.sandalen.advanceddemo.model.ChapterItem;
import com.sandalen.advanceddemo.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChapterBiz {
    private ChapterDao chapterDao = new ChapterDao();
    public void loadDatas(Context context,CallBack callBack,boolean useCache){
        AsyncTask<Boolean,Void,List<Chapter>> asyncTask = new AsyncTask<Boolean, Void, List<Chapter>>() {
            private Exception ex;

            @Override
            protected List<Chapter> doInBackground(Boolean... booleans) {
                List<Chapter> chapters = new ArrayList<>();
                if(booleans[0]){
                    //使用缓存
                    Log.e("TAG","use cache");
                    chapters.addAll(chapterDao.loadFromDb(context));
                }

                //从网络上获取数据
                if(chapters.isEmpty()){
                    Log.e("TAG","laod data from net");
                    //获取数据
                    List<Chapter>  chaptersFromInternet = loadDatasFromNet(context);
                    chapters.addAll(chaptersFromInternet);
                    //更新缓存
                    chapterDao.insertToDb(context,chaptersFromInternet);
                }

                return chapters;
            }

            @Override
            protected void onPostExecute(List<Chapter> chapters) {
                if(ex != null){
                    callBack.loadFail(ex);
                }
                else{
                    callBack.loadSuccess(chapters);
                }
            }
        };

        asyncTask.execute(useCache);
    }

    private List<Chapter> loadDatasFromNet(Context context) {
        String url = "https://www.imooc.com/api/expandablelistview";
        //获取数据
        String content = HttpUtils.doGet(url);
        //解析数据
        List<Chapter> chapterList = parse(content);
        //存入数据库
        return chapterList;
    }

    private List<Chapter> parse(String content) {
        List<Chapter> chapters = new ArrayList<>();
        Log.e("TAG",content);
        try {
            JSONObject jsonObject = new JSONObject(content);
            int errCode = jsonObject.optInt("errorCode");
            if(errCode == 0){
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject chapterJo = data.getJSONObject(i);
                    int id = chapterJo.optInt("id");
                    String name = chapterJo.optString("name");
                    Chapter chapter = new Chapter();
                    chapter.setId(id);
                    chapter.setName(name);

                    JSONArray childs = chapterJo.getJSONArray("children");
                    for (int j = 0; j < childs.length(); j++) {
                        JSONObject child = childs.getJSONObject(j);
                        int cid = child.optInt("id");
                        String cname = child.optString("name");

                        chapter.addChild(cid,cname);
                    }

                    chapters.add(chapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chapters;
    }

    public static interface CallBack{
        void loadSuccess(List<Chapter> chapters);
        void loadFail(Exception ex);
    }
}
