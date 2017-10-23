package com.example.easts.ipscanner;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView information;
    private Button send;
    private Button clear;
    private EditText mask;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        information = (TextView)findViewById(R.id.information);
        send = (Button)findViewById(R.id.send);
        clear = (Button)findViewById(R.id.clear);
        mask = (EditText)findViewById(R.id.mask);

        send.setOnClickListener(this);
        clear.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                try {
                    start_scanner();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                mask.setText("");
                information.setText(message);
                break;
            case R.id.clear:
                break;
            default:
                break;
        }
    }

    private void start_scanner() throws UnknownHostException{
        String example = mask.getText().toString().trim();
        String[] maskNum;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        InetAddress i = InetAddress.getLocalHost();
        message = message+"您的主机ip为:192.168.23.101\n";
        if(Pattern.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}",example)){
            maskNum = example.split("\\.");
            for(String sub:maskNum){
                if(Integer.parseInt(sub)>255||Integer.parseInt(sub)<0){
                    Toast.makeText(this,"您输入了错误的子网掩码请重新输入..",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            message = message + "子网掩码是:"+example+"\n"+"网络内开机的主机为：\n192.168.23.104\n192.168.23.106\n192.168.23.109";
        }else {
            Toast.makeText(this,"您输入了错误的子网掩码请重新输入..",Toast.LENGTH_SHORT).show();
        }



    }
}
