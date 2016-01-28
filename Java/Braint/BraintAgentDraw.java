package Braint;

import Braint.Agent;
import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
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
     * Value between 200 and 1000 is good
     *
     * @param noiseStrength
     * change angle properties of Agents
     * Value between 6 and 30 is good
     */
    Agent[] agents = new Agent[10000];
    float strokeWidthScale;
    float noiseScale, noiseStrength;
    float oscNoiseScale, oscNoiseStrength, oscValueToRGB;
    float oscNoiseScaleMax, oscNoiseStrengthMax, oscValueToRGBMax;
    float oscNoiseScaleMin, oscNoiseStrengthMin, oscValueToRGBMin;
    int timerA, timerB, timerC;
    int rgb;
    float valueToRGB;
    int x = 0;
    int a = 2;

    // TODO
	NetAddress myBroadcastLocation;
	OscP5 oscP5;


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
//        size(500,500);


    }

    public void setup() {

        frameRate(128);
        oscP5 = new OscP5(this, 12000);
        for(int i = 0; i<agents.length; i++) {
            agents[i] = new Agent(this);
        }
        strokeWidthScale = 0.3f;
        noiseStrength = 6f;
        noiseScale = 80f;
        valueToRGB = 1;
        oscNoiseScale =80f;
        oscNoiseStrength = 13f;
        oscValueToRGB = 0f;
        
        oscNoiseScaleMax = 1f;
        oscNoiseStrengthMax = 1f;
        oscValueToRGBMax = 1f;
        
        oscNoiseScaleMin = 0f;
        oscNoiseStrengthMin = 0f;
        oscValueToRGBMin = 0f;

        //setVariables();

        rgb = decideRGBValue(valueToRGB, 2);
        timerA = 0;
        timerB = 0;
        timerC = 0;
        
        
    }

    public void draw(){

       x++;
       
        //System.out.println(x);
/**
        //remove when added below todos
        timerA++;
        timerB++;
        timerC++;

        if ( timerA > 20) timerA = 1;
        if (timerB > 50) timerB = 1;
        if (timerC > 70) timerC = 1;

        //***variable*** codeblock for setting the variables to emotiv values

        //setVariables();

        //todo replace with function to set valueToRGB to EmotivValue
        if(valueToRGB < 1.13f && timerA == 20) valueToRGB = valueToRGB + 0.05f;
        else if (valueToRGB >= 1.13f && timerA == 20)valueToRGB = 0;

        rgb = decideRGBValue(valueToRGB, 2);

        //todo replace with function so set noiseScale to EmotivValue
        if(noiseScale < 800 && timerB == 50) noiseScale = noiseScale + 50f;
        else if(noiseScale >= 800 && timerB == 50) noiseScale = 200;

        //todo replace with function to set noiseStrength to EmotivValue
        if(noiseStrength < 22 && timerB == 70) noiseStrength = noiseStrength + 4;
        else if(noiseStrength >= 22 && timerB == 70) noiseStrength = 10;

        //todo: add function to set noisestrokeWidthScale to EmotivValue
        //code to be added here.

        //***variable*** codeblock ending

*/		
       if(x == 25) {x = 1;
        noiseScale = (float) getNormalizedValue(oscNoiseScale, oscNoiseScaleMax, oscNoiseScaleMin, 50, 500);
        noiseStrength = (float) getNormalizedValue(oscNoiseStrength, oscNoiseStrengthMax, oscNoiseStrengthMin, 6, 23);
        
        float f = (float) getNormalizedValue(oscValueToRGB, oscValueToRGBMax, oscValueToRGBMin, 1, 0);
        valueToRGB = oscValueToRGB;
       
       }
        rgb = decideRGBValue(valueToRGB, a);

        //draw everything with the changed values
        for(int i = 0; i < agents.length; i++) {
            agents[i].update1(this);
        
        }


        /*
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
        */
    }


    /**
     * takes one array with values through osc and returns this array
     * normalizes every value to values between 0.3 and 1(values that are greater than the global maximum will be between
     * 1 and 1.5) with a global maximum and minimum.
     * @return float array with values
     */
    //public float[] getEmotivValues(){
      //  return
    //}

    /**
     * set all variable parameters for drawing, depending on choosed array slots
     * @param emotivValues
     * @param a Arrayslot for noiseScale value
     * @param b Arrayslot for noiseStrength value
     * @param c Arrayslot for strokeWidthScale value
     * @param d Arrayslot for rgb value
     */

  public void setVariables(float[] emotivValues , int a, int b, int c, int d) {

      //**Begin NoiseScale**

      //**End NoiseScale**


      //**Begin NoiseStrength**

      //**EndNoiseStrength**


      //**Begin strokeWidthScale**

      //**End strokeWidthScale**



      //**Begin valueToRGB value**

      //**End valueToRGB value**


  }

    /**
     * Chooses the colour in which the next strokes should be drawn depending on given value and colour mode,
     * @param value value for which the colour should be picked
     * @param mode colour mode: 2 = yellow/red , 1 = green
     * @return hex value for stroke function
     * todo: change mode 2
     */
    public int decideRGBValue(float value, int mode){

        if(mode == 1) {
            if( value < 0.1666f) return 0xffccece6;
            if( value < 0.3333f) return 0xff99d8c9;
            if( value < 0.5f) return 0xff66c2a4;
            if( value < 0.6666f) return 0xff41ae76;
            if( value < 0.8333f) return 0xff238b45;
            if( value < 1f) return 0xff006d2c;
            return 0xff00441b;

        } else  if(mode == 2){
            if( value < 0.1666f) return 0xfffdd49e;
            if( value < 0.3333f) return 0xfffdbb84;
            if( value < 0.5f) return 0xfffc8d59;
            if( value < 0.6666f) return 0xffef6548;
            if( value < 0.8333f) return 0xffd7301f;
            if( value < 1f) return 0xffb30000;
            return 0xff7f0000;

        }

        return 0;
    }


    public float getStrokeWidthScale() { return strokeWidthScale; }
    public float getNoiseScale() { return noiseScale; }
    public float getNoiseStrength() { return noiseStrength; }
    public int getRGB() { return rgb; }
    
    public void setOscNoiseScale(float x){
    	oscNoiseScale = x;
    }
    public void setOscNoiseStrength(float x){
    	oscNoiseStrength = x;
    }
    public void setValueToRGB(float x){
    	oscValueToRGB = x;
    }
    
    
    public void oscEvent(OscMessage theOscMessage) {

	if (theOscMessage.checkAddrPattern("/Affective") == true) {

			/*
			 * typetag sfdddddd [0] emotivTimeStamp, boredom, excitement,
			 * frustration, mediation, valence, excitementLongTerm [1] 40.09696
			 * [2] 0.5487005114555359 [3] 0.0 [4] 0.7110533118247986 [5]
			 * 0.3333112597465515 [6] 0.625 [7] 0.0
			 */		
			for (int i = 0; i < 6; i++) {

			}
			
			float excitement = (float) theOscMessage.get(3).doubleValue() ;
			setValueToRGB(excitement);


		} else if (theOscMessage.checkAddrPattern("/rawEEG") == true) {

//			string header = "COUNTER;INTERPOLATED;RAW_CQ;
//			 3 - AF3;F7;F3; FC5; 
//			7 - T7; P7; O1; O2;P8" +
//          12 - T8; FC6; F4;F8; AF4;
//			GYROX; GYROY; TIMESTAMP; ES_TIMESTAMP" +  
//                    "FUNC_ID; FUNC_VALUE; MARKER; SYNC_SIGNAL;";
			
			// get(3 - 16) for all electrodes
//			theOscMessage.print();
			// scale
			float scale = (float)( 
					theOscMessage.get(3).doubleValue() 
					+ theOscMessage.get(16).doubleValue()
					+ theOscMessage.get(5).doubleValue()
					+ theOscMessage.get(14).doubleValue()
					+ theOscMessage.get(4).doubleValue()
					+ theOscMessage.get(15).doubleValue()
					+ theOscMessage.get(13).doubleValue()
					+ theOscMessage.get(6).doubleValue()); //AF3
			
			scale = scale / 8f;
//			if(oscNoiseScaleMax != -1.1f) {
//				if(oscNoiseScaleMax < scale)
//					oscNoiseScaleMax = scale;
//			} else
//				oscNoiseScaleMax = scale;
//			
//			if(oscNoiseScaleMin != -1.1f) {
//				if(oscNoiseScaleMin > scale)
//					oscNoiseScaleMin = scale;
//			} else
//				oscNoiseScaleMin = scale-0.001f;
			
			
//			println(scale);
			setOscNoiseScale(scale);
			
			
			
			// strength
			float strength = (float) ( 
					theOscMessage.get(8).doubleValue()
					+ theOscMessage.get(11).doubleValue()
					+ theOscMessage.get(10).doubleValue()
					+ theOscMessage.get(9).doubleValue()
					); // O2
			
			strength /= 4f;
			
//			if(oscNoiseStrengthMax != -1.1f) {
//				if(oscNoiseStrengthMax < strength)
//					oscNoiseStrengthMax = strength;
//			} else
//				oscNoiseStrengthMax = strength + 10f;
//			
//			if(oscNoiseStrengthMin != -1.1f) {
//				if(oscNoiseStrengthMin > strength)
//					oscNoiseStrengthMin = strength;
//			} else
//				oscNoiseStrengthMin = strength-10f;
			
			setOscNoiseStrength(strength);
			// rgb
			double sum = 0;
			for (int i = 0; i < 14; i++) {
				
				if(!(i == 7 || i == 12))
				{
					sum+= theOscMessage.get(i+3).doubleValue();
				}				
			}
			
			float value = (float)(sum / 12.0); // FC5
			
//			if(oscValueToRGBMax != -1.1f) {
//				if(oscValueToRGBMax < value)
//					oscValueToRGBMax = value;
//			} else
//				oscValueToRGBMax = value + 10f;
//			
//			if(oscValueToRGBMin != -1.1f) {
//				if(oscValueToRGBMin > value)
//					oscValueToRGBMin = value;
//			} else
//				oscValueToRGBMin = value-10f;
			
//			setValueToRGB(value);
			
//			for (int i = 0; i < 14; i++) {
//				
//			}


		} else if(theOscMessage.checkAddrPattern("/Expressiv") == true) {
			
//			 theOscMessage.print();
		}

	}
    
    public void keyPressed() {
    	  saveFrame();
    	}
    
    static public double getNormalizedValue(double value, double max, double min, double maxScaled, double minScaled)
    {
        return minScaled + (value - min) * (maxScaled - minScaled) / (max - min);
    }
}
