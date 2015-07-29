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

    Button animateBtn ;
    GraphicsView graphicsView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        //setContentView(new GraphicsView(this));

        this.graphicsView = (GraphicsView) findViewById( R.id.myGraphicsView  );

        this.animateBtn = (Button) findViewById( R.id.graphics_anim_btn );

        this.animateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
            }
        });
    }

    public void animate() {
        final Handler handler = new Handler( );

        Runnable runnable = new Runnable() {
            int animCount ;
            @Override
            public void run() {
                if( animCount < 10  ) {
                    graphicsView.invalidate();

                    handler.postDelayed( this, 200 );

                    animCount ++ ;
                }
            }
        };

        handler.postDelayed( runnable, 100 );
    }

}
