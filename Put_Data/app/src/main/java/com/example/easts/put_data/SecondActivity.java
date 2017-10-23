package com.example.easts.put_data;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = (TextView) findViewById(R.id.text);
        Intent intent = getIntent();
        final String receive_data = intent.getStringExtra("data");
        Log.i(TAG,"接收到的数据为:"+receive_data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(receive_data);
            }
        });
    }
}
