package com.example.easts.handleruse;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String UPPER_NUM = "upper";

    private EditText input ;
    private Button send;

    MyThread myThread;

    class MyThread extends Thread{
        public Handler handler;

        @Override
        public void run() {
            //首先创建Looper实例，此时也会创建一个消息队列
            Looper.prepare();
            //创建Handler实例接收消息
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {

                    //确认接受的消息内容是不是button触发时发送过来的
                    if(msg.what == 0x123){
                        int upper = msg.getData().getInt(UPPER_NUM);
                        List<Integer> nums = new ArrayList<Integer>();

                        //此时为代码块命名为outer,计算从2开始到upper为止的所有整数
                        outer:
                        for (int i=2;i<=upper;i++){
                            for(int j=2;j<=Math.sqrt(i);j++){
                                if(i!=2&&i%j==0){
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        Toast.makeText(MainActivity.this,nums.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            };
            Looper.loop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                message.what = 0x123;
                Bundle bundle = new Bundle();
                bundle.putInt(UPPER_NUM,Integer.parseInt(input.getText().toString()));
                message.setData(bundle);
                myThread.handler.sendMessage(message);
            }
        });

        myThread = new MyThread();
        myThread.start();
    }
}
