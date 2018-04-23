package com.example.eastsun.brocastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button send;
    TextView text;
    MyReceiver myReceiver;

    public void init(){
        send = findViewById(R.id.send);
        text = findViewById(R.id.text);
        myReceiver = new MyReceiver();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("myBroadcast");
                sendBroadcast(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

}
