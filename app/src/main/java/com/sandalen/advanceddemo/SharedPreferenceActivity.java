package com.sandalen.advanceddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        EditText edtUsername = findViewById(R.id.shared_username);
        EditText edtPwd = findViewById(R.id.shared_pwd_self);
        Button button = findViewById(R.id.shared_button);

        SharedPreferences preferences = getSharedPreferences("myshare",MODE_PRIVATE);
        String usernameStr = preferences.getString("username", "");
        String pwdStr = preferences.getString("password","");
        if(!usernameStr.equals("") && !pwdStr.equals("")){
            edtUsername.setText(usernameStr);
            edtPwd.setText(pwdStr);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String pwd = edtPwd.getText().toString();

                if(username.equals("123") && pwd.equals("666")){
                    //1.获取SharedPreferences对象
                    SharedPreferences sharedPreferences = getSharedPreferences("myshare",MODE_PRIVATE);
                    //2.获取editor对象
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //3.存储信息
                    editor.putString("username",username);
                    editor.putString("password",pwd);
                    //4.提交
                    editor.commit();
                    Toast.makeText(SharedPreferenceActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SharedPreferenceActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}