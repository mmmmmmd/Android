package com.example.eastsun.mvpsample.view;

import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eastsun.mvpsample.R;
import com.example.eastsun.mvpsample.presenter.WeatherPresenter;

public class MainActivity extends AppCompatActivity implements IWeatherView{

    /*从视图的角度出发，既然View想更新数据又不能直接接触到Model
     * 那么就应该通过presenter去获取数据
     */
    WeatherPresenter mPresenter;

    //控件
    private TextView weatherInfo;
    private Button request;
    private AlertDialog mDialog;

    //刷新控件的Handler
    Handler mHandler = new Handler(Looper.getMainLooper());

    //初始化控件
    private void init(){
        weatherInfo = findViewById(R.id.weather_info);
        request = findViewById(R.id.request);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.requestWeatherInfo();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mPresenter = new WeatherPresenter(this);
    }

    //实现刷新视图的接口
    @Override
    public void onInfoUpgrade(final String info) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                weatherInfo.setText(info);
            }
        });
    }

    @Override
    public void showWaitingDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mDialog!=null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
                mDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("正在请求数据...请稍等..")
                        .show();

            }
        });
    }

    @Override
    public void dissmissWaitingDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mDialog!=null && mDialog.isShowing()){
                    mDialog.dismiss();
                }
            }
        });
    }
}
