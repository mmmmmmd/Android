package com.example.eastsun.viewpager;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends FragmentActivity implements View.OnClickListener{

    private TextView title1;
    private TextView title2;
    private TextView title3;
    private ViewPager pager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initView();

        title1.setOnClickListener(this);
        title2.setOnClickListener(this);
        title3.setOnClickListener(this);
        pager.addOnPageChangeListener(new MyOnPageChangeListener());

        list = new ArrayList<>();
        list.add(new FirstFragment());
        list.add(new SecondFragment());
        list.add(new ThirdFragment());
        adapter = new TabFragmentAdapter(getSupportFragmentManager(),list);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        title1.setBackgroundColor(Color.BLUE);

    }

    private void initView(){
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        title3 = findViewById(R.id.title3);
        pager = findViewById(R.id.container);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title1:
                pager.setCurrentItem(0);
                title1.setBackgroundColor(Color.BLUE);
                title2.setBackgroundColor(Color.WHITE);
                title3.setBackgroundColor(Color.WHITE);
                break;
            case R.id.title2:
                pager.setCurrentItem(1);
                title1.setBackgroundColor(Color.WHITE);
                title2.setBackgroundColor(Color.BLUE);
                title3.setBackgroundColor(Color.WHITE);
                break;
            case R.id.title3:
                pager.setCurrentItem(2);
                title1.setBackgroundColor(Color.WHITE);
                title2.setBackgroundColor(Color.WHITE);
                title3.setBackgroundColor(Color.BLUE);
                break;
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    title1.setBackgroundColor(Color.BLUE);
                    title2.setBackgroundColor(Color.WHITE);
                    title3.setBackgroundColor(Color.WHITE);
                    break;
                case 1:
                    title1.setBackgroundColor(Color.WHITE);
                    title2.setBackgroundColor(Color.BLUE);
                    title3.setBackgroundColor(Color.WHITE);
                    break;
                case 2:
                    title1.setBackgroundColor(Color.WHITE);
                    title2.setBackgroundColor(Color.WHITE);
                    title3.setBackgroundColor(Color.BLUE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
