package com.example.eastsun.ipcmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    public static final String TAG = "MainActivity";
    public static final int MyContents = 0x123;
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    public static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MyContents:
                    Messenger server=msg.replyTo;
                    Message replyMessage=Message.obtain(null,0x456);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","服务端已经接受到了客户端的信息...");
                    replyMessage.setData(bundle);
                    try {
                        server.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG,"receive msg from client:"+"\t"+msg.getData().getString("msg"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
