package com.example.eastsun.ipcfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private final String TAG = "Object";

    private void  initView(){
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    public void requestAllPower(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
    }

    private void persistToFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User("zdy","man",20);
                File dir = new File("/mnt/sdcard/dir");
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File cachedFile = new File("/mnt/sdcard/dir/cachedfile");
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(
                            new FileOutputStream(cachedFile)
                    );
                    objectOutputStream.writeObject(user);
                    Log.e(TAG,"persist user"+user);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(objectOutputStream!=null){
                        try {
                            objectOutputStream.close();
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
        setContentView(R.layout.activity_main);
        initView();
        requestAllPower();
    }

    @Override
    protected void onResume(){
        super.onResume();
        persistToFile();
    }
}
