package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edt;
    private String path;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        edt = findViewById(R.id.external_edt);
        textView = findViewById(R.id.external_txt);
        Button writeBtn = findViewById(R.id.external_write);
        Button readBtn = findViewById(R.id.external_read);

        //不需要权限
//        Environment.DIRECTORY_DCIM
        //用于存储长时间存储的数据
        //SDCard/Android/data/包名/files/目录
//        getExternalFilesDir(Environment.DIRECTORY_DCIM)

        //SDCard/Android/data/包名/cache/目录
        //存储临时缓存的数据
//        getExternalCacheDir()


        /*
        * 内部存储：不需要权限
        * getFilesDir():data/data/包名/files
        * getCacheDir():data/data/包名/cache
        *
        * FileNotFoundException解决：
        * - 检查路径是否存在
        * - 检查权限是否设置
        * - 确认设备是否有SDCard if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        * */
        writeBtn.setOnClickListener(this::onClick);
        readBtn.setOnClickListener(this::onClick);


        //动态获取权限
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.external_write:
                try{
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "imooc.txt";
                    String content = edt.getText().toString();

                    File file = new File(path);
                    if(!file.exists()){
                        file.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(file,true);
                    fos.write(content.getBytes());
                    fos.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                break;
            case R.id.external_read:
                try {
                    FileInputStream fis = new FileInputStream(path);
                    byte[] bytes = new byte[1024];
                    StringBuilder sb = new StringBuilder();

                    int length;

                    while ((length = fis.read(bytes)) != -1){
                        sb.append(new String(bytes,0,length));
                    }

                    textView.setText(sb.toString());
                }
                catch (IOException e){

                }
                break;
        }
    }
}