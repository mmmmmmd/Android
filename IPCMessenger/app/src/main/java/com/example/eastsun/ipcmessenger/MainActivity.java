package com.example.eastsun.ipcmessenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private static final String TAG="MainActivity";

    private Messenger mService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService=new Messenger(service);
            Message msg=Message.obtain(null,0x123);
            Bundle data=new Bundle();
            data.putString("msg","hello this is client");
            msg.setData(data);
            msg.replyTo=mGetReplyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger mGetReplyMessenger=new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x456:
                    Log.d(TAG,"receive msg from service:"+msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MessengerService.class);
                bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }

    @Override
    protected void onDestroy(){
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
