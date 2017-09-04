package com.example.linux_zdy.firstapplication.Register;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.linux_zdy.firstapplication.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button confirm;
    EditText input_account;
    EditText input_password;
    EditText confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        confirm = (Button)findViewById(R.id.register2);
        input_account = (EditText)findViewById(R.id.account);
        input_password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);

        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register2:
                final String account = input_account.getText().toString().trim();
                final String password = input_password.getText().toString().trim();
                String password2 = confirm_password.getText().toString().trim();

                if(password.equals(password2)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient();

                                RequestBody requestBody = new FormBody.Builder()
                                        .add("username", account)
                                        .add("password", password)
                                        .build();

                                Request request = new Request.Builder()
                                        .url("http://192.168.1.114:8080/index")
                                        .post(requestBody)
                                        .build();

                                Response response = client.newCall(request).execute();

                                String responseData = response.body().string();

                                if(responseData.equals("success")){

                                    AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
                                    dialog.setTitle("恭喜您");
                                    dialog.setMessage("注册成功,按确认键返回主菜单...");
                                    dialog.setCancelable(false);
                                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                                    dialog.show();

                                }else if(responseData.equals("fail")){

                                    Toast.makeText(getApplicationContext(),"该账号已经被注册",Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(getApplicationContext(),"发生未知错误",Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(),"两次密码输入不一致，请确认..",Toast.LENGTH_SHORT).show();
                }

                break;


            default:
                break;
        }

    }
}
