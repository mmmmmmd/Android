package com.example.eastsun.mvpsample.presenter;

import com.example.eastsun.mvpsample.model.IWeatherImpl;
import com.example.eastsun.mvpsample.model.IWeatherModel;
import com.example.eastsun.mvpsample.view.IWeatherView;


/**
 * Created by eastsun on 18-3-7.
 * presenter用于链接model和view之间的数据传输
 */

public class WeatherPresenter {
    //既然用于数据传输那么必须得保留两者的调用
    IWeatherView mView;
    IWeatherModel mModel;

    //因为Presenter持有View的引用，所以在这里要将View.interface注入到Presenter当中。
    public WeatherPresenter(IWeatherView mView){
        this.mView = mView;
        mModel = new IWeatherImpl();
    }

    //供View层调用来请求天气的数据
    public void requestWeatherInfo(){
        getNetworkInfo();
    }

    //presenter操作View是通过View.interface,也就是View层定义的接口
    private void showWaitingDialog(){
        mView.showWaitingDialog();
    }
    private void dissmissWaitingDialog(){
        mView.dissmissWaitingDialog();
    }
    private void onUpdateWeatherInfo(String info){
        mView.onInfoUpgrade(info);
    }

    //presenter获取到了数据交给Model处理
    private void saveInfo(String info){
        mModel.setInfo(info);
    }
    //处理完数据后Presenter可以通过Model对象获取本地数据
    private String localInfo(){
        return mModel.getInfo();
    }

    //模拟从服务器上获取数据
    private void getNetworkInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //打开等待对话框
                    showWaitingDialog();
                    //模拟网络延迟
                    Thread.sleep(6000);

                    String info = "20度，小雨转晴";
                    //保存数据到Model层
                    saveInfo(info);
                    //从Model层获取数据
                    String localInfo = localInfo();

                    //通知View层改变视图
                    onUpdateWeatherInfo(info);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    dissmissWaitingDialog();
                }
            }
        }).start();
    }

}
