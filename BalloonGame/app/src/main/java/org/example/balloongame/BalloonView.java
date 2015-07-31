package org.example.balloongame;

import java.util.*;

import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;

import android.graphics.*;

/**
 * Created by sunabove on 2015-07-29.
 */
public class BalloonView extends View implements  CommonInterface {

    private static final String GAME_NAME = "BALLOON GAME";
    private static final String TAG = BalloonView.class.getSimpleName();
    int paintCnt;
    long timeMiliPerFrame;

    boolean paintingNow;
    boolean palyingGameNow;

    long gameStartTime;
    int score;
    int maxScore;

    BalloonList balloons;
    BalloonList stickList ;

    BalloonGameActivity balloonGameActivity;

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
        this.timeMiliPerFrame = 200;
        this.palyingGameNow = false;
        this.paintingNow = false;
        this.balloons = new BalloonList();
        this.stickList = new BalloonList();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.paintingNow) {
            return;
        } else {
            this.paintingNow = true;

            try {
                this.onDrawImpl(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.paintingNow = false;
        }
    }

    private void onDrawImpl(Canvas canvas) {

        this.paintCnt++;

        BalloonList balloons = this.balloons;
        BalloonList stickList = this.stickList;

        int paintCnt = this.paintCnt;
        long timeMiliPerFrame = this.timeMiliPerFrame;

        Context context = this.getContext();

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // draw canvas boundary
        if (true) {
            int left = 1;
            int top = 0;
            int right = width;
            int bottom = height - 1;
            Rect rect = new Rect(left, top, right, bottom);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);

            canvas.drawRect(rect, paint);
        }

        // draw back gound
        boolean drawBackground = true ;
        if ( drawBackground ) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ballon_view_background);

            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();

            Paint paint = new Paint();
            paint.setAntiAlias(false);
            paint.setFilterBitmap(false);

            for (int by = 0; by < height; by += bh) {
                for (int bx = 1; bx < width; bx += bw) {
                    canvas.drawBitmap(bitmap, bx, by, paint);
                }
            }
        }

        if (palyingGameNow) {
            // clear balloons clicked
            BalloonList clickedList = new BalloonList();

            for( Balloon balloon : balloons ) {
                if (balloon.isClicked) {
                    this.score++;
                    clickedList.add(balloon);
                }
            }

            this.maxScore = this.maxScore > this.score ? this.maxScore : this.score;

            if (this.balloonGameActivity != null) {
                this.balloonGameActivity.setGameScore(this.score, this.maxScore);
            }

            for (Balloon balloon : clickedList) {
                balloons.remove(balloon);
            }

            // build stick list
            BalloonList zeroList = new BalloonList();

            for (Balloon balloon : balloons) {
                if( balloon.y <= balloon.radius ) {
                    balloon.isSticked = true ;
                    balloon.fillColor = Color.LTGRAY ;
                    balloon.lineColor = Color.BLACK ;

                    zeroList.add( balloon );
                }
            }

            stickList.addAll( zeroList );

            for( int i = 0; i < balloons.size() ; ) {
                Balloon balloon = balloons.get( i );
                if( balloon.isSticked( stickList )) {
                    balloon.isSticked = true ;
                    balloon.fillColor = Color.LTGRAY ;
                    balloon.lineColor = Color.BLACK ;

                    stickList.add( balloon );
                    balloons.remove( i );
                } else {
                    i ++ ;
                }
            }
            // end of building stick list

            // check whether if this game ended
            float maxBalloonHeight = stickList.getMaxVerticalPosition();

            /*
            float maxBalloonHeight = 0 ;
            for( Balloon balloon : this ) {
                maxBalloonHeight = balloon.y > maxBalloonHeight ? balloon.y : maxBalloonHeight ;
             }
             */

            if( maxBalloonHeight > 0 ) {
                if( stickList.size() > 0 ) {
                    Balloon firstBalloon = stickList.get( 0 );
                    if( maxBalloonHeight >= height - firstBalloon.radius ) {
                        this.palyingGameNow = false ;

                        if( this.balloonGameActivity != null ) {
                            this.balloonGameActivity.setGameEnded();
                        }
                    }
                }
            }
            // end of checking whter if this game ended

            // generate ballons per three frames
            if ( this.palyingGameNow && paintCnt % 4 == 0) {
                Balloon newBallon = Balloon.createBalloon(width, height, timeMiliPerFrame);
                if( newBallon != null ) {
                    balloons.add(newBallon);
                }
            }

        }

        // draw ballons
        if (true) {
            this.paintBalloonList(stickList, canvas);
            this.paintBalloonList(balloons, canvas);
        }

        // draw game information as a text
        if (!palyingGameNow) {
            int color = Color.BLUE;
            // draw text
            Paint paint = new Paint();
            paint.setColor(color);
            // text size
            paint.setTextSize(30);

            // text info
            int balloonsCount = balloons.size();
            String text = "%s ver. 1.0";
            text = String.format(text, this.GAME_NAME);

            //text bounds
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);

            canvas.drawText(text, width / 2 - bounds.width() / 2, height / 2 + bounds.height() / 2, paint);
        }

        // draw game status
        if (true) {
            // text info
            int balloonsCount = balloons.size();
            String text = "frame count = %d, balloon count = %d";
            text = String.format(text, paintCnt, balloonsCount);

            // draw text
            Paint paint = new Paint();
            paint.setTextSize(20);


            paint.setColor(Color.RED);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            //text bounds
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);

            int bw = bounds.width();
            int bh = bounds.height();
            int mx = 20 , my = 10 ;

            paint.setColor(Color.WHITE);

            canvas.drawText(text, width - bw - mx, bh + my, paint);
        }

    }

    private void paintBalloonList( BalloonList balloons, Canvas canvas ){
        long currTimeMili = System.currentTimeMillis();

        Context context = this.getContext() ;

        Paint fillPaint = new Paint();
        Paint linePaint = new Paint();

        fillPaint.setStyle(Paint.Style.FILL);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias( true );

        int index = 0;
        for (Balloon balloon : balloons) {

            Log.d(TAG, "[ " + index + " ] = " + balloon.toString());

            fillPaint.setColor( balloon.fillColor );
            linePaint.setColor( balloon.lineColor );
            linePaint.setStrokeWidth( balloon.lineWidth );

            Path[] shapes = balloon.getShape( currTimeMili );
            for (Path shape : shapes) {
                if( shape != null ) {
                    canvas.drawPath(shape, fillPaint);
                    canvas.drawPath(shape, linePaint);
                }
            }

            // drawm game item bitmap
            GameItem gameItem = balloon.gameItem ;
            if( gameItem != GameItem.NONE ) {
                Bitmap bitmap = this.getItemBitmap(gameItem.drawableResourceId);

                int bw = bitmap.getWidth();
                int bh = bitmap.getHeight();

                Paint paint = new Paint();

                canvas.drawBitmap( bitmap, balloon.x - bw/2 , balloon.y - bh/2, paint );
            }

            index++;
        }
    }

    private Hashtable<Integer, Bitmap> bitmapTable = new Hashtable<Integer, Bitmap>();

    private Bitmap getItemBitmap( int resId ) {
        Bitmap bitmap = bitmapTable.get( resId );
        if( bitmap == null ) {
            bitmap = BitmapFactory.decodeResource( this.getContext().getResources(), resId );
            if( bitmap != null ) {
                bitmapTable.put( resId, bitmap );
            }
        }

        return bitmap ;
    }

    public void playNewGame(BalloonGameActivity balloonGameActivity) {
        // paly a new game

        this.balloonGameActivity = balloonGameActivity;

        final Handler handler = new Handler();

        final BalloonView balloonView = this;
        balloonView.paintCnt = 0;
        balloonView.palyingGameNow = true;
        balloonView.score = 0;
        balloonView.gameStartTime = System.currentTimeMillis();

        Balloon.PAUSE_TIME = 0 ;
        Balloon.PAUSE_CURR_TIME = 0 ;

        balloonView.balloons.clear();
        balloonView.stickList.clear();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (balloonView.palyingGameNow) {

                    // calculate game pause time
                    if( Balloon.PAUSE_TIME > 0 ) {
                        long now = System.currentTimeMillis() ;
                        long then = Balloon.PAUSE_CURR_TIME;

                        long diff = now - then ;

                        Balloon.PAUSE_TIME = Balloon.PAUSE_TIME - diff ;

                        Balloon.PAUSE_CURR_TIME = now ;
                    }

                    balloonView.invalidate();

                    handler.postDelayed(this, timeMiliPerFrame);
                }
            }
        };

        handler.postDelayed(runnable, 0);
    }

    public void stopGame() {
        // Stop current game

        final BalloonView balloonView = this;

        balloonView.palyingGameNow = false;

        final Handler hander = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (balloonView.paintingNow) {
                    hander.postDelayed(this, timeMiliPerFrame);
                } else {
                    // do nothing at current
                }
            }
        };

        hander.postDelayed(runnable, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        boolean playingGameNow = this.palyingGameNow;
        float clickX = event.getX();
        float clickY = event.getY();

        // set balloon as click if it includes click coordinat

        if (palyingGameNow) {
            boolean isClockItemBalloonClicked = false ;
            BalloonList balloons = this.balloons;
            for (Balloon balloon : balloons) {
                if (balloon.includes(clickX, clickY)) {
                    balloon.isClicked = true;

                    if( balloon.gameItem == GameItem.CLOCK ) {
                        isClockItemBalloonClicked = true;
                    }
                }
            }

            // stops the balloon move ment and creating balloons
            if( isClockItemBalloonClicked ) {
                Balloon.PAUSE_TIME = 3000 ;
                Balloon.PAUSE_CURR_TIME = System.currentTimeMillis() ;
            }
        }

        String msg = "onTouchEvent: x = %f , y = %f";

        msg = String.format(msg, clickX, clickY);
        // show logcat message
        String TAG = this.getClass().getSimpleName();
        Log.d(TAG, msg);
        return true;
    }

}
