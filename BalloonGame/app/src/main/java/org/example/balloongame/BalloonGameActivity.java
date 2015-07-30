package org.example.balloongame;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class BalloonGameActivity extends Activity {

    Button newGameBtn;
    Button stopGameBtn ;
    EditText scoreTv ;
    EditText maxScoreTv ;
    BalloonView balloonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballongame);

        //setContentView(new GraphicsView(this));

        this.balloonView = (BalloonView) findViewById( R.id.game_balloon_view  );

        this.newGameBtn = (Button) findViewById( R.id.game_new_game );
        this.stopGameBtn = (Button) findViewById( R.id.game_stop_game );

        this.scoreTv = (EditText) findViewById( R.id.game_score );
        this.maxScoreTv = (EditText) findViewById( R.id.game_max_score ) ;

        this.newGameBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playNewGame();
            }
        });

        this.stopGameBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopGame();
            }
        });
    }

    public void playNewGame() {
        this.newGameBtn.setEnabled( false );
        this.stopGameBtn.setEnabled(true);

        this.balloonView.playNewGame( this );
    }

    public void stopGame() {
        this.balloonView.stopGame();

        this.newGameBtn.setEnabled( true );
        this.stopGameBtn.setEnabled(false);
    }

     public void setGameScore( int score , int maxScore ) {
         this.scoreTv.setText( "" + score );
         this.maxScoreTv.setText( "" + maxScore );
     }

}
