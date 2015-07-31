package org.example.balloongame;

import java.util.ArrayList;

public class BalloonList extends ArrayList<Balloon> {

    public BalloonList() {

    }

    public float getMaxVerticalPosition() {
        float maxBalloonHeight = 0 ;
        for( Balloon balloon : this ) {
            maxBalloonHeight = balloon.y > maxBalloonHeight ? balloon.y : maxBalloonHeight ;
        }

        return maxBalloonHeight ;
    }

    /**
     * returns balloon list inside bomb area
     * @param bombBalloon
     * @return
     */
    public BalloonList getBalloonListInsideBombArea( Balloon bombBalloon ) {
        BalloonList balloonList = new BalloonList();

        float circleDistum = 4* bombBalloon.radius *bombBalloon.radius ;

        for( Balloon balloon : this ) {
            if( bombBalloon.getDistum( balloon) < circleDistum ) {
                balloonList.add( balloon );
            }
        }

        return balloonList ;
    }

}
