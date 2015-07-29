package org.example.sudoku;

import java.io.*;
import java.text.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.AttributeSet;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.graphics.*;
import android.graphics.Path.*;

import android.database.*;
import android.database.sqlite.*;

/**
 * Created by sunabove on 2015-07-29.
 */
public class GraphicsView extends View {

    private static final String QUOTE = "SUDOKU GAME" ;

    public GraphicsView(Context context) {
        super(context);
    }

    public GraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public GraphicsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int color = Color.BLUE; // solid blue
        // Translucent purple
        color = Color.argb(127, 255, 0, 255);
        color = getResources().getColor(R.color.mycolor);

        // draw circle
        int radius = width < height ? width/2 : height/2;
        int cx = width/2 ;
        int cy = height/2;

        Path circle ;
        circle = new Path();
        circle.addCircle( cx, cy, radius, Path.Direction.CW);

        Paint cPaint = new Paint();
        // file circle
        cPaint.setColor(Color.LTGRAY);
        cPaint.setStyle(Paint.Style.FILL);

        canvas.drawPath(circle, cPaint);

        // draw circle line
        cPaint.setColor(Color.RED);
        cPaint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(circle, cPaint);

        // draw rect
        int left = width/2 - width/4;
        int top = height/2 - height/4;
        int right = width/2 + width/4;
        int bottom = height/2 + height/4;
        Rect rect = new Rect( left, top, right, bottom );

        Paint rPaint = new Paint();
        rPaint.setStyle( Paint.Style.STROKE );
        rPaint.setColor( Color.BLUE );
        rPaint.setStrokeWidth( 4 ); // stroke line width pixel

        canvas.drawRect( rect, rPaint );

        // draw text
        Paint tPaint = new Paint();
        tPaint.setColor(color);
        // text size
        tPaint.setTextSize( 30 );

        // text bound
        String text = QUOTE ;
        Rect bounds = new Rect();
        tPaint.getTextBounds(text, 0, text.length(), bounds);

        canvas.drawText( text, width/2 - bounds.width()/2, height/2 + bounds.height()/2 , tPaint );

       // canvas.drawTextOnPath(QUOTE, circle, 0, 20, tPaint);

    }
}
