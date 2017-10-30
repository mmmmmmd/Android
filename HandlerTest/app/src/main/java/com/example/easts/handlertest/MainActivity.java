package com.example.easts.handlertest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int[] images = new int[]{
      R.drawable.car,R.drawable.contact,
            R.drawable.delete,R.drawable.friends,
            R.drawable.message,R.drawable.trend
    };

    private ImageView image;

    private int currentImageId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);

        final Handler myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x1233){
                    image.setImageResource(images[currentImageId++%images.length]);
                }
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(0x1233);
            }
        },0,1200);
    }
}
