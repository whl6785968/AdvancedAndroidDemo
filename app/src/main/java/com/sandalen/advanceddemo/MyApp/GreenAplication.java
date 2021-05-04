package com.sandalen.advanceddemo.MyApp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.sandalen.advanceddemo.model.DaoMaster;
import com.sandalen.advanceddemo.model.DaoSession;

public class GreenAplication extends Application {

    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
    }

    public void initDb(){
        //1.获取需要连接的数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "imooc2.db");
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        //2.创建数据库连接
        DaoMaster daoMaster = new DaoMaster(database);
        //3.创建数据库会话
        daoSession = daoMaster.newSession();
    }
}
