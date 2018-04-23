package com.example.easts.demo.Activty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easts.demo.R;

import java.io.IOException;


import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginAcitivity extends AppCompatActivity implements View.OnClickListener{

    //打印日志TAG
    final String TAG = "com.LoginActivity,tw";

    //相关控件
    private TextInputLayout inputUsername;
    private TextInputLayout inputPassword;
    private ImageView checkcode;
    private EditText inputCheckcode;
    private Button login;

    //保存用户数据的SharedPreference
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //更新UI的Handler
    Handler mHandler = new Handler(Looper.getMainLooper());

    //请求验证码相关参数
    String CHECKCODE_URL ="http://222.24.62.120/CheckCode.aspx" ;
    byte[] imageByte;

    //请求通过验证参数
    String REQUEST_URL = "http://222.24.62.120/default2.aspx";


    //点击的监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
            case R.id.checkcode:
                getCheckcode();
                break;
            case R.id.login:
                loginRequest();
                break;
        }
    }

    //初始化视图相关参数
    public void initView(){
        inputUsername = (TextInputLayout) findViewById(R.id.input_username);
        inputPassword = (TextInputLayout) findViewById(R.id.input_password);
        checkcode = (ImageView) findViewById(R.id.checkcode);
        inputCheckcode = (EditText) findViewById(R.id.input_checkcode);
        login = (Button) findViewById(R.id.login);

        checkcode.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    //获取验证码
    public void getCheckcode(){
        //在子线程中执行耗时操作（网络请求）
        new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化客户端
                OkHttpClient client = new OkHttpClient();
                try {
                    //获取服务器相应
                    Response response = client.newCall(new Request.Builder().url(CHECKCODE_URL).build()).execute();
                    //获取cookie
                    String Cookie = response.header("Set-Cookie");
                    //将cookie存储在Sharedpreference中令其持久化（啥时想用就拿来用）
                    //文件命名为user，并且以私有方式打开
                    sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    //将获取的cookie存入文件
                    editor.putString("Cookie",Cookie);
                    editor.commit();
                    //获取图片的二进制流
                    //为什么imageByte不用指定长度？
                    //因为在静态实例化过程中会为数组指定长度
                    //其长度为response.body().bytes();语句返回的byte数组的长度
                    imageByte = response.body().bytes();
                    //解码图片的二进制流
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                    Log.e(TAG, Cookie+"\n"+imageByte );
                    //使用handler更新UI
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            checkcode.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void loginRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String cookie = getSharedPreferences("user",MODE_PRIVATE).getString("Cookie","");
                    RequestBody requestBody = new FormBody.Builder()
                            .add("__VIEWSTATE","dDwxNTMxMDk5Mzc0Ozs+lYSKnsl/mKGQ7CKkWFJpv0btUa8=")
                            .add("Button1","")
                            .add("hidPdrs","")
                            .add("hidsc","")
                            .add("lbLanguage","")
                            .add("RadioButtonList1","ѧ��")
                            .add("Textbox1",inputUsername.getEditText().getText().toString())
                            .add("TextBox2",inputPassword.getEditText().getText().toString())
                            .add("txtSecretCode",inputCheckcode.getText().toString())
                            .add("txtUserName",inputUsername.getEditText().getText().toString())
                            .build();

                    String contentLength = String.valueOf(requestBody.contentLength());
                    Log.e("ContentLength",contentLength);
                    Request request = new Request.Builder()
                            .addHeader("Cache-Control","max-age=0")
                            .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Accept-Encoding","gzip, deflate")
                            .header("Accept-Language","zh-CN,zh;q=0.9")
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
                            .header("Cookie",cookie)
                            .header("Content-Length",contentLength)
                            .header("Content-Type","application/x-www-form-urlencoded")
                            .header("Referer","http://222.24.62.120/")
                            .header("Origin","http://222.24.62.120")
                            .header("Upgrade-Insecure-Requests","1")
                            .header("Connection","keep-alive")
                            .header("Host","222.24.62.120")
                            .url(REQUEST_URL)
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    //System.out.println(response.body().string());


                    if(response.header("P3P",null)==null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginAcitivity.this,"恭喜您登陆成功..",Toast.LENGTH_SHORT).show();
                            }
                        });

                        //使用SharedPreference保存重定向后的URL
                        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("mainUrl", String.valueOf(response.request().url()));
                        editor.commit();
                        Intent intent = new Intent(LoginAcitivity.this, MainActivity.class);
                        intent.putExtra("data",response.body().string());
                        startActivity(intent);
                    }else{
                        getCheckcode();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                inputUsername.getEditText().setText("");
                                inputPassword.getEditText().setText("");
                                inputCheckcode.setText("");
                                Toast.makeText(LoginAcitivity.this,"登陆失败了呦..",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();
        getCheckcode();
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}
