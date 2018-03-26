package com.zz.m.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class BezierBallsView extends View implements GestureDetector.OnGestureListener{

    private  Paint mPointPaint;
    private  Path mPath;
    private  Paint bezierPaint;
    private float bigBallWidth;

    private float smallBallWidth;

    private float mWidth;

    private float mHeight;

    private Paint bigBallPaint;

    private Paint smallBallPaint;

    private GestureDetector mGestureDetector;

    private PointF bigBallPoint;

    private PointF smallBallPoint;

    //控制点
    private PointF controlPoint;

    //大球点1
    private PointF b1Point;

    //大球点2
    private PointF b2Point;

    //小球点1
    private PointF s1Point;

    //小球点2
    private PointF s2Point;
    private double lengthOO;


    public BezierBallsView(Context context) {
        this(context,null);
    }

    public BezierBallsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierBallsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bigBallPaint=new Paint();
        bigBallPaint.setAntiAlias(true);
        bigBallPaint.setStyle(Paint.Style.FILL);
        bigBallPaint.setColor(Color.RED);
        smallBallPaint=new Paint();
        smallBallPaint.setAntiAlias(true);
        smallBallPaint.setStyle(Paint.Style.FILL);
        smallBallPaint.setColor(Color.RED);
        bezierPaint=new Paint();
        bezierPaint.setColor(Color.RED);
        bezierPaint.setStyle(Paint.Style.FILL);
        bezierPaint.setAntiAlias(true);
        mGestureDetector=new GestureDetector(context,this);
        bigBallPoint=new PointF();
        smallBallPoint=new PointF();
        controlPoint=new PointF();
        b1Point=new PointF();
        b2Point=new PointF();
        s1Point=new PointF();
        s2Point=new PointF();
        mPath=new Path();
        mPointPaint=new Paint();
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        if(bigBallWidth==0){
            smallBallWidth=bigBallWidth=50;
            bigBallPoint.set(mWidth/2,mHeight/2);
            smallBallPoint.set(mWidth/2,mHeight/2);
            controlPoint.set(mWidth/2,mHeight/2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        computePoint();
        canvas.drawCircle(bigBallPoint.x,bigBallPoint.y,bigBallWidth,bigBallPaint);
        canvas.drawCircle(smallBallPoint.x,smallBallPoint.y,smallBallWidth,smallBallPaint);
        mPath.reset();
        mPath.moveTo(b1Point.x,b1Point.y);
        mPath.quadTo(controlPoint.x,controlPoint.y,s1Point.x,s1Point.y);
        mPath.lineTo(s2Point.x,s2Point.y);
        mPath.quadTo(controlPoint.x,controlPoint.y,b2Point.x,b2Point.y);
        mPath.lineTo(b1Point.x,b1Point.y);
        canvas.drawPath(mPath,bezierPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) ;
    }

    @Override
    public boolean onDown(MotionEvent e) {
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
        float x=bigBallPoint.x-distanceX;
        float y=bigBallPoint.y-distanceY;
        lengthOO=Math.hypot(x-smallBallPoint.x,y-smallBallPoint.y);
        if(lengthOO<=200){
            smallBallWidth=(240-(float)lengthOO)/4;
            bigBallPoint.x=x;
            bigBallPoint.y=y;
            invalidate();
        }
        return true;
    }

    private void computePoint(){
        lengthOO=Math.hypot(bigBallPoint.x-smallBallPoint.x,bigBallPoint.y-smallBallPoint.y);
        controlPoint.set((bigBallPoint.x+smallBallPoint.x)/2,(bigBallPoint.y+smallBallPoint.y)/2);
        double cos=(bigBallPoint.x-smallBallPoint.x)/lengthOO;
        double sin=(bigBallPoint.y-smallBallPoint.y)/lengthOO;
        b1Point.set((float)( bigBallPoint.x-bigBallWidth*sin),
                (float) (bigBallPoint.y+bigBallWidth*cos));
        b2Point.set((float)( bigBallPoint.x+bigBallWidth*sin),
                (float) (bigBallPoint.y-bigBallWidth*cos));
        s1Point.set((float)( smallBallPoint.x-smallBallWidth*sin),
                (float) (smallBallPoint.y+smallBallWidth*cos));
        s2Point.set((float)( smallBallPoint.x+smallBallWidth*sin),
                (float) (smallBallPoint.y-smallBallWidth*cos));

    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }
}
