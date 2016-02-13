package Braint.drawMethods.agents;

import java.util.Random;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import Braint.openVibe.OpenVibeCalibration;
import Braint.openVibe.OpenVibeOscEEGPowerDataHandler;
import Braint.util.BraintUtil;

public class BraintAgentParameterDeterminer {

	public static void determineBasedOnOpenVibeSignals(BraintAgentDraw braintAgentDraw,
			OpenVibeOscEEGPowerDataHandler data, OpenVibeCalibration calib) {

		float currentAlpha = data.alpha.getMeanAllChannels();
		float currentBeta = data.beta.getMeanAllChannels();
		float currentRatioAlphaBeta = currentAlpha / currentBeta;

		SummaryStatistics aSummary = calib.getAlphaStatisticsSummary();
		SummaryStatistics bSummary = calib.getBetaStatisticsSummary();
		SummaryStatistics abRSummary = calib.getRatioAlphaBetaStatisticsSummary();

		braintAgentDraw.valueToRGB = Math.abs(
				(float) BraintUtil.getNormalizedValueLog(currentRatioAlphaBeta, abRSummary.getMean() + 3 * abRSummary.getVariance(),
						abRSummary.getMean() - abRSummary.getStandardDeviation(), 1, 0));

		Random rnd = new Random();

		braintAgentDraw.rgb = BraintUtil.decideRGBValue(braintAgentDraw.valueToRGB, 1);
		braintAgentDraw.noiseStrength = (float) BraintUtil.getNormalizedValueLog(currentAlpha, // (alphaStatistics.getMean()
																			// +
																			// 3
																			// *
																			// alphaStatistics.getVariance())
																			// -
				aSummary.getMean() + 3 * aSummary.getVariance(), aSummary.getMean() - aSummary.getStandardDeviation(),
				25, 6);

		
		braintAgentDraw.noiseScale = (float) BraintUtil.getNormalizedValueLog(currentBeta, bSummary.getMean() + 3 * bSummary.getVariance(),
				bSummary.getMean() - bSummary.getStandardDeviation(), 500, 50);
		
		
		//System.out.println("rgb:\t" + braintAgentDraw.valueToRGB  + "|\t strength:\t"+braintAgentDraw.noiseStrength+"|\t scale:\t"+braintAgentDraw.noiseScale);

		
		
	}

}
