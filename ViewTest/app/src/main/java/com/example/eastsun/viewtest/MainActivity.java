package com.example.eastsun.viewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            int heightMeasureSpec = button.getMeasuredHeight();
            int widthMeasureSpec  = button.getMeasuredWidth();
            Log.e(TAG,String.valueOf(heightMeasureSpec));
            Log.e(TAG,String.valueOf(widthMeasureSpec));
        }
    }

    String TAG = "Zh.EastSun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.post(new Runnable() {
                    @Override
                    public void run() {
                        int heightMeasureSpec = button.getMeasuredHeight();
                        int widthMeasureSpec  = button.getMeasuredWidth();
                        Log.e(TAG,String.valueOf(heightMeasureSpec));
                        Log.e(TAG,String.valueOf(widthMeasureSpec));
                    }
                });
            }
        });
    }
}
