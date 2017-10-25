package com.example.easts.chronometer;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    private Chronometer time;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (Chronometer) findViewById(R.id.time);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //为计时器设置开始的时间
                time.setBase(SystemClock.elapsedRealtime());
                //启动计时器
                time.start();
                start.setEnabled(false);
            }
        });

        time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(SystemClock.elapsedRealtime() - time.getBase() > 20*1000){
                    time.stop();
                    start.setEnabled(true);
                }
            }
        });
    }
}
