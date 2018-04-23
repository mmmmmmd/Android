package com.example.eastsun.mvpsample.view;

/**
 * Created by eastsun on 18-3-7.
 * 用于View显示的接口
 * 具体在Activity中实现
 */

public interface IWeatherView {
    //实现天气更新的接口
    public void onInfoUpgrade(String info);
    //实现刷新等待的接口
    public void showWaitingDialog();
    //实现等待框取消的接口
    public void dissmissWaitingDialog();
}
