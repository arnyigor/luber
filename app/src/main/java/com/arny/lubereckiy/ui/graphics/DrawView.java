package com.arny.lubereckiy.ui.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawView extends View{
    private ArrayList<Rect> rectangles = new ArrayList<>();
    Paint paint = new Paint();

    public DrawView(Context context, ArrayList<Rect> rectangles) {
        super(context);
        this.rectangles = rectangles;
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLUE);
        for (Rect rect : rectangles) {
            canvas.drawRect(rect, paint);
        }

    }

}