package com.sandalen.advanceddemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.sandalen.advanceddemo.model.GoodsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GreenDataUtils {
    private static final String TAG = "GreenDataUtils";
    public static String getJson(String fileName, Context context){
        StringBuilder sb = new StringBuilder();
        AssetManager assets = context.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(assets.open(fileName)));
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {

        }

        return sb.toString();
    }

    public static List<GoodsModel> getJsonModels(String json){
        Log.e(TAG, "getJsonModels: " + json );
        List<GoodsModel> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i < jsonArray.length() ; i ++) {
                JSONObject object = jsonArray.getJSONObject(i);
                GoodsModel goodsModel = new GoodsModel();
                goodsModel.setGoodsId(object.getInt("goodsId"));
                goodsModel.setIcon(object.getString("icon"));
                goodsModel.setInfo(object.getString("info"));
                goodsModel.setName(object.getString("name"));
                goodsModel.setType(object.getString("type"));
                result.add(goodsModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
