package org.example.balloongame;

import android.graphics.*;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class Balloon implements  CommonInterface {

    static long TOTAL_PAUSE_TIME = 0 ;
    static long PAUSE_TIME = 0 ;
    static long PAUSE_CURR_TIME = 0 ;

    float x;
    float y;
    float radius;

    boolean isClicked ;
    boolean isSticked ;

    int fillColor ;
    int lineColor ;
    int lineWidth ;

    long timeMili;
    float vy_pixel_per_sec;
    Path circle ;

    GameItem gameItem ;

    private Balloon() {
        this.timeMili = System.currentTimeMillis();

        this.fillColor = Color.RED ;
        this.lineColor = Color.BLUE ;
        this.lineWidth = 2 ;

        this.isClicked = false ;
        this.isSticked = false ;

        this.gameItem = GameItem.NONE ;
    }

    public Path[] getShape(long currTimeMili) {

        if( PAUSE_TIME > 0 ) {
            this.timeMili = currTimeMili ;
            
            Path[] shapes = { this.circle };
            return shapes ;
        }

        if( this.isSticked && this.circle != null ) {
            Path[] shapes = { this.circle };
            return shapes ;
        }

        long timeDiffMili = currTimeMili - timeMili ;

        this.y = this.y + (vy_pixel_per_sec * (timeDiffMili / 1000.0F));

        Path circle = new Path();

        circle.addCircle(x, y, radius, Path.Direction.CW);

        Path[] shapes = {circle};

        this.timeMili = currTimeMili;
        this.circle = circle ;

        return shapes;
    }

    @Override
    public String toString() {
        String msg = "Ballon x = %f, y = %f, r = %f, vy = %f";
        msg = String.format( msg, x, y, radius, vy_pixel_per_sec );

        return msg;
    }

    /**
     * returns if this ball include the clicked coordinate
     * @param clickX
     * @param clickY
     * @return
     */
    public boolean includes( float clickX, float clickY ) {
        float dx = this.x - clickX ;
        float dy = this.y - clickY ;
        float radius = this.radius ;

        if( dx*dx + dy*dy < radius*radius ) {
            return true;
        } else {
            return false ;
        }
    }

    /**
     * returns if the ball is sticked
     * @param balloon
     * @return
     */
    public boolean isSticked( Balloon balloon ) {
        float dx = this.x - balloon.x ;
        float dy = this.y - balloon.y ;

        float dr = this.radius + balloon.radius ;

        return dx*dx + dy*dy <= dr*dr ;
    }

    /**
     * returns if the ball is sticked
     * @param balloons
     * @return
     */
    public boolean isSticked( BalloonList balloons ) {

        for( Balloon balloon : balloons ) {
            if( this.isSticked( balloon )) {
                return true ;
            }
        }
        return false ;
    }

    private static final int [] BALLOON_FILL_COLORS = { Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN };

    public static Balloon createBalloon( int width , int height , long timeMiliPerFrame ) {

        if( PAUSE_TIME > 0 ) {
            return null ;
        }

        Balloon balloon = new Balloon();

        int radius = width < height ? width / 8 : height / 8;

        int x = (int)( radius + (width - 2*radius)*Math.random() );
        float vMin = height/12 ;
        float vMax = height/5 ;
        float vy_pixel_per_sec = - (float)( vMin + (vMax - vMin)*Math.random() ) ;

        GameItem gameItem = GameItem.NONE ;

        if( true ) {
            double itemId = Math.random() ;

            if( itemId < 0.05 ) {
                gameItem = GameItem.BOMB ;
            } else if( 0.5 < itemId && itemId < 0.55 ) {
                gameItem = GameItem.CLOCK ;
            }
        }

        balloon.x = x;
        balloon.y = height + radius ;
        balloon.radius = radius ;
        balloon.timeMili = System.currentTimeMillis();
        balloon.vy_pixel_per_sec = vy_pixel_per_sec ;

        int colorIndex = (int) ( BALLOON_FILL_COLORS.length*Math.random() );
        balloon.fillColor = BALLOON_FILL_COLORS[ colorIndex ];
        balloon.lineColor = Color.BLUE ;
        balloon.lineWidth = 2 ;
        balloon.gameItem = gameItem ;

        return balloon ;
    }
}
