package com.example.eastsun.mvpsample.model;

/**
 * Created by eastsun on 18-3-7.
 * 该模型接口用于存储和获取数据
 */

public interface IWeatherModel {
    //获取数据
    public String getInfo();
    //存储数据
    public void setInfo(String info);
}
