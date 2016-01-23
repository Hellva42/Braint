package Braint;

import Braint.Agent;
import processing.core.*;

/**
 * Created by Saitama on 18.01.2016.
 *
 *
 */
public class BraintAgentDraw extends PApplet {

    /**
     * @param strokeWidthScale
     * Value between 0 and 1, modifies the strokeWidth of the Agents in agents.update1()
     *
     * @param noiseScale
     * change angle properties of Agents
     *
     * @param noiseStrength
     * change angle properties of Agents
     */
    Agent[] agents = new Agent[10000];
    float strokeWidthScale;
    float noiseScale, noiseStrength;
    int timer;
    int rgb;



    /**
     * main method, requiered
     * open sketch window in fullscreen
     * @param args
     */
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "Braint.BraintAgentDraw" });
    }


    public void settings() {
        size(1920, 1080);
        //size(500,500);


    }

    public void setup() {

        for(int i = 0; i<agents.length; i++) {
            agents[i] = new Agent(this);
        }
        strokeWidthScale = 0.3f;
        noiseStrength = 10f;
        noiseScale = 300f;
        timer = 0;
        rgb = 0xff2b2b2b;
    }

    public void draw(){

        timer ++;

        if (timer > 200) timer = 1;

        if (timer > 160) {
            rgb = 0xffbbff88;
            //rgb = 0xffa81919;
            noiseStrength = 12;
            noiseScale = 450;
        //} else if ( timer > 120 ) {
          //  rgb = 0xff7b99ae;
            //noiseStrength = 16;
        } else if ( timer > 80 ) {
            rgb = 0xff7c0000;
            noiseStrength = 7;
            noiseScale = 200;
        //} else if (timer > 40) {
          //  rgb = 0xffbbff88;
            //noiseStrength = 14;
        }





//        if (drawMode == 1) {
            for(int i = 0; i < agents.length; i++) {
                agents[i].update1(this);
                /*
                if (i < 2500)  noiseScale = 1;
                else if (i >= 2500 && i < 5000) noiseScale = 0.666f;
                else if (i >= 5000 && i < 7500) noiseScale = 0.333f;
                else noiseScale = 4;
                */
            }
//        } else {
//            for(int i =0; i<agentsCount; i++) agents[i].update2();
//        }


        /*
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
        */
    }

    public float getStrokeWidthScale() {
        return strokeWidthScale;
    }
    public float getNoiseScale() { return noiseScale; }
    public float getNoiseStrength() { return noiseStrength; }
    public int getRGB() { return rgb; }
}
