package com.example.easts.toolbartest;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener{

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextItemAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<String> textViewList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBar = getSupportActionBar();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_call);

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        init();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refersh);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TextItemAdapter(textViewList);
        recyclerView.setAdapter(adapter);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<15;i++){
                    textViewList.add(new String("测试字符串"+i));
                }
            }
        },2000);
        swipeRefreshLayout.setRefreshing(false);
    }

    public void init(){
        for(int i=0;i<15;i++){
            textViewList.add(new String("测试字符串"+i));
        }
    }
}
