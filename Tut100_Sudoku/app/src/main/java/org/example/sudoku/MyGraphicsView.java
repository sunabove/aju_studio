package org.example.sudoku;

import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.*;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by sunabove on 2015-07-29.
 */
public class MyGraphicsView extends View {

    public MyGraphicsView(Context context) {
        super(context);
    }

    public MyGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGraphicsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        setBackgroundResource(R.drawable.background);

        int color = Color.BLUE; // solid blue

        // Translucent purple
        color = Color.argb(127, 255, 0, 255);

        color = getResources().getColor(R.color.mycolor);

        // Drawing commands go here
        Path circle = new Path();
        circle.addCircle(150, 150, 100, Path.Direction.CW);

        String QUOTE = "안녕하세요?";

        Paint cPaint = new Paint();
        cPaint.setColor(Color.LTGRAY);
        cPaint.setStyle(Paint.Style.STROKE);
        cPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(circle, cPaint);

        Paint tPaint = new Paint();
        tPaint.setColor(color);
        final float testTextSize = 48f;

        String text = QUOTE;
        // Get the bounds of the text, using our testTextSize.
        tPaint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        tPaint.getTextBounds(text, 0, text.length(), bounds);

        int desiredWidth = width / 2;

        // Calculate the desired size as a proportion of our testTextSize.
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        // Set the paint for that size.
        tPaint.setTextSize(desiredTextSize);
        canvas.drawText(text, 10, bounds.bottom, tPaint);

        //canvas.drawTextOnPath(QUOTE, circle, 0, 20, tPaint);
    }

    protected void onDrawTextBounds(Canvas canvas) {
        final String s = "Hello. I'm some text!";

        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(60);

        p.getTextBounds(s, 0, s.length(), bounds);
        float mt = p.measureText(s);
        int bw = bounds.width();

        Log.i("LCG", String.format(
                        "measureText %f, getTextBounds %d (%s)",
                        mt,
                        bw, bounds.toShortString())
        );
        bounds.offset(0, -bounds.top);
        p.setStyle(Style.STROKE);
        canvas.drawColor(0xff000080);
        p.setColor(0xffff0000);
        canvas.drawRect(bounds, p);
        p.setColor(0xff00ff00);
        canvas.drawText(s, 0, bounds.bottom, p);
    }
}