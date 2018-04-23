package com.example.eastsun.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_add_frag1;
    private Button btn_add_frag2;
    private Button btn_remove_frag2;
    private Button btn_replace_frag1;

    protected void addFragment(Fragment fragment,String tag){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container,fragment,tag);
        transaction.commit();
    }

    protected void removeFragment2(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag("fragment2");
        transaction.remove(fragment);
        transaction.commit();
    }

    protected void replaceFragment1(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment2 fragment = new Fragment2();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    protected void init(){
        btn_add_frag1 = findViewById(R.id.btn_add_frag1);
        btn_add_frag2 = findViewById(R.id.btn_add_frag2);
        btn_remove_frag2 = findViewById(R.id.btn_remove_frag2);
        btn_replace_frag1 = findViewById(R.id.btn_replace_frag1);

        btn_add_frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new Fragment1(),"fragment1");
            }
        });

        btn_add_frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new Fragment2(),"fragment2");
            }
        });

        btn_remove_frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment2();
            }
        });

        btn_replace_frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment1();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
}
