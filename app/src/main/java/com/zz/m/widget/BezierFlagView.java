package com.zz.m.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class BezierFlagView extends View{
    private  Path mPath;
    private  Paint mPaint;
    private  PointF mLeft1;
    private  PointF mControlLeft1;
    private  PointF mLeft2;
    private  PointF mControlLeft2;
    private  PointF mFirst;
    private  PointF mControlFirst;
    private  PointF mSecond;
    private  PointF mControlSecond;
    private  PointF mRight;

    private  PointF mLeft11;
    private  PointF mControlLeft11;
    private  PointF mLeft21;
    private  PointF mControlLeft21;
    private  PointF mFirst1;
    private  PointF mControlFirst1;
    private  PointF mSecond1;
    private  PointF mControlSecond1;
    private  PointF mRight1;
    private float mHeight;
    private float mWidth;
    private float startX = 0;
    private int perHeight=50;

    private boolean isRunning=false;
    private float mUseHeightTop;
    private float mUseHeightBottom;

    public BezierFlagView(Context context) {
        this(context,null);
    }

    public BezierFlagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPath=new Path();
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mLeft1=new PointF();
        mControlLeft1=new PointF();
        mLeft2=new PointF();
        mControlLeft2=new PointF();
        mFirst=new PointF();
        mControlFirst=new PointF();
        mSecond=new PointF();
        mControlSecond=new PointF();
        mRight=new PointF();

        mLeft11=new PointF();
        mControlLeft11=new PointF();
        mLeft21=new PointF();
        mControlLeft21=new PointF();
        mFirst1=new PointF();
        mControlFirst1=new PointF();
        mSecond1=new PointF();
        mControlSecond1=new PointF();
        mRight1=new PointF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        mUseHeightTop=mHeight/8;
        mUseHeightBottom=mHeight*7/8;
        changeXY();
    }

    private void changeXY(){
        mLeft1.set(startX-mWidth,mUseHeightTop);
        mLeft2.set(startX-mWidth/2,mUseHeightTop);
        mFirst.set(startX,mUseHeightTop);
        mSecond.set(startX+mWidth/2,mUseHeightTop);
        mRight.set(startX+mWidth,mUseHeightTop);
        mControlLeft1.set(startX-mWidth*3/4,mUseHeightTop-perHeight);
        mControlLeft2.set(startX-mWidth/4,mUseHeightTop+perHeight);
        mControlFirst.set(startX+mWidth/4,mUseHeightTop-perHeight);
        mControlSecond.set(startX+mWidth*3/4,mUseHeightTop+perHeight);

        mLeft11.set(startX-mWidth,mUseHeightBottom);
        mLeft21.set(startX-mWidth/2,mUseHeightBottom);
        mFirst1.set(startX,mUseHeightBottom);
        mSecond1.set(startX+mWidth/2,mUseHeightBottom);
        mRight1.set(startX+mWidth,mUseHeightBottom);
        mControlLeft11.set(startX-mWidth*3/4,mUseHeightBottom-perHeight);
        mControlLeft21.set(startX-mWidth/4,mUseHeightBottom+perHeight);
        mControlFirst1.set(startX+mWidth/4,mUseHeightBottom-perHeight);
        mControlSecond1.set(startX+mWidth*3/4,mUseHeightBottom+perHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mLeft1.x, mLeft1.y);
        mPath.quadTo(mControlLeft1.x, mControlLeft1.y, mLeft2.x, mLeft2.y);
        mPath.quadTo(mControlLeft2.x, mControlLeft2.y, mFirst.x, mFirst.y);
        mPath.quadTo(mControlFirst.x, mControlFirst.y, mSecond.x, mSecond.y);
        mPath.quadTo(mControlSecond.x, mControlSecond.y, mRight.x, mRight.y);
        mPath.lineTo(mRight1.x, mRight1.y);
        mPath.quadTo(mControlSecond1.x, mControlSecond1.y, mSecond1.x, mSecond1.y);
        mPath.quadTo(mControlFirst1.x, mControlFirst1.y, mFirst1.x, mFirst1.y);
        mPath.quadTo(mControlLeft21.x, mControlLeft21.y, mLeft21.x, mLeft21.y);
        mPath.quadTo(mControlLeft11.x, mControlLeft11.y, mLeft11.x, mLeft11.y);
        mPath.lineTo(mLeft1.x, mLeft1.y);
        canvas.drawPath(mPath, mPaint);
        if(!isRunning){
            handler.sendEmptyMessage(1);
        }
    }

    public void startRun(){
//        handler.sendEmptyMessage(1);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startAnim();
        }
    };

    private void startAnim() {
        isRunning=true;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mWidth);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startX=(float)animation.getAnimatedValue();
                Log.d("StartX_Bezier", "onAnimationUpdate: "+startX);
                changeXY();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
