package com.example.easts.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private List<Fruit> fruits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        Fruit apple = new Fruit("apple",R.mipmap.ic_launcher_round);
        Fruit banana = new Fruit("banana",R.mipmap.ic_launcher_round);

        fruits.add(apple);
        fruits.add(banana);

        FruitAdapter adapter = new FruitAdapter(MainActivity.this,R.layout.list_item,fruits);
        listView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Fruit fruit = fruits.get(i);
        Toast.makeText(MainActivity.this,"您点击了"+fruit.getName(),Toast.LENGTH_SHORT).show();
    }
}
