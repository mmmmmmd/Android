package com.example.easts.put_data;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Button button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.next);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String data = "data";
        Log.i(TAG,"点击了button,携带的数据为"+data);
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);
    }
}
