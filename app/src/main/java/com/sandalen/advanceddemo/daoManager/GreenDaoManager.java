package com.sandalen.advanceddemo.daoManager;

import android.content.Context;

import com.sandalen.advanceddemo.MyApp.GreenAplication;
import com.sandalen.advanceddemo.model.GoodsModel;
import com.sandalen.advanceddemo.model.GoodsModelDao;
import com.sandalen.advanceddemo.utils.GreenDataUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class GreenDaoManager {

    private Context context;
    private GoodsModelDao goodsModelDao;

    public GreenDaoManager(Context context) {
        this.context = context;
        this.goodsModelDao = GreenAplication.daoSession.getGoodsModelDao();
    }

    public void insertGoods(){
        String json = GreenDataUtils.getJson("goods.json",context);
        goodsModelDao.insertInTx(GreenDataUtils.getJsonModels(json));
    }

    public List<GoodsModel> queryAll(){
        QueryBuilder<GoodsModel> builder = goodsModelDao.queryBuilder();
        builder = builder.orderAsc();
        return builder.list();
    }

    public List<GoodsModel> queryFruits(){
        QueryBuilder<GoodsModel> builder = goodsModelDao.queryBuilder();
        builder = builder.where(GoodsModelDao.Properties.Type.eq("0"))
                .orderAsc(GoodsModelDao.Properties.Id);
        return builder.list();
    }

    public List<GoodsModel> querySnacks(){
        QueryBuilder<GoodsModel> builder = goodsModelDao.queryBuilder();
        builder = builder.where(GoodsModelDao.Properties.Type.eq("1"))
                .orderAsc(GoodsModelDao.Properties.Id);
        return builder.list();
    }

    public void updateGoodsInfo (GoodsModel model) {
        goodsModelDao.update(model);
        goodsModelDao.updateInTx();
    }

    /**
     * 删除指定商品的商品信息
     * @param model
     */
    public void deleteGoodsInfo (GoodsModel model) {
        goodsModelDao.deleteByKey(model.getId());
    }
}
