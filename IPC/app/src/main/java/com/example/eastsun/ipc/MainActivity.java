package com.example.eastsun.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eastsun.ipc.bean.Person;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView content;

    private IMyAidlInterface mAidl;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAidl = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAidl = null;
        }
    };

    private void initView(){
        button = findViewById(R.id.button);
        content = findViewById(R.id.content);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Person person = new Person("Zh.eastsun"+random.nextInt(10));
                try{
                    mAidl.addPerson(person);
                    List<Person> personList = mAidl.getPersonList();
                    content.setText(personList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = new Intent(getApplicationContext(),MyAidlService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(connection);
    }
}
