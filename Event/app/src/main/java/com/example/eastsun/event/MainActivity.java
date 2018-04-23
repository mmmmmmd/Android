package com.example.eastsun.event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private VelocityTracker vt;
    private String TAG = "MainActivity.eastsun2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View layout = findViewById(R.id.main);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG,"点击了屏幕...");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        acquireVelocityTracker(event);
                        vt.computeCurrentVelocity(1000);
                        Log.e(TAG,vt.getXVelocity()+"\n"+vt.getYVelocity());
                        break;
                    case MotionEvent.ACTION_UP:
                        releaseVelocityTracker();
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    private void acquireVelocityTracker(final MotionEvent event){
        if(vt == null ){
            vt = VelocityTracker.obtain();
        }
        vt.addMovement(event);
    }

    private void releaseVelocityTracker(){
        if(vt != null){
            vt.clear();
            vt.recycle();
            vt = null;
        }
    }
}
