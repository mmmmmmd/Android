package com.example.linux_zdy.firstapplication.Login;

/*
   linux-zdy create in 6-1-2017
   完成了界面的编写
   测试了控件
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linux_zdy.firstapplication.Main.MainActivity;
import com.example.linux_zdy.firstapplication.R;
import com.example.linux_zdy.firstapplication.Register.RegisterActivity;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login;                   //登录按钮
    private Button register;                //跳转注册界面
    private CheckBox remember_password;     //记住密码按钮
    private EditText account_text;          //账号输入栏
    private EditText password_text;         //密码输入栏


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button)findViewById(R.id.login);
        register =(Button)findViewById(R.id.register);
        account_text = (EditText)findViewById(R.id.account);
        password_text = (EditText)findViewById(R.id.password);
        remember_password = (CheckBox)findViewById(R.id.remember_password);


        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);


        login.setOnClickListener(this);
        register.setOnClickListener(this);


        if(isRemember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            account_text.setText(account);
            password_text.setText(password);
            remember_password.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {


        final String username = account_text.getText().toString().trim();
        final String password = password_text.getText().toString().trim();


        //向服务器发送账号密码请求
        switch (v.getId()){
            case R.id.login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            OkHttpClient client = new OkHttpClient();

                            //账号密码键值对
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("name",username)
                                    .add("password",password)
                                    .build();

                            //向后台服务器发起请求
                            Request request = new Request.Builder()
                                    .url("http://192.168.1.114:8080/login")
                                    .post(requestBody)
                                    .build();

                            //接受后台服务器返回数据
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();

                            responseData = responseData.substring(1,responseData.length()-1);
                            
                            if(responseData.equals("success")){
                                editor = pref.edit();
                                if(remember_password.isChecked()){
                                    editor.putBoolean("remember_password",true);
                                    editor.putString("account",username);
                                    editor.putString("password",password);
                                }else{
                                    editor.clear();
                                }
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;


            default:
                break;
        }
    }
}
