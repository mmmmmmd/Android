package com.example.easts.drawball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by easts on 2017/10/23.
 */

public class DrawView extends View {

    public float currentX = 40;
    public float currentY = 50;

    Paint p = new Paint();

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    //为该组件添加触碰事件的处理方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //修改currentX，currentY的两个属性
        currentX = event.getX();
        currentY = event.getY();
        //通知当前组件重新绘制自己
        invalidate();
        //返回true表明处理方法已经处理完该事件
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(Color.BLUE);
        canvas.drawCircle(currentX,currentY,15,p);
    }
}
