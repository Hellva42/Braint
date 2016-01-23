package Braint;

import Braint.Agent;
import processing.core.PApplet;

/**
 * Created by Saitama on 18.01.2016.
 */
public class BraintAgentDraw extends PApplet {

    /**
     * @param strokeWidthScale
     * Value between 0 and 1, modifies the strokeWidth of the Agents in agents.update1()
     */
    Agent[] agents = new Agent[10000];
    int agentsCount = agents.length;
    float strokeWidthScale;



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


    }

    public void setup() {

        for(int i = 0; i<agents.length; i++) {
            agents[i] = new Agent(this);
        }

    }

    public void draw(){

        strokeWidthScale = random(0,1);



//        if (drawMode == 1) {
            for(int i = 0; i < agents.length; i++) agents[i].update1(this);
//        } else {
//            for(int i =0; i<agentsCount; i++) agents[i].update2();
//        }


    }

    public float getStrokeWidthScale() {
        return strokeWidthScale;
    }
}
