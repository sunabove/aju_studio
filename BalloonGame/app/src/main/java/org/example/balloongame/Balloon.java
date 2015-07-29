package org.example.balloongame;

import android.graphics.*;

public class Balloon {
    float x;
    float y;
    float radius;

    long timeMili;
    float vy_pixel_per_sec;
    Path circle ;

    public Balloon() {
        this.timeMili = System.currentTimeMillis();
    }

    public Path[] getShape(long currTimeMili) {

        long timeDiffMili = currTimeMili - timeMili ;
        this.y = this.y + (vy_pixel_per_sec*timeDiffMili/1000.0F );

        Path circle = new Path();

        circle.addCircle(x, y, radius, Path.Direction.CW);

        Path[] shapes = {circle};

        this.timeMili = currTimeMili;
        this.circle = circle ;

        return shapes;
    }
}
