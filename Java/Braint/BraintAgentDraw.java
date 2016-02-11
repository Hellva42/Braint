package Braint;

import java.util.LinkedList;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

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
	 *            Value between 0 and 1, modifies the strokeWidth of the Agents
	 *            in agents.update1()
	 *
	 * @param noiseScale
	 *            change angle properties of Agents Value between 200 and 1000
	 *            is good
	 *
	 * @param noiseStrength
	 *            change angle properties of Agents Value between 6 and 30 is
	 *            good
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


	
	// TODO CLEAN UP
	public class AlphaSample {

		public float o1, o2, p7, p8 = 0;

		public int channelCount = 4;

		public float getMean() {
			return alphaSum() / channelCount;
		}

		private float alphaSum() {
			return o1 + o2 + p7 + p8;
		}

	}

	public class BetaSample {

		public float f3, f4, f7, f8, af3, af4, fc5, fc6 = 0;

		public int channelCount = 8;

		public float getMean() {
			return betaSum() / channelCount;
		}

		private float betaSum() {
			return f3 + f4 + f7 + f8 + af3 + af4 + fc5 + fc6;
		}

	}
	
	// samples
	AlphaSample alpha = new AlphaSample();
	BetaSample beta = new BetaSample();
	
	SummaryStatistics alphaStatistics, betaStatistics, alphaBetaRatioStatistics;
	
	// 30 seconds calibration
	boolean calibrated = false;
	int calibrationTime = 30000;
	long startTime = -1;

	
	// CLEAN UP

	// TODO
	NetAddress myBroadcastLocation;
	OscP5 oscP5;

	/**
	 * main method, requiered open sketch window in fullscreen
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Braint.BraintAgentDraw" });
	}

	public void settings() {
		size(1920, 1080);
		// size(500,500);

	}

	public void setup() {

		frameRate(128);
		oscP5 = new OscP5(this, 12000);
		for (int i = 0; i < agents.length; i++) {
			agents[i] = new Agent(this);
		}
		strokeWidthScale = 0.3f;
		noiseStrength = 6f;
		noiseScale = 80f;
		valueToRGB = 1;
		oscNoiseScale = 80f;
		oscNoiseStrength = 13f;
		oscValueToRGB = 0f;

		oscNoiseScaleMax = 1f;
		oscNoiseStrengthMax = 1f;
		oscValueToRGBMax = 1f;

		oscNoiseScaleMin = 0f;
		oscNoiseStrengthMin = 0f;
		oscValueToRGBMin = 0f;

		// setVariables();

		rgb = decideRGBValue(valueToRGB, 2);
		timerA = 0;
		timerB = 0;
		timerC = 0;


	}

	public void draw() {
		
		
		if(!calibrated) {
			
			
//			clear();
			
			if(startTime < 0) {
				startTime = System.currentTimeMillis();				
				alphaBetaRatioStatistics = new SummaryStatistics();
				alphaStatistics = new SummaryStatistics();
				betaStatistics= new SummaryStatistics();
			
			}
			
			println(alpha.getMean() / beta.getMean());
			
			
			if(!Double.isNaN(alpha.getMean() / beta.getMean()))
				alphaBetaRatioStatistics.addValue(alpha.getMean() / beta.getMean());
			if(!Double.isNaN(alpha.getMean()))
					alphaStatistics.addValue(alpha.getMean());
			if(!Double.isNaN(beta.getMean()))
				betaStatistics.addValue(beta.getMean());
			
			long time = System.currentTimeMillis() - startTime ;
			
			text("the time: "+time, width / 2, 0 + time);
			
			if(time > calibrationTime) {
				
				
				calibrated = true;
				
				
				println(alphaStatistics.toString());
				

				println(betaStatistics.toString());
				
				println(alphaBetaRatioStatistics.toString());
			
			}
			
			
		} else {

		x++;

		// if(x == 25) {x = 1;
		// noiseScale = (float) getNormalizedValue(oscNoiseScale,
		// oscNoiseScaleMax, oscNoiseScaleMin, 50, 500);
		// noiseStrength = (float) getNormalizedValue(oscNoiseStrength,
		// oscNoiseStrengthMax, oscNoiseStrengthMin, 6, 23);

		// float f = (float) getNormalizedValue(oscValueToRGB, oscValueToRGBMax,
		// oscValueToRGBMin, 1, 0);
		
		
		
		valueToRGB = (float) getNormalizedValue(alpha.getMean() / beta.getMean(), alphaBetaRatioStatistics.getMax(), alphaBetaRatioStatistics.getMin(), 1, 0);
		
		
		valueToRGB = alpha.getMean() / beta.getMean();
		
		
		// }
		Random rnd = new Random();
		a = 1 + rnd.nextInt(2);
		a = 1;
		rgb = decideRGBValue(valueToRGB, a);
		noiseStrength = (float) getNormalizedValue(alpha.getMean(), alphaStatistics.getMax(), alphaStatistics.getMin(), 23, 6);
		// control with alpha power test
		noiseScale = (float) getNormalizedValue(beta.getMean(), betaStatistics.getMax(), betaStatistics.getMin(), 500, 50);
		// println("alpha"+ (o1 + o2 + p7 +p8));

		// draw everything with the changed values
		for (int i = 0; i < agents.length; i++) {
			agents[i].update1(this);

		}
		
		
		
		
		
		}
		/*
		 * try{ Thread.sleep(1000); }catch(InterruptedException e){}
		 */
	}

	/**
	 * takes one array with values through osc and returns this array normalizes
	 * every value to values between 0.3 and 1(values that are greater than the
	 * global maximum will be between 1 and 1.5) with a global maximum and
	 * minimum.
	 * 
	 * @return float array with values
	 */
	// public float[] getEmotivValues(){
	// return
	// }

	/**
	 * set all variable parameters for drawing, depending on choosed array slots
	 * 
	 * @param emotivValues
	 * @param a
	 *            Arrayslot for noiseScale value
	 * @param b
	 *            Arrayslot for noiseStrength value
	 * @param c
	 *            Arrayslot for strokeWidthScale value
	 * @param d
	 *            Arrayslot for rgb value
	 */

	public void setVariables(float[] emotivValues, int a, int b, int c, int d) {

		// **Begin NoiseScale**

		// **End NoiseScale**

		// **Begin NoiseStrength**

		// **EndNoiseStrength**

		// **Begin strokeWidthScale**

		// **End strokeWidthScale**

		// **Begin valueToRGB value**

		// **End valueToRGB value**

	}

	/**
	 * Chooses the colour in which the next strokes should be drawn depending on
	 * given value and colour mode,
	 * 
	 * @param value
	 *            value for which the colour should be picked
	 * @param mode
	 *            colour mode: 2 = yellow/red , 1 = green
	 * @return hex value for stroke function todo: change mode 2
	 */
	public int decideRGBValue(float value, int mode) {

		if (mode == 1) {
			if (value < 0.1666f)
				return 0xffccece6;
			if (value < 0.3333f)
				return 0xff99d8c9;
			if (value < 0.5f)
				return 0xff66c2a4;
			if (value < 0.6666f)
				return 0xff41ae76;
			if (value < 0.8333f)
				return 0xff238b45;
			if (value < 1f)
				return 0xff006d2c;
			return 0xff00441b;

		} else if (mode == 2) {
			if (value < 0.1666f)
				return 0xfffdd49e;
			if (value < 0.3333f)
				return 0xfffdbb84;
			if (value < 0.5f)
				return 0xfffc8d59;
			if (value < 0.6666f)
				return 0xffef6548;
			if (value < 0.8333f)
				return 0xffd7301f;
			if (value < 1f)
				return 0xffb30000;
			return 0xff7f0000;

		}

		return 0;
	}

	public float getStrokeWidthScale() {
		return strokeWidthScale;
	}

	public float getNoiseScale() {
		return noiseScale;
	}

	public float getNoiseStrength() {
		return noiseStrength;
	}

	public int getRGB() {
		return rgb;
	}

	public void setOscNoiseScale(float x) {
		oscNoiseScale = x;
	}

	public void setOscNoiseStrength(float x) {
		oscNoiseStrength = x;
	}

	public void setValueToRGB(float x) {
		oscValueToRGB = x;
	}

	public void oscEvent(OscMessage theOscMessage) {

		handleOpenVibeOSCMessages(theOscMessage);

		if (theOscMessage.checkAddrPattern("/Affective") == true) {

			/*
			 * typetag sfdddddd [0] emotivTimeStamp, boredom, excitement,
			 * frustration, mediation, valence, excitementLongTerm [1] 40.09696
			 * [2] 0.5487005114555359 [3] 0.0 [4] 0.7110533118247986 [5]
			 * 0.3333112597465515 [6] 0.625 [7] 0.0
			 */
			for (int i = 0; i < 6; i++) {

			}

			float excitement = (float) theOscMessage.get(3).doubleValue();
			setValueToRGB(excitement);

		} else if (theOscMessage.checkAddrPattern("/rawEEG") == true) {

			// string header = "COUNTER;INTERPOLATED;RAW_CQ;
			// 3 - AF3;F7;F3; FC5;
			// 7 - T7; P7; O1; O2;P8" +
			// 12 - T8; FC6; F4;F8; AF4;
			// GYROX; GYROY; TIMESTAMP; ES_TIMESTAMP" +
			// "FUNC_ID; FUNC_VALUE; MARKER; SYNC_SIGNAL;";

			// get(3 - 16) for all electrodes
			// theOscMessage.print();
			// scale
			float scale = (float) (theOscMessage.get(3).doubleValue() + theOscMessage.get(16).doubleValue()
					+ theOscMessage.get(5).doubleValue() + theOscMessage.get(14).doubleValue()
					+ theOscMessage.get(4).doubleValue() + theOscMessage.get(15).doubleValue()
					+ theOscMessage.get(13).doubleValue() + theOscMessage.get(6).doubleValue()); // AF3

			scale = scale / 8f;

			setOscNoiseScale(scale);

			// strength
			float strength = (float) (theOscMessage.get(8).doubleValue() + theOscMessage.get(11).doubleValue()
					+ theOscMessage.get(10).doubleValue() + theOscMessage.get(9).doubleValue()); // O2

			strength /= 4f;

			setOscNoiseStrength(strength);
			// rgb
			double sum = 0;
			for (int i = 0; i < 14; i++) {

				if (!(i == 7 || i == 12)) {
					sum += theOscMessage.get(i + 3).doubleValue();
				}
			}

			float value = (float) (sum / 12.0); // FC5

		} else if (theOscMessage.checkAddrPattern("/Expressiv") == true) {

		}

	}

	private void handleOpenVibeOSCMessages(OscMessage theOscMessage) {

		// handle alpha
		if (theOscMessage.checkAddrPattern("/openvibe/alpha/o1") == true) {
			// TODO
			// theOscMessage.print();
			alpha.o1 = theOscMessage.get(0).floatValue();
		}

		else if (theOscMessage.checkAddrPattern("/openvibe/alpha/o2") == true) {

			alpha.o2 = theOscMessage.get(0).floatValue();
		} else if (theOscMessage.checkAddrPattern("/openvibe/alpha/p7") == true) {

			alpha.p7 = theOscMessage.get(0).floatValue();
		} else if (theOscMessage.checkAddrPattern("/openvibe/alpha/p8") == true) {

			alpha.p8 = theOscMessage.get(0).floatValue();
		}
		// handle beta
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/f3") == true) {

			beta.f3 = theOscMessage.get(0).floatValue();
		}
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/f4") == true) {

			beta.f4 = theOscMessage.get(0).floatValue();
		}
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/af3") == true) {

			beta.af3 = theOscMessage.get(0).floatValue();
		}
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/af4") == true) {

			beta.af4 = theOscMessage.get(0).floatValue();
		}
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/f7") == true) {

			beta.f7 = theOscMessage.get(0).floatValue();
		}
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/f8") == true) {

			beta.f8 = theOscMessage.get(0).floatValue();
		}
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/fc5") == true) {

			beta.fc5 = theOscMessage.get(0).floatValue();
		}
		else if (theOscMessage.checkAddrPattern("/openvibe/beta/fc6") == true) {

			beta.fc6 = theOscMessage.get(0).floatValue();
		}
		
	}

	public void keyPressed() {
		saveFrame();
	}

	static public double getNormalizedValue(double value, double max, double min, double maxScaled, double minScaled) {
		return minScaled + (value - min) * (maxScaled - minScaled) / (max - min);
	}
}
