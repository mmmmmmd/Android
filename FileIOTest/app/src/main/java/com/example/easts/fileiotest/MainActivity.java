package com.example.easts.fileiotest;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity {

    final String FILE_NAME = "zdy.bin";

    private EditText input_text;
    private Button send;
    private TextView message;
    private Button receive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_text = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        message = (TextView) findViewById(R.id.text);
        receive = (Button) findViewById(R.id.receive);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_text.getText().toString()==""){
                    Toast.makeText(MainActivity.this,"您输入了空数据",Toast.LENGTH_SHORT).show();
                }else{
                    if(write(input_text.getText().toString()))
                        Toast.makeText(MainActivity.this,"成功写入数据",Toast.LENGTH_SHORT).show();
                    else{
                        Toast.makeText(MainActivity.this,"写入数据失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(read() == null){
                    Toast.makeText(MainActivity.this,"未读取到数据",Toast.LENGTH_SHORT).show();
                }else{
                    message.setText(read());
                }
            }
        });
    }

    public boolean write(String data){
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME,MODE_APPEND);
            PrintStream ps = new PrintStream(fos);
            ps.println(data);
            ps.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String read(){
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buff = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder("");
            while((hasRead=fis.read(buff))>0){
                sb.append(new String(buff,0,hasRead));
            }
            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
