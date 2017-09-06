package com.example.easts.toolbartest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(this,"点击了backup按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"点击了delete按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"点击了setting按钮",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
