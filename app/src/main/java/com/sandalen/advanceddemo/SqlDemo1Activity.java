package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;

import java.io.File;

public class SqlDemo1Activity extends AppCompatActivity {
    private EditText nameEdt,ageEdt,idEdt;
    private RadioGroup radioGroup;
    private String genderStr = "男";
    private ListView stuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_demo1);

        nameEdt = findViewById(R.id.sql_edt_name);
        ageEdt = findViewById(R.id.sql_edt_age);
        idEdt = findViewById(R.id.sql_id_edt);

        radioGroup = findViewById(R.id.sql_radio_gp);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.sql_male){
                    genderStr = "男";
                }
                else{
                    genderStr = "女";
                }
            }
        });

        stuList = findViewById(R.id.stu_list);
    }

    public void operate(View v){
        String path = Environment.getExternalStorageDirectory() + File.separator + "stu.db";
        //如果只有一个数据库名称，则该数据库位置在私有目录下
        //如果带SD卡路径，则数据库在指定目录下
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this,path,null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                //若数据库存在，直接打开
                //若不存在，先创建再打开
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //版本号升级，执行该方法
            }
        };

        SQLiteDatabase database = helper.getReadableDatabase();
        //查询
//        database.rawQuery();
        //添加、删除、修改、创建表
//        database.execSQL();

        String name = nameEdt.getText().toString();
        String age = ageEdt.getText().toString();
        String id = idEdt.getText().toString();

        switch (v.getId()){
            case R.id.sql_add:

//                String sql = "insert into info_tb (name,age,gender) values(?,?,?)";
//                database.execSQL(sql,new String[]{name,age,genderStr});
                ContentValues contentValues = new ContentValues();
                contentValues.put("name",name);
                contentValues.put("age",age);
                contentValues.put("gender",genderStr);
                /*
                * 参数2：可以为空列
                * 如果参数3为null或者没有数据，语法错误
                * 如果指定参数2，则语句变为 insert into info_tb (可空列) values(null)
                * */
                database.insert("info_tb",null,contentValues);
                break;
            case R.id.sql_delete:
                String delete_sql = "delete from info_tb where _id = " + id;
                database.execSQL(delete_sql);
                break;
            case R.id.sql_update:
                String update_sql = "update info_tb set name = ?,age = ?,gender = ? where _id = ?";
                database.execSQL(update_sql,new String[]{name,age,genderStr,id});
                break;
            case R.id.sql_check:
//                String check_sql = "select * from info_tb";
//
//                if(!id.equals("")){
//                    check_sql += " where _id=" + id;
//                }
//
//                Cursor cursor = database.rawQuery(check_sql, null);

                //参数2：查询的列，为null表示查询所有列
                //参数3：条件 格式：列1=?
                //参数4：条件值 new String[]{"aa"}
                //参数5：分组
                //参数6：having
                //参数7：order by
                Cursor cursor = database.query("info_tb",
                        null,null,null,null,null,null);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        this,
                        R.layout.item_sql,
                        cursor,
                        new String[]{"_id","name","age","gender"},
                        new int[]{R.id.sql_check_id,R.id.sql_check_name,R.id.sql_check_age,R.id.sql_check_sex},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                stuList.setAdapter(adapter);
                break;
        }
    }
}