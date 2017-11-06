package com.example.easts.intenttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {

    TextView info;
    Button  button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        info = (TextView) findViewById(R.id.info);
        button  = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        Set<String> categories = new HashSet<>();
                        categories = intent.getCategories();
                        info.setText(intent.getAction()+categories);
                    }
                }).start();
            }
        });
    }
}
