package com.example.eastsun.ipcbundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private final String message = "MainActivity的数据";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("data",message+random.nextInt(100));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
