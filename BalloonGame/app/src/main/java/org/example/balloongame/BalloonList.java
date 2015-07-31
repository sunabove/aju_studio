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

}
