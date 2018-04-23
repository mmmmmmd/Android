package com.example.eastsun.animationtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by eastsun on 18-4-18.
 */

public class MyView extends View {

    public static final float RADIUS=70f;
    private Point currentPoint;
    private Paint mPaint;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //构造器中传入第一个参数是为了让画笔绘制的时候抗锯齿
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(currentPoint==null){
            currentPoint=new Point(RADIUS,RADIUS);

            float x=currentPoint.getX();
            float y=currentPoint.getY();

            canvas.drawCircle(x,y,RADIUS,mPaint);

            Point startPoint=new Point(RADIUS,RADIUS);
            Point endPoint=new Point(700,1000);

            ValueAnimator valueAnimator=ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
            valueAnimator.setDuration(5000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint= (Point) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();

        }else{
            float x=currentPoint.getX();
            float y=currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
        }
    }
}
