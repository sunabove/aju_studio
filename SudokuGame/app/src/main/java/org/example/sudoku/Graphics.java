package org.example.sudoku;

import java.io.*;
import java.text.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.graphics.*;
import android.graphics.Path.*;

import android.database.*;
import android.database.sqlite.*;

public class Graphics extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_graphics);

        setContentView(new GraphicsView(this));
    }


    static public class GraphicsView extends View {

        private static final String QUOTE = "Now is the time for all " +
                "good men to come to the aid of their country." ;

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

            Paint cPaint = new Paint();
            cPaint.setColor(Color.LTGRAY);

            int radius = width < height ? width/2 : height/2;
            int cx = width/2 ;
            int cy = height/2;

            Path circle ;
            circle = new Path();
            circle.addCircle( cx, cy, radius, Direction.CW);

            canvas.drawPath(circle, cPaint);

            Paint tPaint = new Paint();
            tPaint.setColor( color );
            // text size
            tPaint.setTextSize( 30 );

            canvas.drawTextOnPath(QUOTE, circle, 0, 20, tPaint);

        }
    }
}
