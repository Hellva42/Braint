package Braint.openVibe;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import Braint.drawMethods.IProcessingDrawable;
import Braint.main.BraintMainApplet;

public class OpenVibeCalibration implements IProcessingDrawable{
	
	
	
	
	private OpenVibeOscEEGPowerDataHandler data;
	private int timeInMS;
	private boolean startCalibration;
	private boolean calibrated;
	private long startTime;
	
	private boolean isCalibrating;
	
	private SummaryStatistics alphaStatisticsSummary, betaStatisticsSummary, ratioAlphaBetaStatisticsSummary;
	
	
	public SummaryStatistics getAlphaStatisticsSummary() {
		return alphaStatisticsSummary;
	}


	public SummaryStatistics getBetaStatisticsSummary() {
		return betaStatisticsSummary;
	}


	public SummaryStatistics getRatioAlphaBetaStatisticsSummary() {
		return ratioAlphaBetaStatisticsSummary;
	}


	public OpenVibeCalibration(OpenVibeOscEEGPowerDataHandler oscData, int calibrationTimeInMS) {
		
		data = oscData;
		timeInMS = calibrationTimeInMS;
		startCalibration = false;
		calibrated = false;
		startTime = -1;
		isCalibrating = false;
		
	}


	public void startCalibrationInNextDraw() {
		
		startCalibration = true;
		startTime = -1;
		calibrated = false;
		isCalibrating = true;
		
		
	}
	
	public boolean isCalibrating() {
		return isCalibrating;
	}


	@Override
	public void setupDrawing(BraintMainApplet applet) {
		// TODO Auto-generated method stub
		
	}
	
	
	// TODO draw something?
	@Override
	public void updateAndDraw(BraintMainApplet applet) {
		
		if(startCalibration && !calibrated) {
			
			
			
			if (startTime < 0) {
				startTime = System.currentTimeMillis();
				alphaStatisticsSummary = new SummaryStatistics();
				betaStatisticsSummary = new SummaryStatistics();
				ratioAlphaBetaStatisticsSummary = new SummaryStatistics();
				isCalibrating = true;
			}


			if (!Double.isNaN(data.alpha.getMeanAllChannels() / data.beta.getMeanAllChannels())) {
				ratioAlphaBetaStatisticsSummary.addValue(data.alpha.getMeanAllChannels() / data.beta.getMeanAllChannels());
			}
			if (!Double.isNaN(data.alpha.getMeanAllChannels())) {
				
				alphaStatisticsSummary.addValue(data.alpha.getMeanAllChannels());
			}
			if (!Double.isNaN(data.beta.getMeanAllChannels())) {
				betaStatisticsSummary.addValue(data.beta.getMeanAllChannels());
			}

			long time = System.currentTimeMillis() - startTime;

			applet.text("the time: " + time, applet.width / 2, 0 + time);

			if (time > timeInMS) {

				calibrated = true;
				isCalibrating = false;

//				println("Alpha");
//				println(alphaStatistics.toString());
//
//				println("Beta");
//				println(betaStatistics.toString());
//
//				
//				println("Alpha/Beta");
//				println(alphaBetaRatioStatistics.toString());

			}			
			
		}
			
		
	}


	public boolean calibrationFinisehd() {
		return calibrated;
	}
	




	
	
	
	
}