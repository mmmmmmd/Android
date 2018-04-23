package com.example.eastsun.ipcfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "Object";

    private void recoverFromFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File cachedFile=new File("cachedfile");
                if(cachedFile.exists()){
                    ObjectInputStream objectInputStream=null;
                    try {
                        objectInputStream=new ObjectInputStream(
                                new FileInputStream(cachedFile));
                        user=(User)objectInputStream.readObject();
                        Log.e(TAG,"recoverFromFile"+user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        if(objectInputStream!=null)
                            try {
                                objectInputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onResume(){
        super.onResume();
        recoverFromFile();
    }
}
