package org.example.balloongame;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class BalloonGameActivity extends Activity {

    Button animateBtn ;
    BalloonView balloonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballongame);

        //setContentView(new GraphicsView(this));

        this.balloonView = (BalloonView) findViewById( R.id.myGraphicsView  );

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

        final BalloonView balloonView = this.balloonView;
        balloonView.paintCnt = 0 ;
        balloonView.animatingNow = true ;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if( balloonView.animatingNow ) {
                    balloonView.invalidate();

                    handler.postDelayed( this, 200 );
                }
            }
        };

        handler.postDelayed( runnable, 100 );
    }

}
