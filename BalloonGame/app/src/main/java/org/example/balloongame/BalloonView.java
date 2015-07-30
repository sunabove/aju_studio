package org.example.balloongame;

import java.util.*;

import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import android.graphics.*;

/**
 * Created by sunabove on 2015-07-29.
 */
public class BalloonView extends View {

    private static final String GAME_NAME = "BALLOON GAME" ;
    private static final String TAG = BalloonView.class.getSimpleName();
    int paintCnt ;
    long timeMiliPerFrame;

    boolean paintingNow ;
    boolean palyingGameNow;

    long gameStartTime ;
    int score ;
    int maxScore ;
    ArrayList<Balloon> balloons ;

    public BalloonView(Context context) {
        super(context);
        this.initBalloonView();
    }

    public BalloonView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.initBalloonView();
    }

    public BalloonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initBalloonView();
    }

    private void initBalloonView() {
        this.timeMiliPerFrame = 200 ;
        this.palyingGameNow = false ;
        this.paintingNow = false ;
        this.balloons = new ArrayList<Balloon>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if( this.paintingNow ) {
            return ;
        } else {
            this.paintingNow = true ;

            try {
                this.onDrawImpl( canvas );
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            this.paintingNow = false ;
        }
    }

    private void onDrawImpl( Canvas canvas ) {

        this.paintCnt ++ ;

        ArrayList<Balloon> balloons = this.balloons ;
        int paintCnt = this.paintCnt ;
        long timeMiliPerFrame = this.timeMiliPerFrame;

        Context context = this.getContext();

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // draw canvas boundary
        if( true ) {
            int left = 1;
            int top = 0;
            int right = width;
            int bottom = height -1 ;
            Rect rect = new Rect( left, top, right, bottom );

            Paint paint = new Paint();
            paint.setColor( Color.BLACK );
            paint.setStyle( Paint.Style.STROKE );
            paint.setStrokeWidth(1);

            canvas.drawRect(rect, paint);
        }

        // draw back gound
        if( true ) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ballon_view_background);

            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();

            Paint paint = new Paint();
            paint.setAntiAlias(false);
            paint.setFilterBitmap(false);

            for( int by = 0 ; by < height ; by += bh ) {
                for( int bx = 1; bx < width ; bx += bw ){
                    canvas.drawBitmap(bitmap, bx, by, paint);
                }
            }
        }

        if( palyingGameNow ) {
            // clear balloons useless
            ArrayList<Balloon> clearList = new ArrayList<Balloon>();

            for( Balloon balloon : balloons ) {
                if( balloon.isClicked ) {
                    this.score ++ ;
                }

                if( balloon.isClicked || ( balloon.y <= - balloon.radius ) ) {
                    clearList.add( balloon );
                }
            }

            for( Balloon balloon : clearList ) {
                balloons.remove( balloon );
            }

            // generate ballons per three frames
            if (paintCnt % 3 == 0) {
                Balloon newBallon = Balloon.createBalloon(width, height, timeMiliPerFrame);
                balloons.add(newBallon);
            }
        }

        // draw ballons
        if( true ) {
            long currTimeMili = System.currentTimeMillis();

            Paint fillPaint = new Paint();
            Paint linePaint = new Paint();

            fillPaint.setStyle(Paint.Style.FILL);
            linePaint.setStyle(Paint.Style.STROKE);

            int index = 0 ;
            for( Balloon balloon : balloons ) {

                Log.d( TAG,  "[ " + index + " ] = " + balloon.toString() );

                fillPaint.setColor( balloon.fillColor );
                linePaint.setColor( balloon.lineColor );
                Path [] shapes = balloon.getShape( currTimeMili );
                for( Path shape : shapes ) {
                    canvas.drawPath( shape, fillPaint );
                    canvas.drawPath( shape, linePaint );
                }

                index ++ ;
            }
        }

        // draw game information as a text
        if( true ) {
            int color = Color.BLUE;
            // draw text
            Paint paint = new Paint();
            paint.setColor(color);
            // text size
            paint.setTextSize(30);

            // text info
            int balloonsCount = balloons.size() ;
            String text = "%s (%d) (%d)";
            text = String.format( text, this.GAME_NAME, this.paintCnt, balloonsCount );

            //text bounds
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);

            canvas.drawText(text, width / 2 - bounds.width() / 2, height / 2 + bounds.height() / 2, paint);
        }

    }



    public void playNewGame() {
        final Handler handler = new Handler( );

        final BalloonView balloonView = this ;
        balloonView.paintCnt = 0 ;
        balloonView.palyingGameNow = true ;
        balloonView.gameStartTime = System.currentTimeMillis();

        balloonView.balloons.clear();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if( balloonView.palyingGameNow ) {
                    balloonView.invalidate();

                    handler.postDelayed( this, timeMiliPerFrame);
                }
            }
        };

        handler.postDelayed(runnable, 0 );
    }

    public void stopGame() {
        final BalloonView balloonView = this ;

        balloonView.palyingGameNow = false ;

        final Handler hander = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if( balloonView.paintingNow ) {
                    hander.postDelayed( this, timeMiliPerFrame);
                } else {
                    //balloonView.balloons.clear();
                }
            }
        };

        hander.postDelayed(runnable, 0 );
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
