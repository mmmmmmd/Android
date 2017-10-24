package com.example.easts.radiobutton;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

/*
   RadioButton是一个适合用作单项选择的控件，通过实现RadioGroup.OnCheckedChangeListener接口
   的OnCheckedChange接口来实现监听RadioGroup中radioButton选定状态的改变，接口中第一个参数
   是监听的RadioGroup实例，第二个是选定的radioButton的ID。
 */

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    TextView text;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        radioGroup = (RadioGroup) findViewById(R.id.sex);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            case R.id.male:
                text.setText("您是男性");
                break;
            case R.id.fmale:
                text.setText("您是女性");
                break;
            default:
                break;
        }
    }
}
