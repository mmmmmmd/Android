package com.example.easts.park;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
   缺少匹配车牌号的正则表达式
 */

public class RegistActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText input_phoneNum;
    private EditText input_plateNum;
    private EditText input_password;
    private EditText input_password_twice;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        input_phoneNum = (EditText) findViewById(R.id.input_phoneNum);
        input_plateNum = (EditText) findViewById(R.id.car_id);
        input_password = (EditText) findViewById(R.id.input_password);
        input_password_twice = (EditText) findViewById(R.id.twice_password);
        send = (Button) findViewById(R.id.regist);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regist:
                regist();
                break;
            default:
                break;
        }
    }

    private void regist(){
        final String phoneNum = input_phoneNum.getText().toString();
        final String carId = input_plateNum.getText().toString();
        final String password = input_password.getText().toString();
        String password_twice = input_password_twice.getText().toString();
        //用作手机号码的匹配的正则表达式
        final String regix_phone = "1(3|5|7|8)[0-9]{9}";
        if(!phoneNum.matches(regix_phone)){
            Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            return ;
        }
        else if(!password.equals(password_twice)){
            Toast.makeText(this,"请确认两次输入密码是否一致",Toast.LENGTH_SHORT).show();
            return ;
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("phonenumber", phoneNum)
                                .add("license", carId)
                                .add("password", password)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://192.168.1.157")
                                .post(body)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();

                        if (responseData.equals("success")){
                            Toast.makeText(RegistActivity.this,"账号注册成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistActivity.this,"注册失败请重新尝试",Toast.LENGTH_SHORT).show();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}