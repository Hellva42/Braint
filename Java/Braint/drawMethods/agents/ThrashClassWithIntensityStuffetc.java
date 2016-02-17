//
//import Braint.openVibe.OpenVibeCalibration;
//import Braint.openVibe.OpenVibeOscEEGPowerDataHandler;
//import Braint.util.BraintUtil;
//import Braint.util.ThresholdBasedIntensity;

//package Braint.drawMethods.agents;
//
//public class ThrashClassWithIntensityStuffetc {
//
//
//	public static float[] decideStrengthAndScaleBasedOnIntensity(int intensityA, int intensityB) {
//		int strMax = 25;
//		int strMin = 6;
//
//		int scaMax = 500;
//		int scaMin = 50;
//
//		float[] res = new float[] { 0f, 0f };
//
//		float scale = 0;
//		float strength = 0;
//
//		if (intensityA == 3) {
//			// strength should be small and scale should be small
//
//			strength = strMin; // + 1/4 * (strMax -strMin);
//
//			scale = scaMin;
//
//			switch (intensityB) { // high beta should lead to bigger strength
//			case 1:
//				scale += 1 / 6 * (scaMax - scaMin);
//				strength += 1 / 6 * (strMax - strMin);
//				break;
//			case 2:
//				scale += 2 / 6 * (scaMax - scaMin);
//				strength += 2 / 6 * (strMax - strMin);
//				break;
//			case 3:
//				scale += 3 / 6 * (scaMax - scaMin);
//				strength += 3 / 6 * (strMax - strMin);
//				break;
//
//			default:
//
//				strength += 0 / 6 * (strMax - strMin);
//				scale += 0 / 6 * (scaMax - scaMin);
//				break;
//			}
//
//		} else if (intensityA == 2) {
//			strength = strMin + 2 / 6 * (strMax - strMin);
//
//			scale = scaMin + 1 / 6 * (scaMax - scaMin);
//
//			switch (intensityB) { // high beta should lead to bigger strength
//			case 1:
//				scale += 1 / 6 * (scaMax - scaMin);
//				strength += 1 / 6 * (strMax - strMin);
//				break;
//			case 2:
//				scale += 2 / 6 * (scaMax - scaMin);
//				strength += 2 / 6 * (strMax - strMin);
//				break;
//			case 3:
//				scale += 3 / 6 * (scaMax - scaMin);
//				strength += 3 / 6 * (strMax - strMin);
//				break;
//
//			default:
//
//				strength += 0 / 6 * (strMax - strMin);
//				scale += 0 / 6 * (scaMax - scaMin);
//				break;
//			}
//		}
//
//		else if (intensityA == 1) {
//			strength = strMin + 3 / 6 * (strMax - strMin);
//
//			scale = scaMin + 2 / 6 * (scaMax - scaMin);
//
//			switch (intensityB) { // high beta should lead to bigger strength
//			case 1:
//				scale += 1 / 6 * (scaMax - scaMin);
//				strength += 1 / 6 * (strMax - strMin);
//				break;
//			case 2:
//				scale += 2 / 6 * (scaMax - scaMin);
//				strength += 2 / 6 * (strMax - strMin);
//				break;
//			case 3:
//				scale += 3 / 6 * (scaMax - scaMin);
//				strength += 3 / 6 * (strMax - strMin);
//				break;
//
//			default:
//
//				strength += 0 / 6 * (strMax - strMin);
//				scale += 0 / 6 * (scaMax - scaMin);
//				break;
//			}
//		} else if (intensityA == 0) {
//			strength = strMin + 4 / 6 * (strMax - strMin);
//
//			scale = scaMin + 3 / 6 * (scaMax - scaMin);
//
//			switch (intensityB) { // high beta should lead to bigger strength
//			case 1:
//				scale += 1 / 6 * (scaMax - scaMin);
//				strength += 1 / 6 * (strMax - strMin);
//				break;
//			case 2:
//				scale += 2 / 6 * (scaMax - scaMin);
//				strength += 2 / 6 * (strMax - strMin);
//				break;
//			case 3:
//				scale += 3 / 6 * (scaMax - scaMin);
//				strength += 3 / 6 * (strMax - strMin);
//				break;
//
//			default:
//
//				strength += 0 / 6 * (strMax - strMin);
//				scale += 0 / 6 * (scaMax - scaMin);
//				break;
//			}
//		}
//
//		res[0] = strength;
//		res[1] = scale;
//
//		System.out.println("complicated:(strength,scale) = (" + strength + "," + scale + ")");
//		return res;
//	}
//
//	public static float decideStrengthValueBasedOnIntensity(int intensity) {
//
//		// 25, 6
//		int max = strMax;
//		int min = strMin;
//		float res = 0;
//
//		switch (intensity) {
//		case 1:
//			res = max - ((max - min) / 4);
//			break;
//		case 2:
//			res = min + ((max - min) / 2);
//			break;
//		case 3:
//			res = min + ((max - min) / 4);
//			break;
//
//		default:
//
//			res = max;
//
//			break;
//		}
//
//		return res;
//	}
//
//	public static float decideScaleValueBasedOnIntensity(int intensity) {
//
//		// 500, 50
//		int max = scaMax;
//		int min = scaMin;
//
//		float res = 0;
//
//		switch (intensity) {
//		case 1:
//			res = min + ((max - min) / 4);
//			break;
//		case 2:
//			res = min + ((max - min) / 2);
//			break;
//		case 3:
//			res = max - +((max - min) / 4);
//			break;
//
//		default:
//			res = min;
//			break;
//		}
//
//		return res;
//	}
//
//	public static float decideRGBValueBasedOnIntensity(int intensity) {
//
//		return 0;
//	}
//	
//
//	public static void determineBasedOnOpenVibeSignalsWithIntensityThresholds(BraintAgentDraw braintAgentDraw,
//			OpenVibeOscEEGPowerDataHandler data, OpenVibeCalibration calib) {
//
//		float currentAlpha = data.alpha.getMeanAllChannels();
//		float currentBeta = data.beta.getMeanAllChannels();
//		float currentRatioAlphaBeta = currentAlpha / currentBeta;
//
//		SummaryStatistics aSummary = calib.getAlphaStatisticsSummary();
//		SummaryStatistics bSummary = calib.getBetaStatisticsSummary();
//		SummaryStatistics abRSummary = calib.getRatioAlphaBetaStatisticsSummary();
//
//		if (alphaThres == null) {
//
//			// set the thresholds
//			float first, second, third;
//
//			// TODO which values make sense
//
//			// alpha
//			first = (float) aSummary.getMean();
//			second = (float) (aSummary.getMean() + aSummary.getStandardDeviation());
//			third = (float) (aSummary.getMean() + 3 * aSummary.getStandardDeviation());
//
//			alphaThres = new ThresholdBasedIntensity(first, second, third);
//
//			// beta
//			first = (float) bSummary.getMean();
//			second = (float) (bSummary.getMean() + bSummary.getStandardDeviation());
//			third = (float) (bSummary.getMean() + 3 * bSummary.getStandardDeviation());
//
//			betaThres = new ThresholdBasedIntensity(first, second, third);
//
//			// alpha / beta
//			first = (float) abRSummary.getMean();
//			second = (float) (abRSummary.getMean() + abRSummary.getStandardDeviation());
//			third = (float) (abRSummary.getMean() + 3 * abRSummary.getStandardDeviation());
//
//			alphaBetaRatioThres = new ThresholdBasedIntensity(first, second, third);
//
//		}
//
//		int alphaIntensity, betaIntensity, abRIntensity = 0;
//
//		alphaIntensity = alphaThres.determineIntensity(currentAlpha);
//		betaIntensity = betaThres.determineIntensity(currentBeta);
//		abRIntensity = alphaBetaRatioThres.determineIntensity(currentRatioAlphaBeta);
//
//		braintAgentDraw.valueToRGB = Math.abs((float) BraintUtil.getNormalizedValueLog(currentRatioAlphaBeta,
//				abRSummary.getMean() + 3 * abRSummary.getStandardDeviation(),
//				abRSummary.getMean() - abRSummary.getStandardDeviation(), 1, 0));
//
//		braintAgentDraw.rgb = BraintUtil.decideRGBValue(braintAgentDraw.valueToRGB, 1);
//
//		float[] results = new float[2];
//
//		// 0 = strenght und 1 = scale
//		results = decideStrengthAndScaleBasedOnIntensity(alphaIntensity, betaIntensity);
//
////		 results =
//		 //ThrashClassWithIntensityStuffetc.decideStrengthAndScaleBasedOnIntensityMoreSimple(alphaIntensity,
//		 //betaIntensity);
//		//
//		// results[0] = decideStrengthValueBasedOnIntensity(alphaIntensity);
//		// results[1] = decideScaleValueBasedOnIntensity(betaIntensity);
//
////		braintAgentDraw.noiseStrength = results[0];
////
////		braintAgentDraw.noiseScale = results[1];
//
//	}
//
//	/*
//	 * Noise strenght kleiner wenn Alpha power höher dann je höher beta power
//	 * detso höher noise strenght und je höher Alpha power desto niedriger noise
//	 * scale
//	 */

//
//	public static float[] decideStrengthAndScaleBasedOnIntensityMoreSimple(int intensityA, int intensityB) {
//	
//		float[] res = new float[] { 0f, 0f };
//	
//		float scale = BraintAgentParameterDeterminer.scaMin;
//		float strength = BraintAgentParameterDeterminer.strMin;
//	
//		switch (intensityB) { // high beta should lead to bigger strength
//		case 1:
//			scale += 1 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			strength += 1 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			break;
//		case 2:
//			scale += 2 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			strength += 2 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			break;
//		case 3:
//			scale += 3 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			strength += 3 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			break;
//	
//		default:
//	
//			strength += 0 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			scale += 0 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			break;
//		}
//	
//		switch (intensityA) { // high alpha should lead to smaller strength &
//								// scale
//		case 3:
//			scale += 1 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			strength += 1 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			break;
//		case 2:
//			scale += 2 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			strength += 2 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			break;
//		case 1:
//			scale += 3 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			strength += 3 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			break;
//	
//		default:
//	
//			strength += 0 / 6 * (BraintAgentParameterDeterminer.strMax - BraintAgentParameterDeterminer.strMin);
//			scale += 0 / 6 * (BraintAgentParameterDeterminer.scaMax - BraintAgentParameterDeterminer.scaMin);
//			break;
//		}
//	
//		res[0] = strength;
//		res[1] = scale;
//	
//		System.out.println("simple:(strength,scale) = (" + strength + "," + scale + ")");
//	
//		return res;
//	}
//
//}
