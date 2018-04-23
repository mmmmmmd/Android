package com.example.eastsun.animationtest;

import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

/*
   差值器设置的是动画改变的模式（快慢等）
   估值器设置的是动画改变时具体参数的数值
 */

public class MainActivity extends AppCompatActivity {

    private Button button;
    private final String TAG="MainActivity.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                //渐变改变按钮颜色
                ObjectAnimator colorAnim=ObjectAnimator.ofInt(button,"backgroundColor",0xFFFF8080,0xFF8080FF);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.setInterpolator(new BounceInterpolator());
                colorAnim.start();
                */


                //改变按钮的大小
                ObjectAnimator objectAnimator=ObjectAnimator.ofInt(new MyView(button),"width",button.getLayoutParams().width,500);
                objectAnimator.setDuration(5000);
                objectAnimator.start();

                /*
                ValueAnimator valueAnimator=ValueAnimator.ofInt(button.getLayoutParams().width,1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    private IntEvaluator mEvaluator=new IntEvaluator();
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue=(Integer) animation.getAnimatedValue();
                        button.getLayoutParams().width=currentValue;
                        button.requestLayout();
                    }
                });
                valueAnimator.setDuration(5000).setInterpolator(new DecelerateInterpolator());
                valueAnimator.start();
                */
            }
        });

    }

    private static class MyView{

        private Button targetView;

        public MyView(Button targetView){
            this.targetView=targetView;
        }

        public int getWidth(){
            return targetView.getLayoutParams().width;
        }

        public void setWidth(int width){
            targetView.getLayoutParams().width=width;
            targetView.requestLayout();
        }
    }
}