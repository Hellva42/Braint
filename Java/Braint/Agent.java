package Braint;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by Saitama on 19.01.2016.
 * TestTest
 */
public class Agent {

    public static void main() {}

    public PVector p, pOld;
    public float stepSize, angle;
    public boolean isOutside = false;

    public Agent(BraintAgentDraw bad) {

        p = new PVector(bad.random(bad.width), bad.random(bad.height));
        pOld = new PVector(p.x, p.y);
        stepSize = bad.random(1, 5);

    }

    public void update1(BraintAgentDraw bad) {

        angle = bad.noise(p.x, p.y);
        p.x += bad.cos(angle) * stepSize;
        p.y += bad.sin(angle) * stepSize;


        if(p.x < -10) isOutside = true;
        else if (p.x > bad.width+10) isOutside = true;
        else if (p.y < -10) isOutside = true;
        else if (p.y > bad.height+10) isOutside = true;


        if(isOutside) {

            p.x = bad.random(bad.width);
            p.y = bad.random(bad.height);
            pOld.set(p);

        }

        /**
         * todo : strokeWeigth(strokeWidth*length)
         */
//        strokeWeight(strokeWidth*length);
        float strokeWidth = 1;
        bad.strokeWeight(strokeWidth*bad.getStrokeWidthScale());
        bad.stroke(bad.random(1,65535));
        bad.line(pOld.x, pOld.y, p.x, p.y);
        pOld.set(p);
        isOutside = false;

    }



}
