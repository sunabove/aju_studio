package org.example.sudoku;

import android.support.v7.app.ActionBarActivity;
import java.io.*;
import java.text.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.graphics.*;
import android.graphics.Path.*;

import android.database.*;
import android.database.sqlite.*;

public class GraphicsActivity extends Activity {

    final String TAG = GraphicsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean useLayoutFile = true ;
        if( useLayoutFile ) {
            setContentView(R.layout.activity_graphics);

            MyGraphicsView myGraphicsView = (MyGraphicsView) findViewById( R.id.graphics_my_graphics );
            myGraphicsView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startGame( 2 );
                }
            });
        } else if( ! useLayoutFile ) {
            setContentView(new GraphicsView(this));
        }
    }

    private void startGame(int i) {
        Log.d(TAG, "clicked on " + i);
        Intent intent = new Intent(GraphicsActivity.this, GameActivity.class);
        intent.putExtra(GameActivity.KEY_DIFFICULTY, i);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = this.getApplicationContext();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        String tag = this.getClass().getPackage().getName();

        String msg = "Display width = %d, height = %d" ;
        msg = String.format( Locale.KOREA, msg, width, height );

        // 초 간단 메시자 창 출력
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();

        // Logcat 으로 메시지 출력
        Log.d( tag, msg );
    }

    static public class GraphicsView extends View {
        public GraphicsView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int color = Color.BLUE; // solid blue

            // Translucent purple
            color = Color.argb(127, 255, 0, 255);

            color = getResources().getColor(R.color.mycolor);

            // Drawing commands go here
            Path circle = new Path();
            circle.addCircle(150, 150, 100, Direction.CW);

            String QUOTE = "Now is the time for all " +
                    "good men to come to the aid of their country." ;

            Paint cPaint = new Paint();
            cPaint.setColor(Color.LTGRAY);
            cPaint.setStyle(Paint.Style.STROKE);
            cPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(circle, cPaint);

            Paint tPaint = new Paint();
            tPaint.setColor(color);
            final float testTextSize = 48f;

            String text = QUOTE ;
            // Get the bounds of the text, using our testTextSize.
            tPaint.setTextSize(testTextSize);
            Rect bounds = new Rect();
            tPaint.getTextBounds(text, 0, text.length(), bounds);

            int desiredWidth = width/2;

            // Calculate the desired size as a proportion of our testTextSize.
            float desiredTextSize = testTextSize * desiredWidth / bounds.width();

            // Set the paint for that size.
            tPaint.setTextSize(desiredTextSize);
            canvas.drawText( text, 10, bounds.height(), tPaint );

            canvas.drawTextOnPath(QUOTE, circle, 0, 20, tPaint);
        }
    }
}
