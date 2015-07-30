package org.example.balloongame;

import android.graphics.*;

public class Balloon {
    float x;
    float y;
    float radius;

    boolean isClicked ;

    int fillColor ;
    int lineColor ;
    int lineWidth ;

    long timeMili;
    float vy_pixel_per_sec;
    Path circle ;

    private Balloon() {
        this.timeMili = System.currentTimeMillis();

        this.fillColor = Color.RED ;
        this.lineColor = Color.BLUE ;
        this.lineWidth = 2 ;

        this.isClicked = false ;
    }

    public Path[] getShape(long currTimeMili) {

        long timeDiffMili = currTimeMili - timeMili ;
        this.y = this.y + (vy_pixel_per_sec*(timeDiffMili/1000.0F) );

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

    private static final int [] BALLOON_FILL_COLORS = { Color.RED, Color.YELLOW, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.LTGRAY };

    public static Balloon createBalloon( int width , int height , long timeMiliPerFrame ) {

        Balloon balloon = new Balloon();

        int radius = width < height ? width / 8 : height / 8;

        int x = (int)( radius + (width - 2*radius)*Math.random() );
        balloon.x = x;
        balloon.y = height + radius ;
        balloon.radius = radius ;
        balloon.timeMili = System.currentTimeMillis();
        balloon.vy_pixel_per_sec = - height/4;

        int colorIndex = (int) ( BALLOON_FILL_COLORS.length*Math.random() );
        balloon.fillColor = BALLOON_FILL_COLORS[ colorIndex ];
        balloon.lineColor = Color.BLUE ;
        balloon.lineWidth = 2;

        return balloon ;
    }
}
