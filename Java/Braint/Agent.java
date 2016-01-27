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
        stepSize = bad.random(1, 5);
        //stepSize = 0.1f;
    }

    public void update1(BraintAgentDraw bad) {


        angle = bad.noise(p.x / bad.getNoiseScale(), p.y / bad.getNoiseScale()) * bad.getNoiseStrength();
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


        float strokeWidth = 1;
        bad.strokeWeight(strokeWidth*bad.getStrokeWidthScale());
        bad.stroke(bad.getRGB());
        bad.line(pOld.x, pOld.y, p.x, p.y);
        pOld.set(p);
        isOutside = false;

    }



}
