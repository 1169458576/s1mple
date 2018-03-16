package com.zz.m.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


import java.util.Date;

public class RulerView extends View implements GestureDetector.OnGestureListener{

    private final static String TAG="RulerView";

    private long downTime;

    //最小值
    private int minValue;
    //最大值
    private int maxValue;
    //每刻度 值
    private float spacingValue;
    //控件宽度
    private float mWidth;
    //控件高度
    private float mHeight;
    //刻度间隔距离
    private float perWidth;
    //短刻度高度
    private float shortHeight;
    //长刻度高度
    private float longHeight;
    //刻度线画笔
    private Paint linePaint;
    //中线画笔
    private Paint middlePaint;
    //刻度文字画笔
    private Paint mTextPaint;
    //开始横坐标
    private float startX;
    //路径
    private Path mPath;

    //当前值
    private int mCurrentValue;

    //值变化监听
    private OnValueChangedListener mOnValueChangedListener;
    private ValueAnimator valueAnimator;

    public void setOnValueChangedListener(OnValueChangedListener mOnValueChangedListener) {
        this.mOnValueChangedListener = mOnValueChangedListener;
    }

    /**
     * 获取当前刻度值
     * @return 当前刻度值
     */
    public int getCurrentValue() {
        return mCurrentValue;
    }

    GestureDetector mGestureDetector;
    private float totalWidth;

    public RulerView(Context context) {
        this(context,null);
    }
    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector=new GestureDetector(context,this);
        init();
    }


    private void init(){
        perWidth=20;
        minValue=0;
        maxValue=100;
        spacingValue=1;
        shortHeight =25;
        longHeight =50;
        totalWidth=(maxValue-minValue)/spacingValue*perWidth;
        linePaint=new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(3);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        middlePaint=new Paint();
        middlePaint.setColor(Color.RED);
        middlePaint.setStrokeWidth(3);
        middlePaint.setAntiAlias(true);
        middlePaint.setStyle(Paint.Style.STROKE);
        mTextPaint=new TextPaint();
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mPath=new Path();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * 延迟滑动
     */
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(final Message msg) {
            scrollTo((int)msg.obj);
            return false;
        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                Message msg=new Message();
                msg.obj=getCurrentValue();
                handler.sendMessageDelayed(msg,200);
                long upTime = new Date().getTime();
                if(upTime -downTime<=500){
                    performClick();
                }
                break;
        }

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        startX=mWidth/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(startX,mHeight);
        mPath.lineTo(startX+totalWidth,mHeight);
        for(float i=minValue;i<=maxValue;i=i+spacingValue){
            mPath.moveTo(startX+(i-minValue)/spacingValue*perWidth,mHeight);
            if(i%(spacingValue*10)==0){
                mPath.lineTo(startX+(i-minValue)/spacingValue*perWidth,mHeight- longHeight);
                canvas.drawText(String.valueOf((int)i),startX+(i-minValue)/spacingValue*perWidth,
                        mHeight- longHeight -40,mTextPaint);
            }else{
                mPath.lineTo(startX+(i-minValue)/spacingValue*perWidth,mHeight- shortHeight);
            }
        }
        canvas.drawPath(mPath,linePaint);
        canvas.drawLine(mWidth/2,mHeight,mWidth/2,mHeight-75,middlePaint);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        downTime=new Date().getTime();
        return true;
    }



    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(valueAnimator!=null){
            if(valueAnimator.isRunning()){
                valueAnimator.cancel();
                valueAnimator=null;
            }
        }
        startX=startX-distanceX;
        if(startX>=mWidth/2){
            startX=mWidth/2;
        }
        if(startX<=mWidth/2-totalWidth){
            startX=mWidth/2-totalWidth;
        }
        float floatEnd=(minValue+(mWidth/2-startX)/perWidth);
        int intEnd=(int)floatEnd;
        if(floatEnd-intEnd>=0.5f){
            mCurrentValue=intEnd+1;
        }else{
            mCurrentValue=intEnd;
        }
        if(mOnValueChangedListener!=null){
            mOnValueChangedListener.onValueChanged(mCurrentValue);
        }
        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    private void scrollTo(int value){
        float x=startX+value*perWidth;
        valueAnimator=null;
        valueAnimator = ValueAnimator.ofFloat(startX, startX-(x-mWidth/2));
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startX=(float)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public interface OnValueChangedListener{
        void onValueChanged(int value);
    }

}
