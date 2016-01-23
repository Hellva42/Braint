package Braint;

import processing.core.*;


/**
 * Created by Saitama on 19.01.2016.
 * TestTest
 *
 * Variable Werte: stepSize
 */
public class Agent {

    public static void main() {}

    public PVector p, pOld;
    public float stepSize, angle;
    public boolean isOutside = false;

    public Agent(BraintAgentDraw bad) {

        p = new PVector(bad.random(bad.width), bad.random(bad.height));
        pOld = new PVector(p.x, p.y);
        //stepSize = bad.random(1, 5);
        stepSize = 0.1f;
    }

    public void update1(BraintAgentDraw bad) {

        //alternative
        angle = bad.noise(p.x / bad.getNoiseScale(), p.y / bad.getNoiseScale()) * bad.getNoiseStrength();

        //angle = bad.noise(p.x, p.y);
        p.x += bad.cos(angle) * stepSize;
        p.y += bad.sin(angle) * stepSize;

        //System.out.println("(" + pOld.x + "," + pOld.y + ")" + " -> " + "(" + p.x + "," + p.y + ")" + "  Angle = " + angle);
        //System.out.println(bad.cos(angle));
        System.out.println(bad.noise(p.x / bad.getNoiseScale(), p.y / bad.getNoiseScale()));


        if(p.x < -10) isOutside = true;
        else if (p.x > bad.width+10) isOutside = true;
        else if (p.y < -10) isOutside = true;
        else if (p.y > bad.height+10) isOutside = true;


        if(isOutside) {

            p.x = bad.random(bad.width);
            p.y = bad.random(bad.height);
            pOld.set(p);

        }

        /* use for colour mapping to strokes
        int rgb;
        int rnd = Math.round(bad.random(1,5));
        if (rnd == 1) rgb = 0x00640000;
        else if (rnd == 2) rgb = 0xB2222200;
        else if (rnd == 3) rgb = 0xFFFF0000;
        else if (rnd == 4) rgb = 0x0000FF00;
        else rgb = 0xFF149300;
        */


        /**
         * Variables:
         * stroke(rgb colour) see above
         * stroke(gray colour) value between 1 - 255
         *
         *
         */

        /**
         * todo : strokeWeigth(strokeWidth*length)
         */
//        strokeWeight(strokeWidth*length);
        float strokeWidth = 1;
        bad.strokeWeight(strokeWidth*bad.getStrokeWidthScale());
        //bad.stroke(rgb);
        //bad.stroke(125);
        bad.line(pOld.x, pOld.y, p.x, p.y);
        pOld.set(p);
        isOutside = false;

    }



}
