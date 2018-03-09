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


public class BezierWaveView extends View{
    private final Path mPath;
    private final Paint mPaint;
    private final PointF mLeft1;
    private final PointF mControlLeft1;
    private final PointF mLeft2;
    private final PointF mControlLeft2;
    private final PointF mFirst;
    private final PointF mControlFirst;
    private final PointF mSecond;
    private final PointF mControlSecond;
    private final PointF mRight;

    private final Path mPath1;
    private final Paint mPaint1;
    private final PointF mLeft11;
    private final PointF mControlLeft11;
    private final PointF mLeft21;
    private final PointF mControlLeft21;
    private final PointF mFirst1;
    private final PointF mControlFirst1;
    private final PointF mSecond1;
    private final PointF mControlSecond1;
    private final PointF mRight1;

    private float mHeight;
    private float mWidth;
    private float startX = 0;
    private float startX1 = 0;
    private int perHeight=50;

    private boolean isRunning=false;
    private float mUseHeightTop;
    private float mUseHeightBottom;

    public BezierWaveView(Context context) {
        this(context,null);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPath=new Path();
        mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#FF4876FF"));
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

        mPath1=new Path();
        mPaint1=new Paint();
        mPaint1.setColor(Color.parseColor("#994876FF"));
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.FILL);
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
        changeXY1();
        changeXY2();
    }

    private void changeXY1(){
        mLeft1.set(startX-mWidth,mUseHeightTop);
        mLeft2.set(startX-mWidth/2,mUseHeightTop);
        mFirst.set(startX,mUseHeightTop);
        mSecond.set(startX+mWidth/2,mUseHeightTop);
        mRight.set(startX+mWidth,mUseHeightTop);
        mControlLeft1.set(startX-mWidth*3/4,mUseHeightTop-perHeight);
        mControlLeft2.set(startX-mWidth/4,mUseHeightTop+perHeight);
        mControlFirst.set(startX+mWidth/4,mUseHeightTop-perHeight);
        mControlSecond.set(startX+mWidth*3/4,mUseHeightTop+perHeight);
    }

    private void changeXY2(){
        mLeft11.set(startX1-mWidth,mUseHeightTop);
        mLeft21.set(startX1-mWidth/2,mUseHeightTop);
        mFirst1.set(startX1,mUseHeightTop);
        mSecond1.set(startX1+mWidth/2,mUseHeightTop);
        mRight1.set(startX1+mWidth,mUseHeightTop);
        mControlLeft11.set(startX1-mWidth*3/4,mUseHeightTop-perHeight);
        mControlLeft21.set(startX1-mWidth/4,mUseHeightTop+perHeight);
        mControlFirst1.set(startX1+mWidth/4,mUseHeightTop-perHeight);
        mControlSecond1.set(startX1+mWidth*3/4,mUseHeightTop+perHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath1.reset();
        mPath1.moveTo(mLeft11.x, mLeft11.y);
        mPath1.quadTo(mControlLeft11.x, mControlLeft11.y, mLeft21.x, mLeft21.y);
        mPath1.quadTo(mControlLeft21.x, mControlLeft21.y, mFirst1.x, mFirst1.y);
        mPath1.quadTo(mControlFirst1.x, mControlFirst1.y, mSecond1.x, mSecond1.y);
        mPath1.quadTo(mControlSecond1.x, mControlSecond1.y, mRight1.x, mRight1.y);
        mPath1.lineTo(mWidth,mHeight);
        mPath1.lineTo(0,mHeight);
        mPath1.lineTo(mLeft1.x, mLeft1.y);
        canvas.drawPath(mPath1, mPaint1);

        mPath.reset();
        mPath.moveTo(mLeft1.x, mLeft1.y);
        mPath.quadTo(mControlLeft1.x, mControlLeft1.y, mLeft2.x, mLeft2.y);
        mPath.quadTo(mControlLeft2.x, mControlLeft2.y, mFirst.x, mFirst.y);
        mPath.quadTo(mControlFirst.x, mControlFirst.y, mSecond.x, mSecond.y);
        mPath.quadTo(mControlSecond.x, mControlSecond.y, mRight.x, mRight.y);
        mPath.lineTo(mWidth,mHeight);
        mPath.lineTo(0,mHeight);
        mPath.lineTo(mLeft1.x, mLeft1.y);
        canvas.drawPath(mPath, mPaint);
        if(!isRunning){
            handler.sendEmptyMessage(1);
        }
    }

    public void startRun(){
//        handler.sendEmptyMessage(1);
    }

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            startAnim1();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAnim2();
                }
            },500);
            return false;
        }
    });

    private void startAnim1() {
        isRunning=true;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mWidth);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startX=(float)animation.getAnimatedValue();
                startX1=-(float) animation.getAnimatedValue();
                Log.d("StartX_Bezier", "onAnimationUpdate: "+startX);
                changeXY1();
                invalidate();
            }
        });
        valueAnimator.start();
    }
    private void startAnim2() {
        isRunning=true;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mWidth);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startX1=(float) animation.getAnimatedValue();
                Log.d("StartX_Bezier", "onAnimationUpdate: "+startX);
                changeXY2();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
