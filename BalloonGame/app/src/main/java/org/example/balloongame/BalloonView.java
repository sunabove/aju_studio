package org.example.balloongame;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import android.graphics.*;

/**
 * Created by sunabove on 2015-07-29.
 */
public class BalloonView extends View {

    private static final String GAME_NAME = "BALLOON GAME" ;
    int paintCnt ;
    boolean animatingNow ;
    Path animationCircle ;

    public BalloonView(Context context) {
        super(context);
        this.animatingNow = false ;
        this.animationCircle = null ;
    }

    public BalloonView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BalloonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        this.paintCnt ++ ;

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
        cPaint.setAntiAlias( true );
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
        rPaint.setColor(Color.BLUE);
        rPaint.setStrokeWidth( 4 ); // stroke line width pixel

        canvas.drawRect( rect, rPaint );

        // draw triangle
        Path triAngle = new Path();
        triAngle.moveTo(30, 60);
        triAngle.lineTo( 60, 0  );
        triAngle.lineTo(0, 0);
        triAngle.close();

        Paint triPaint = new Paint();
        triPaint.setStyle(Paint.Style.FILL);
        triPaint.setColor(Color.MAGENTA);

        canvas.drawPath(triAngle, triPaint);

        triPaint.setStyle(Paint.Style.STROKE);
        triPaint.setColor(Color.BLUE);
        triPaint.setStrokeWidth(2);

        canvas.drawPath(triAngle, triPaint);

        // draw animation circle
        if( this.animatingNow  ) {
            this.animationCircle = new Path();
            Path animationCircle = this.animationCircle;
            int paintCnt = this.paintCnt;

            int stepCount = 30;
            int aniRadius = width < height ? width/10 : height/10 ;
            int aniX = width*paintCnt/stepCount;
            int aniY = height/2 ;

            if( aniX > width + aniRadius ) {
                this.animatingNow = false ;
            }

            animationCircle.addCircle( aniX, aniY, aniRadius, Path.Direction.CW);

            Paint aniPaint = new Paint();
            aniPaint.setStyle(Paint.Style.FILL);
            aniPaint.setColor(Color.YELLOW);

            canvas.drawPath(animationCircle, aniPaint);

            aniPaint.setStyle(Paint.Style.STROKE);
            aniPaint.setColor(Color.BLUE);
            aniPaint.setStrokeWidth( 2 );

            canvas.drawPath(animationCircle, aniPaint);
        }

        // draw text
        Paint tPaint = new Paint();
        tPaint.setColor(color);
        // text size
        tPaint.setTextSize( 30 );

        // text bound
        String text = "%s (%d)";
        text = String.format( text, GAME_NAME, paintCnt );
        Rect bounds = new Rect();
        tPaint.getTextBounds(text, 0, text.length(), bounds);

        canvas.drawText( text, width/2 - bounds.width()/2, height/2 + bounds.height()/2 , tPaint );

       // canvas.drawTextOnPath(QUOTE, circle, 0, 20, tPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        float clickX = event.getX();
        float clickY = event.getY();

        String msg = "onTouchEvent: x = %f , y = %f";
        msg = String.format(msg, clickX, clickY);


        // show simple mssage
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();

        // show logcat message
        String TAG = this.getClass().getSimpleName();
        Log.d(TAG, msg);
        return true;
    }
}
