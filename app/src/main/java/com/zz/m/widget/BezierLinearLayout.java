package com.zz.m.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

public class BezierLinearLayout extends LinearLayout{
    private  PointF mTouchStartPoint;
    private  PointF mControlPoint;
    private  Path mPath;
    private  Paint mPaint;
    private float mWidth;
    private float mHeight;
    private ValueAnimator valueAnimator;


    public BezierLinearLayout(Context context) {
        this(context,null);
    }

    public BezierLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public BezierLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint=new Paint();
        mPaint.setColor(Color.LTGRAY);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPath=new Path();
        mControlPoint=new PointF();
        mTouchStartPoint = new PointF();
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        mControlPoint.set(mWidth/2,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(0,0);
        mPath.quadTo(mControlPoint.x,mControlPoint.y,mWidth,0);
        mPath.lineTo(0,0);
        canvas.drawPath(mPath,mPaint);
        canvas.drawCircle(mControlPoint.x,mControlPoint.y,10,mPaint);
        super.onDraw(canvas);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopAnim();
                mTouchStartPoint.set(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float changedX=event.getX()-mTouchStartPoint.x;
                float changedY=event.getY()-mTouchStartPoint.y;
                if(changedY>mHeight/6){
                    changedY=mHeight/6;
                }
                mControlPoint.set(mWidth/2+changedX,0+changedY);
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                startAnim();
                break;
        }
        invalidate();
        return true;
    }

    private void startAnim() {
        valueAnimator = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                PointF startPoint = (PointF) startValue;
                PointF endPoint = (PointF) endValue;
                float x = startPoint.x + fraction * (endPoint.x - startPoint.x);
                float y = startPoint.y + fraction * (endPoint.y - startPoint.y);
                PointF point = new PointF(x, y);
                return point;
            }
        }, mControlPoint,new PointF(mWidth/2,0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point=(PointF) animation.getAnimatedValue();
                Log.d("PointAnimate",point.x+","+point.y);
                mControlPoint.set(point.x,point.y);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private void stopAnim(){
        if(valueAnimator!=null&&valueAnimator.isRunning()){
            mControlPoint.set(mWidth/2,0);
            valueAnimator.cancel();
            invalidate();
        }
    }

}
