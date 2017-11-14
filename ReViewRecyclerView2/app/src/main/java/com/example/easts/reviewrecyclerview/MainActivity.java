package com.example.easts.reviewrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> text ;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initText();

        recyclerView = (RecyclerView) findViewById(R.id.show);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        MyAdapter adapter = new MyAdapter(MainActivity.this,text);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        Log.e("EastSun",text.toString());

    }

    public void initText(){
        text = new ArrayList<String>();
        for (int i=0;i<50;i++)
            text.add("Item"+i);
    }
}
