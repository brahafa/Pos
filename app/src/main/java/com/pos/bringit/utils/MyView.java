package com.pos.bringit.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;


public class MyView extends View {
    Paint mTextPaint, otherPaint;
    RectF mRectF;
    Context context;
    int x, y;


    public MyView(Context context, int x, int y) {
        super(context);

        mTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(pxFromDp(context, 24));

        otherPaint = new Paint();
        otherPaint.setStrokeWidth(2);

        this.context = context;
        this.x = x;
        this.y = y;

    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius = 40;
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setTextSize(pxFromDp(context, 12));
        canvas.drawText("4 פנוי", x + (radius >> 1), y - 5, mTextPaint);
        otherPaint.setColor(Color.GREEN);
        otherPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x + radius, y + radius, radius, otherPaint);


        otherPaint.setColor(Color.GREEN);
        otherPaint.setStyle(Paint.Style.FILL);
        int x1 = (x + radius);
        int y1 = (y + radius);
        int recWidth = 20;
        mRectF = new RectF(x1 - recWidth, y1 - recWidth, x1 + recWidth, y1 + recWidth);

        canvas.drawRect(mRectF, otherPaint);

        mTextPaint.setTextSize(pxFromDp(context, 20));
        mTextPaint.setColor(Color.WHITE);
        canvas.drawText("+", x1 - (recWidth >> 1), y1 + (recWidth >> 1), mTextPaint);

    }


    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}