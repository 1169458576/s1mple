package com.zz.m.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BezierBallsView extends View{

    private float bigBallWidth;

    private float smallBallWidth;

    private float mWidth;

    private float mHeight;

    private Paint bigBallPaint;

    private Paint smallBallPaint;



    public BezierBallsView(Context context) {
        this(context,null);
    }

    public BezierBallsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierBallsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        if(bigBallWidth==0){
            smallBallWidth=bigBallWidth=mWidth>mHeight?mHeight:mWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
