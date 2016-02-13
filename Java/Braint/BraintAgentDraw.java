package Braint;

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * Created by Saitama on 18.01.2016.
 *
 *
 */
public class BraintAgentDraw implements IProcessingDrawable {

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

	// 30 seconds calibration
	boolean calibrated = false;
	int calibrationTime = 15000;
	long startTime = -1;

	// TODO

	@Override
	public void setupDrawing(BraintMainApplet applet) {

		for (int i = 0; i < agents.length; i++) {
			agents[i] = new Agent(this);
			agents[i].setupDrawing(applet);
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

		rgb = BraintUtil.decideRGBValue(valueToRGB, 2);

		timerA = 0;
		timerB = 0;
		timerC = 0;

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

	@Override
	public void updateAndDraw(BraintMainApplet applet) {
		x++;

		OpenVibeOscEEGPowerDataHandler data = applet.getPowerDataHandler();
		OpenVibeCalibration calib = applet.getCalibrationData();

		float currentAlpha = data.alpha.getMeanAllChannels();
		float currentBeta = data.beta.getMeanAllChannels();
		float currentRatioAlphaBeta = currentAlpha / currentBeta;

		SummaryStatistics aSummary = calib.getAlphaStatisticsSummary();
		SummaryStatistics bSummary = calib.getBetaStatisticsSummary();
		SummaryStatistics abRSummary = calib.getRatioAlphaBetaStatisticsSummary();

		valueToRGB = Math.abs(
				(float) BraintUtil.getNormalizedValue(currentAlpha / currentBeta, aSummary.getMean() + 3 * aSummary.getVariance(),
						aSummary.getMean() - aSummary.getStandardDeviation(), 1, 0));

		// valueToRGB = alpha.getMean() / beta.getMean();

		// println("rgb value"+valueToRGB);
		// }
		Random rnd = new Random();
		a = 1 + rnd.nextInt(2);
		a = 1;
		rgb = BraintUtil.decideRGBValue(valueToRGB, a);
		noiseStrength = (float) BraintUtil.getNormalizedValue(currentAlpha, // (alphaStatistics.getMean()
																			// +
																			// 3
																			// *
																			// alphaStatistics.getVariance())
																			// -
				aSummary.getMean() + 3 * aSummary.getVariance(), aSummary.getMean() - aSummary.getStandardDeviation(),
				25, 6);
				// control with alpha power test

		// noiseStrength = 6;

		noiseScale = (float) BraintUtil.getNormalizedValue(currentBeta, bSummary.getMean() + 3 * bSummary.getVariance(),
				bSummary.getMean() - bSummary.getStandardDeviation(), 500, 50);

		// noiseScale = 500;
		// println("alpha"+ (o1 + o2 + p7 +p8));

		// println("noise scale:"+noiseScale);

		System.out.println("noise strength:" + noiseStrength + "--- scale:" + noiseScale);

		// draw everything with the changed values
		for (int i = 0; i < agents.length; i++) {
			agents[i].updateAndDraw(applet);
		}

	}

}
