package com.example.eastsun.localbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    LocalBroadcastManager manager;
    Handler myHandler = new Handler(Looper.getMainLooper());
    LocalReceiver localReceiver = new LocalReceiver();

    public void init(){
        button = findViewById(R.id.send);
        textView = findViewById(R.id.text);
        manager = LocalBroadcastManager.getInstance(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("localBroadcast");
                manager.sendBroadcast(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("localBroadcast");
        manager.registerReceiver(localReceiver,intentFilter);

    }

    protected void onDestroy(){
        super.onDestroy();
        manager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText("接收到了本地广播");
                }
            });
        }
    }
}
