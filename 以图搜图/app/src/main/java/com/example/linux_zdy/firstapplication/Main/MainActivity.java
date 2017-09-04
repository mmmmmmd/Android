package com.example.linux_zdy.firstapplication.Main;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.linux_zdy.firstapplication.R;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
  网络请求需要修改；
  具体修改方法包括下拉刷新时的网络请求和SnakeBar的网络请求；
  还有requestByOkhttp方法

  网络请求的响应结果暂时写死
  请求的域名暂时为百度（150行左右进行修改）

 */

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    //控件的声明
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;


    private int page=2;


    private ImageAdapter adapter;
    public List<ImageBean.ResultsBean> beanList = new ArrayList<ImageBean.ResultsBean>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //控件实例化
        coordinatorLayout    = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        recyclerView         = (RecyclerView) findViewById(R.id.recycler);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating);
        swipeRefreshLayout   = (SwipeRefreshLayout) findViewById(R.id.refresh);


        //设置SwipeRefreshLayout属性
        //设置下拉监听
        swipeRefreshLayout.setOnRefreshListener(this);
        //改变控件旋转的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorRefresh_start,R.color.colorRefresh_end);
        //设置刷新按钮的背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.barBackground);


        //设置FloatingBar属性
        floatingActionButton.setOnClickListener(this);

        //设置网格布局管理器
        gridLayoutManager = new GridLayoutManager(this,2);

        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);

        requestByOkhttp(page++);



    }


    //下拉刷新的逻辑
    @Override
    public void onRefresh() {


        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                beanList.clear();
                page = ++page;
                requestByOkhttp(page);
                swipeRefreshLayout.setRefreshing(false);  //设置进度条不显示
            }
        },2000);



    }

    //悬浮按钮的点击事件
    @Override
    public void onClick(View v) {
        Snackbar.make(coordinatorLayout,"置顶",Snackbar.LENGTH_SHORT)

                //底部对话框
                .setAction("刷新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    //请求数据
    public void requestByOkhttp(int page){


        //实例化一个客户端
        OkHttpClient Client = new OkHttpClient.Builder().build();
        //通过request来请求一个数据
        Request request = new Request.Builder()
                .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/16/"+page)
                .build();


        //通过客户端执行回调方法
        Client.newCall(request).enqueue(new Callback() {
            @Override
            //加载失败时执行的方法
            public void onFailure(Call call, IOException e) {

            }

            @Override
            //加载成功时执行的方法
            public void onResponse(Call call, Response response) throws IOException {


                //响应的结果
                final String responseData = response.body().string();


                //设置控件展示
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Gson gson = new Gson();

                        ImageBean bean = gson.fromJson(responseData,ImageBean.class);

                        //将数据放入集合中
                        beanList.addAll(bean.getResults());

                        //设置适配器,非空判断
                        if(adapter == null) {
                            adapter = new ImageAdapter(getApplicationContext(), beanList);
                            recyclerView.setAdapter(adapter);
                        }else{
                            //刷新数据
                            adapter.notifyDataSetChanged();
                        }


                        //点击图片跳转到响应界面
                        adapter.setItemOnClick(new ImageAdapter.RecyclerViewOnClick() {
                            @Override
                            public View onClick(View v, int position) {

                                //指定意图
                                Intent intent = new Intent(MainActivity.this,PhotoViewActivity.class);

                                //跳转
                                intent.putExtra("url",beanList.get(position).getUrl());
                                startActivity(intent);
                                return null;
                            }
                        });


                    }
                });


            }
        });

    }



}
